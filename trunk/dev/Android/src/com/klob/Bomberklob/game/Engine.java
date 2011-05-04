package com.klob.Bomberklob.game;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.objects.exceptions.BombPowerException;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class Engine {

	private Single single;

	private Point nextTile = null, currentTile = null;
	private int size,x, y;


	private boolean bombBoolean = true;
	private Thread bombThread;	
	private ConcurrentHashMap<Point, Bomb> bombs;

	/* Constructeur -------------------------------------------------------- */

	public Engine(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		this.single = new Single(mapName, enemies, gametype, random, difficulty);
		this.size = ResourcesManager.getSize();
		this.bombs = new ConcurrentHashMap<Point, Bomb>();
		this.x = 0;
		this.y = 0;
	}


	/* Getters ------------------------------------------------------------- */

	public Single getSingle() {
		return single;
	}

	/* Setters ------------------------------------------------------------- */

	public void setSingle(Single single) {
		this.single = single;
	}

	public void setBombThreadRunning(boolean bombBoolean2) {
		this.bombBoolean = bombBoolean2;
		if ( this.bombBoolean && (this.bombThread == null || this.bombThread.getState() == Thread.State.TERMINATED)) {
			this.bombThread = new Thread() {
				@Override
				public void run() {
					Log.i("Bombs Thread","Thread started");
					while (bombBoolean) {
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
						for(Entry<Point, Bomb> entry : bombs.entrySet()) {
							bombs.get(entry.getKey()).updateTime();							
						}
					}
					Log.i("Bombs Thread","Thread done");
				};
			};
			this.bombThread.start();
		}
	}

	/* Méthodes publiques -------------------------------------------------- */		

	public void pushBomb(Player player) {

		if ( player != null ) {
			Point p = ResourcesManager.coToTile(player.getPosition().x+(ResourcesManager.getSize()/2), player.getPosition().y+(ResourcesManager.getSize()/2));

			if ( this.bombs.get(p) == null ) {
				if ( player.getBombNumber() > 0 ) {
					ConcurrentHashMap<Point, Integer> zoneDangereuses = this.single.map.getZoneDangereuses();

					/* On crée une nouvelle bombe */
					Bomb bomb = new Bomb(player.getBombSelected(), ResourcesManager.getBombsAnimations().get(player.getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, player);

					/* AJOUT DES ZONES DANGEREUSES POUR L'IA */

					/* CENTER */
					zoneDangereuses.put(p,1);

					/* UP */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( (this.single.map.getBlocks()[p.x][p.y-k] == null) && (this.bombs.get(new Point(p.x, p.y-k)) == null) ) {
							if ( !this.single.map.getGrounds()[p.x][p.y-k].isFireWall() ) {
								zoneDangereuses.put(new Point(p.x, p.y-k),1);
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}

					/* DOWN */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( (this.single.map.getBlocks()[p.x][p.y+k] == null) && (this.bombs.get(new Point(p.x, p.y+k)) == null) ) {
							if ( !this.single.map.getGrounds()[p.x][p.y+k].isFireWall() ) {
								zoneDangereuses.put(new Point(p.x, p.y+k),1);
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}

					/* LEFT */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( (this.single.map.getBlocks()[p.x-k][p.y] == null) && (this.bombs.get(new Point(p.x-k, p.y)) == null) ) {
							if ( !this.single.map.getGrounds()[p.x-k][p.y].isFireWall() ) {
								zoneDangereuses.put(new Point(p.x-k, p.y),1);
								System.out.println(p.toString());
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}

					/* RIGHT */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( (this.single.map.getBlocks()[p.x+k][p.y] == null) && (this.bombs.get(new Point(p.x+k, p.y)) == null) ) {
							if ( !this.single.map.getGrounds()[p.x+k][p.y].isFireWall() ) {
								zoneDangereuses.put(new Point(p.x+k, p.y),1);
								System.out.println(p.toString());
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}

					/* FIN DES ZONES DANGEREUSES */

					/* Coordonnées de la bombe posée */
					bomb.setPosition(ResourcesManager.tileToCo(p.x, p.y));

					/* On joue la musique */
					bomb.playCurrentAnimationSound();

					/* On l'ajoute dans la hash map de bombes */
					this.bombs.put(p, bomb);

					/* On diminue la quantité des bombes que peut poser le joueur */
					try {
						player.setBombNumber(player.getBombNumber()-1);
					} catch (BombPowerException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);

		/* FIXME */
		for(Entry<Point, Bomb> entry : bombs.entrySet()) {
			bombs.get(entry.getKey()).onDraw(canvas, size);							
		}
	}

	public void update() {
		Player[] players = this.single.getPlayers();
		ConcurrentHashMap<Point, Integer> zoneDangereuses = this.single.map.getZoneDangereuses();

		/* Bombes -------------------------------------------------- */

		this.updateBombs();			

		/* Joueurs ------------------------------------------------- */

		for (int i = 0 ; i < players.length ; i++ ) {
			if ( players[i] != null ) {

				if ( players[i].getPosition() != null ) {

					/* Si l'animation courante correspond à KILL et qu'elle est finie on supprime le personnage */
					if ( players[i].hasAnimationFinished() && players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
						/* On met joueur à null ce qui stopera le thread d'écoute seulement pour le joueur humain */
						players[i].setPosition(null);
					}
					else {
						/* Si le joueur vient de se faire toucher */
						if ( players[i].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel()) ) {
							/* Si l'animation est finie */
							if ( players[i].hasAnimationFinished() ) {
								/* On lance un timer qui fera "clignoter" le personnage */
								players[i].setCurrentAnimation(PlayerAnimations.IDLE);
								players[i].setImmortal(50);
							}
						}
						else {
							Point point = players[i].getPosition();
							Point point1 = ResourcesManager.coToTile(point.x,point.y);
							Point point2 = ResourcesManager.coToTile(point.x+ResourcesManager.getSize()-1,point.y);
							Point point3 = ResourcesManager.coToTile(point.x+ResourcesManager.getSize()-1,point.y+ResourcesManager.getSize()-1);
							Point point4 = ResourcesManager.coToTile(point.x,point.y+ResourcesManager.getSize()-1);

							/* IA */
							if ( i != 0 && !players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel()) ) {
								/*Si le bot n'a pas d'objectif*/
								if ( players[i].getObjectif() == null ) {
									/* Defensif (On est dans une zone dangereuse) */
									System.out.println("Point Bot : " + point1.toString());

									if ( zoneDangereuses.get(point1) == Integer.valueOf(1) ) {

										int x = point1.x;
										int y = point1.y;

										/* Point.x = 21 */
										/* Point.y = 15 */

										/* On regarde les 8 cases nous entourant */
										for (int h = point1.x-1; h < point1.x+2 ; h++ ) {
											for (int v = point1.y-1; v < point1.y+2 ; v++ ) {	
												System.out.println("TEST : (" + h + "," +v + ")" );
												if ( zoneDangereuses.get(new Point(h,v)) != Integer.valueOf(1) && isSolidBlock(h,v) && !this.single.map.getGrounds()[h][v].isHit()) {
													/* Diagonales */
													if ( point1.x != h && point1.y != v ) {
														if ( h > point1.x ) {
															if ( v > point1.y ) {
																if ( isSolidBlock(h-1, v) && !this.single.map.getGrounds()[h-1][v].isHit()) {
																	if ( isSolidBlock(h, v-1) && !this.single.map.getGrounds()[h][v-1].isHit()) {
																		x = h;
																		y = v;
																		System.out.println("RESULTAT : " + x + "|" + y);
																	}
																}															
															}
															else {
																if ( isSolidBlock(h-1, v) && !this.single.map.getGrounds()[h-1][v].isHit() ) {
																	if ( isSolidBlock(h, v+1) && !this.single.map.getGrounds()[h][v+1].isHit() ) {
																		x = h;
																		y = v;
																		System.out.println("RESULTAT : " + x + "|" + y);
																	}
																}
															}
														}
														else {
															if ( v > point1.y ) {
																if ( isSolidBlock(h+1, v) && !this.single.map.getGrounds()[h+1][v].isHit() ) {
																	if ( isSolidBlock(h, v-1) && !this.single.map.getGrounds()[h][v-1].isHit() ) {
																		x = h;
																		y = v;
																		System.out.println("RESULTAT : " + x + "|" + y);
																	}
																}	
															}
															else {
																if ( isSolidBlock(h+1, v) && !this.single.map.getGrounds()[h+1][v].isHit() ) {
																	if ( isSolidBlock(h, v+1) && !this.single.map.getGrounds()[h][v+1].isHit() ) {
																		x = h;
																		y = v;
																		System.out.println("RESULTAT : " + x + "|" + y);
																	}
																}
															}
														}
													}
													else {
														x = h;
														y = v;
														System.out.println("RESULTAT : " + x + "|" + y);
													}
												}
											}
										}

										/* FIXME FAIRE LA FONCTION DE RECHERCHE */
										if ( x == point1.x && y == point1.y ) {
/*
											int[][] distance = new int[21][15];

											distance[x][y] = 1;
											distance[x+1][y] = 1;
											distance[x-1][y] = 1;
											distance[x][y+1] = 1;
											distance[x][y-1] = 1;

											PlayerAnimations[][] direction = new PlayerAnimations[21][15];
											int d = 1;

											for (int h=0; h<21 ; h++) {
												for (int v=0 ; v<15 ; v++) {

													if (distance[h][v] == d) {

														if (!solid[h][v+1] && distance[h][v+1]==0) {
															direction[h][v+1] = direction[h][v];
															distance[h][v+1]=d+1;
														}


														if (!solid[h][v-1] && distance[h][v-1]==0) {
															direction[h][v-1] = direction[h][v];
															distance[h][v-1]=d+1;
														}

														if (!solid[h+1][v] && distance[h+1][v]==0) {
															direction[h+1][v] = direction[h][v];
															distance[h+1][v]=d+1;
														}

														if (!solid[h-1][v] && distance[h-1][v]==0) {
															direction[h-1][v] = direction[h][v];
															distance[h-1][v]=d+1;
														}
													}
												}
											}*/
										}

										x = x*ResourcesManager.getSize();
										y = y*ResourcesManager.getSize();

										players[i].setObjectif(new Point(x,y));
									}
									/* Offensif */
									else {

									}
								}
								else {
									Point objectif = players[i].getObjectif();
									String animation = players[i].getCurrentAnimation();

									/* Si on a atteind l'objectif */
									if ( point.x == objectif.x && point.y == objectif.y ) {
										players[i].setObjectif(null);
										if ( animation.equals(PlayerAnimations.RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_RIGHT);
										}
										else if ( animation.equals(PlayerAnimations.LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_LEFT);
										}
										else if ( animation.equals(PlayerAnimations.UP.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_UP);
										}
										else if ( animation.equals(PlayerAnimations.DOWN.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_DOWN);
										}
										else if ( animation.equals(PlayerAnimations.DOWN_RIGHT.getLabel()) ) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_DOWN_RIGHT);
										}
										else if ( animation.equals(PlayerAnimations.DOWN_LEFT.getLabel()) ) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_DOWN_LEFT);
										}
										else if ( animation.equals(PlayerAnimations.UP_RIGHT.getLabel()) ) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_UP_RIGHT);
										}
										else if ( animation.equals(PlayerAnimations.UP_LEFT.getLabel()) ) {
											players[i].setCurrentAnimation(PlayerAnimations.STOP_UP_LEFT);
										}
									}


									if ( point.x < objectif.x && point.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN_RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN_RIGHT);
										}										
									}
									else if ( point.x > objectif.x && point.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN_LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN_LEFT);
										}										
									}
									else if ( point.x > objectif.x && point.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP_LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP_LEFT);
										}										
									}
									else if ( point.x < objectif.x && point.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP_RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP_RIGHT);
										}										
									}
									else if ( point.x < objectif.x ) {
										if ( !animation.equals(PlayerAnimations.RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.RIGHT);
										}										
									}
									else if ( point.x > objectif.x  ) {
										if ( !animation.equals(PlayerAnimations.LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.LEFT);
										}										
									}
									else if ( point.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP);
										}										
									}
									else if ( point.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN);
										}										
									}
								}
							}

							/* On vérifie que le joueur n'est pas sur une case lui causant des dommages */
							if ( players[i].isDestructible() ) {
								if ( (this.single.map.getAnimatedObjects().get(point1) != null && this.single.map.getAnimatedObjects().get(point1).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point2) != null && this.single.map.getAnimatedObjects().get(point2).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point3) != null && this.single.map.getAnimatedObjects().get(point3).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point4) != null && this.single.map.getAnimatedObjects().get(point4).getDamage() != 0) || (this.single.map.getGrounds()[point1.x][point1.y] != null && this.single.map.getGrounds()[point1.x][point1.y].getDamage() != 0) || (this.single.map.getGrounds()[point1.x][point1.y] != null && this.single.map.getGrounds()[point2.x][point2.y].getDamage() != 0) || (this.single.map.getGrounds()[point3.x][point3.y] != null && this.single.map.getGrounds()[point3.x][point3.y].getDamage() != 0) || (this.single.map.getGrounds()[point4.x][point4.y] != null && this.single.map.getGrounds()[point4.x][point4.y].getDamage() != 0) ) {
									players[i].decreaseLife();
									if ( players[i].getLife() == 0 ) {
										players[i].setCurrentAnimation(PlayerAnimations.KILL);
									}
									else {
										players[i].setCurrentAnimation(PlayerAnimations.TOUCHED);
									}
								}
							}
						}						

						/*TODO  Vérifier tapis roulant */

						move(players[i]);
						players[i].update();

					}
				}

			}
		}
		this.single.update();
	}

	public void restartGame () {		
		/* Remise à 0 du vecteur de bombes */
		this.bombs.clear();

		/* Thread des bombes mis à l'arrêt */
		this.setBombThreadRunning(false);

		this.single.restartGame();		
	}
	
	public boolean isSolidBlock(int x, int y) {
		return ((this.bombs.get(new Point(x,y)) == null) && ((this.single.map.getBlocks()[x][y] == null) || !this.single.map.getBlocks()[x][y].isHit() || ((this.single.map.getBlocks()[x][y].isDestructible() && ((this.single.map.getAnimatedObjects().get(new Point(x,y)) == null) || !this.single.map.getAnimatedObjects().get(new Point(x,y)).isHit())))));
	}

	/* Méthodes privées ---------------------------------------------------- */

	private void move(Player player) {
		if ( player.getCurrentAnimation().equals(PlayerAnimations.UP.getLabel())) {
			moveUp(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.DOWN.getLabel())) {
			moveDown(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.LEFT.getLabel())) {
			moveLeft(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.RIGHT.getLabel())) {
			moveRight(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.DOWN_LEFT.getLabel())) {
			downLeft(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.DOWN_RIGHT.getLabel())) {
			downRight(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.UP_LEFT.getLabel())) {
			upLeft(player);
		}
		else if ( player.getCurrentAnimation().equals(PlayerAnimations.UP_RIGHT.getLabel())) {
			upRight(player);
		}
	}


	private void moveUp(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.y > this.size ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().y == this.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x, this.y-1);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( isSolidBlock(this.nextTile.x , this.nextTile.y) ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y--;
							}
							else if ( isSolidBlock(this.nextTile.x+1 , this.nextTile.y) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									this.y--;
								}
								else {
									if ( this.x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
										this.x--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( this.x <= ((this.currentTile.x*this.size)+(this.size/2)) ) {
									this.x--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( isSolidBlock(this.nextTile.x+1, this.nextTile.y) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									if ( this.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
										this.x++;
									}
									else {
										break;
									}
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
					}
					else {
						if ( isSolidBlock(this.nextTile.x+1 , this.nextTile.y) ) {
							if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
								if ( this.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
									this.x++;
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
				}
				else {
					this.y--;
				}
			}
			else {
				break;
			}
		}
		player.setPosition(new Point(this.x, this.y));		
	}


	private void moveDown(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.y < (this.size*(this.single.map.getBlocks()[0].length-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().y == this.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x, this.y+this.size);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y+this.size-1);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( isSolidBlock(this.nextTile.x , this.nextTile.y) ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y++;
							}
							else if ( isSolidBlock(this.nextTile.x+1 , this.nextTile.y) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									this.y++;
								}
								else {
									if ( this.x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
										this.x--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( this.x <= ((this.currentTile.x*this.size)+(this.size/2)) ) {
									this.x--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( isSolidBlock(this.nextTile.x+1 , this.nextTile.y) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									if ( this.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
										this.x++;
									}
									else {
										break;
									}
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
					}
					else {
						if ( isSolidBlock(this.nextTile.x+1 , this.nextTile.y) ) {
							if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
								if ( this.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
									this.x++;
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
				}
				else {
					this.y++;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(this.x, this.y));		
	}


	private void moveRight(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.x < (this.size*(this.single.map.getBlocks().length-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().x == this.x) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x+this.size, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x+this.size-1, this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( isSolidBlock(this.nextTile.x , this.nextTile.y) ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x++;
							}
							else if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									this.x++;
								}
								else {
									if ( this.y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
										this.y--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( this.y <= ((this.currentTile.y*this.size)+(this.size/2)) ) {
									this.y--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									if ( this.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
										this.y++;
									}
									else {
										break;
									}
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
					}
					else {
						if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
							if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
								if ( this.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
									this.y++;
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
				}
				else {
					this.x++;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(this.x, this.y));
	}


	private void moveLeft(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.x > this.size ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().x == this.x) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x-1, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( isSolidBlock(this.nextTile.x , this.nextTile.y) ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x--;
							}
							else if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									this.x--;
								}
								else {
									if ( this.y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
										this.y--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( this.y <= ((this.currentTile.y*this.size)+(this.size/2)) ) {
									this.y--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									if ( this.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
										this.y++;
									}
									else {
										break;
									}
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
					}
					else {
						if ( isSolidBlock(this.nextTile.x , this.nextTile.y+1) ) {
							if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
								if ( this.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
									this.y++;
								}
								else {
									break;
								}
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
				}
				else {
					this.x--;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(this.x, this.y));			
	}


	private void upRight(Player player) {		
		moveRight(player);
		moveUp(player);
	}

	private void upLeft(Player player) {		
		moveLeft(player);
		moveUp(player);
	}

	private void downLeft(Player player) {		
		moveLeft(player);
		moveDown(player);
	}

	private void downRight(Player player) {		
		moveRight(player);
		moveDown(player);
	}

	private void updateBombs() {
		Map map = this.single.getMap();
		ConcurrentHashMap<Point, Objects> animatedObjects = map.getAnimatedObjects();
		Bomb bomb = null;
		Objects object;

		for(Entry<Point, Bomb> entry : bombs.entrySet()) {

			bomb = bombs.get(entry.getKey());

			/* On met à jour l'affichage de la bombe */
			bomb.update();

			/* Si la bombe doit exploser */
			if ( bomb.timeElapsed() && bomb.getCurrentAnimation().equals(ObjectsAnimations.ANIMATE.getLabel())) {
				/* On passe son animation à DESTROY */
				bomb.setCurrentAnimation(ObjectsAnimations.DESTROY);
			}
			/* Si la bombe a comme animation DESTROY et qu'elle est finie alors on ajoute les flammes et on supprime la bombe */
			else if ( bomb.hasAnimationFinished() && bomb.getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel())) {

				Point p = ResourcesManager.coToTile(bomb.getPosition().x, bomb.getPosition().y);
				object = ResourcesManager.getObjects().get("firecenter").copy();
				object.setPosition(new Point(p.x*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));				
				animatedObjects.put(p, object);

				/* UP */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x, p.y-k)) != null ) {
						this.bombs.get(new Point(p.x, p.y-k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( (map.getBlocks()[p.x][p.y-k] == null || (map.getBlocks()[p.x][p.y-k].isDestructible() && map.getAnimatedObjects().get(new Point(p.x ,p.y-k)) == null )) ) {
						/* Si le sol ne laisse pas passer le feu */
						if ( map.getGrounds()[p.x][p.y-k].isFireWall() ) {
							/* On sort */
							break;
						}
						else {
							/* On affiche le feu */
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firevertical").copy();
								object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y-k)*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x,p.y-k), object);
							}
							else {
								object = ResourcesManager.getObjects().get("fireup").copy();
								object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y-k)*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x,p.y-k), object);
							}
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( !map.getBlocks()[p.x][p.y-k].isDestructible() ) {
							/* Si il ne laisse pas passer le feu */
							if ( map.getBlocks()[p.x][p.y-k].isFireWall() ) {
								/* On break */
								break;
							}
						}
						/* Si il est destructible */
						else {
							/* Si il est présent dans les blocs animé */
							if ( map.getAnimatedObjects().get(new Point(p.x ,p.y-k)) != null ) {
								/* Si il est déjà en train d'être détruit */
								if ( map.getAnimatedObjects().get(new Point(p.x ,p.y-k)).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
									if ( k < bomb.getPower()-1 ) {
										object = ResourcesManager.getObjects().get("firevertical").copy();
										object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y-k)*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x,p.y-k), object);
									}
									else {
										object = ResourcesManager.getObjects().get("fireup").copy();
										object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y-k)*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x,p.y-k), object);
									}
								}
								/* Sinon on le detruit */
								else {
									map.getAnimatedObjects().get(new Point(p.x ,p.y-k)).destroy();
									/* Si il ne laisse pas passer le feu */
									if ( map.getAnimatedObjects().get(new Point(p.x ,p.y-k)).isFireWall() ) {
										/* On break */
										break;
									}
								}
							}
						}
					}
				}

				/* DOWN */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					if ( this.bombs.get(new Point(p.x, p.y+k)) != null ) {
						this.bombs.get(new Point(p.x, p.y+k)).destroy();
					}
					else if ( map.getBlocks()[p.x][p.y+k] == null || (map.getBlocks()[p.x][p.y+k].isDestructible() && map.getAnimatedObjects().get(new Point(p.x ,p.y+k)) == null ) ) {
						if ( map.getGrounds()[p.x][p.y+k].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firevertical").copy();
								object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y+k)*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x,p.y+k), object);
							}
							else {
								object = ResourcesManager.getObjects().get("firedown").copy();
								object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y+k)*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x,p.y+k), object);
							}
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( !map.getBlocks()[p.x][p.y+k].isDestructible() ) {
							/* Si il ne laisse pas passer le feu */
							if ( map.getBlocks()[p.x][p.y+k].isFireWall() ) {
								/* On break */
								break;
							}
						}
						/* Si il est destructible */
						else {
							/* Si il est présent dans les blocs animé */
							if ( map.getAnimatedObjects().get(new Point(p.x ,p.y+k)) != null ) {
								/* Si il est déjà en train d'être détruit */
								if ( map.getAnimatedObjects().get(new Point(p.x ,p.y+k)).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
									if ( k < bomb.getPower()-1 ) {
										object = ResourcesManager.getObjects().get("firevertical").copy();
										object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y+k)*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x,p.y+k), object);
									}
									else {
										object = ResourcesManager.getObjects().get("firedown").copy();
										object.setPosition(new Point(p.x*ResourcesManager.getSize(), (p.y+k)*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x,p.y+k), object);
									}
								}
								/* Sinon on le detruit */
								else {
									map.getAnimatedObjects().get(new Point(p.x ,p.y+k)).destroy();
									/* Si il ne laisse pas passer le feu */
									if ( map.getAnimatedObjects().get(new Point(p.x ,p.y+k)).isFireWall() ) {
										/* On break */
										break;
									}
								}
							}
						}
					}
				}

				/* LEFT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					if ( this.bombs.get(new Point(p.x-k, p.y)) != null ) {
						this.bombs.get(new Point(p.x-k, p.y)).destroy();
					}
					else if ( map.getBlocks()[p.x-k][p.y] == null || (map.getBlocks()[p.x-k][p.y].isDestructible() && map.getAnimatedObjects().get(new Point(p.x-k ,p.y)) == null ) ) {
						if ( map.getGrounds()[p.x-k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firehorizontal").copy();
								object.setPosition(new Point((p.x-k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x-k,p.y), object);
							}
							else {
								object = ResourcesManager.getObjects().get("fireleft").copy();
								object.setPosition(new Point((p.x-k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x-k,p.y), object);
							}
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( !map.getBlocks()[p.x-k][p.y].isDestructible() ) {
							/* Si il ne laisse pas passer le feu */
							if ( map.getBlocks()[p.x-k][p.y].isFireWall() ) {
								/* On break */
								break;
							}
						}
						/* Si il est destructible */
						else {
							/* Si il est présent dans les blocs animé */
							if ( map.getAnimatedObjects().get(new Point(p.x-k ,p.y)) != null ) {
								/* Si il est déjà en train d'être détruit */
								if ( map.getAnimatedObjects().get(new Point(p.x-k ,p.y)).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
									if ( k < bomb.getPower()-1 ) {
										object = ResourcesManager.getObjects().get("firehorizontal").copy();
										object.setPosition(new Point((p.x-k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x-k,p.y), object);
									}
									else {
										object = ResourcesManager.getObjects().get("fireleft").copy();
										object.setPosition(new Point((p.x-k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x-k,p.y), object);
									}
								}
								/* Sinon on le detruit */
								else {
									map.getAnimatedObjects().get(new Point(p.x-k ,p.y)).destroy();
									/* Si il ne laisse pas passer le feu */
									if ( map.getAnimatedObjects().get(new Point(p.x-k ,p.y)).isFireWall() ) {
										/* On break */
										break;
									}
								}
							}
						}
					}
				}

				/* RIGHT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					if ( this.bombs.get(new Point(p.x+k, p.y)) != null ) {
						this.bombs.get(new Point(p.x+k, p.y)).destroy();
					}
					else if ( map.getBlocks()[p.x+k][p.y] == null || (map.getBlocks()[p.x+k][p.y].isDestructible() && map.getAnimatedObjects().get(new Point(p.x+k ,p.y)) == null )) {
						if ( map.getGrounds()[p.x+k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firehorizontal").copy();
								object.setPosition(new Point((p.x+k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x+k,p.y), object);
							}
							else {
								object = ResourcesManager.getObjects().get("fireright").copy();
								object.setPosition(new Point((p.x+k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
								animatedObjects.put(new Point(p.x+k,p.y), object);
							}
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( !map.getBlocks()[p.x+k][p.y].isDestructible() ) {
							/* Si il ne laisse pas passer le feu */
							if ( map.getBlocks()[p.x+k][p.y].isFireWall() ) {
								/* On break */
								break;
							}
						}
						/* Si il est destructible */
						else {
							/* Si il est présent dans les blocs animé */
							if ( map.getAnimatedObjects().get(new Point(p.x+k ,p.y)) != null ) {
								/* Si il est déjà en train d'être détruit */
								if ( map.getAnimatedObjects().get(new Point(p.x+k ,p.y)).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
									if ( k < bomb.getPower()-1 ) {
										object = ResourcesManager.getObjects().get("firehorizontal").copy();
										object.setPosition(new Point((p.x+k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x+k,p.y), object);
									}
									else {
										object = ResourcesManager.getObjects().get("fireright").copy();
										object.setPosition(new Point((p.x+k)*ResourcesManager.getSize(), p.y*ResourcesManager.getSize()));
										animatedObjects.put(new Point(p.x+k,p.y), object);
									}
								}
								/* Sinon on le detruit */
								else {
									map.getAnimatedObjects().get(new Point(p.x+k ,p.y)).destroy();
									/* Si il ne laisse pas passer le feu */
									if ( map.getAnimatedObjects().get(new Point(p.x+k ,p.y)).isFireWall() ) {
										/* On break */
										break;
									}
								}
							}
						}
					}
				}

				/* Puis on supprime la bombe */
				bomb.getPlayer().increaseBombs();
				bombs.remove(entry.getKey());
			}
		}
	}
}