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
import com.klob.Bomberklob.resources.ColisionMapObjects;
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

					/* On crée une nouvelle bombe */
					Bomb bomb = new Bomb(player.getBombSelected(), ResourcesManager.getBombsAnimations().get(player.getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, player);


					/* Coordonnées de la bombe posée */
					bomb.setPosition(ResourcesManager.tileToCo(p.x, p.y));

					/* On joue la musique */
					bomb.playCurrentAnimationSound();

					/* On l'ajoute dans la hash map de bombes */
					this.bombs.put(p, bomb);

					/* Zone dangereuses */
					this.single.map.colisionMapUpdate(bomb);

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
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();

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

									if ( colisionMap.get(point1) == ColisionMapObjects.DANGEROUS_AREA ) {

										int x = point1.x;
										int y = point1.y;

										/* Point.x = 21 */
										/* Point.y = 15 */

										/* On regarde les 8 cases nous entourant */
										for (int h = point1.x-1; h < point1.x+2 ; h++ ) {
											for (int v = point1.y-1; v < point1.y+2 ; v++ ) {	
												if ( colisionMap.get(new Point(h,v)) == ColisionMapObjects.EMPTY ) {
													/* Diagonales */
													if ( point1.x != h && point1.y != v ) {
														if ( h > point1.x ) {
															if ( v > point1.y ) {
																if ( colisionMap.get(new Point(h-1, v)) == ColisionMapObjects.EMPTY ) {
																	if ( colisionMap.get(new Point(h, v-1)) == ColisionMapObjects.EMPTY ) {
																		x = h;
																		y = v;
																	}
																}															
															}
															else {
																if ( colisionMap.get(new Point(h-1, v)) == ColisionMapObjects.EMPTY ) {
																	if ( colisionMap.get(new Point(h, v+1)) == ColisionMapObjects.EMPTY ) {
																		x = h;
																		y = v;
																	}
																}
															}
														}
														else {
															if ( v > point1.y ) {
																if ( colisionMap.get(new Point(h+1, v)) == ColisionMapObjects.EMPTY ) {
																	if ( colisionMap.get(new Point(h, v-1)) == ColisionMapObjects.EMPTY ) {
																		x = h;
																		y = v;
																	}
																}	
															}
															else {
																if ( colisionMap.get(new Point(h+1, v)) == ColisionMapObjects.EMPTY ) {
																	if ( colisionMap.get(new Point(h, v+1)) == ColisionMapObjects.EMPTY ) {
																		x = h;
																		y = v;
																	}
																}
															}
														}
													}
													else {
														x = h;
														y = v;
													}
												}
											}
										}

										if ( x == point1.x && y == point1.y ) {

											int[][] distance = new int[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
											PlayerAnimations[][] direction = new PlayerAnimations[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
											PlayerAnimations pa = null;

											distance[x][y] = 1;

											if ( colisionMap.get(new Point(x+1, y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(x+1, y)) != ColisionMapObjects.GAPE ) {
												distance[x+1][y] = 1;
												direction[x+1][y] = PlayerAnimations.RIGHT;
											}
											if ( colisionMap.get(new Point(x-1, y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(x-1, y)) != ColisionMapObjects.GAPE) {
												distance[x-1][y] = 1;
												direction[x-1][y] = PlayerAnimations.LEFT;
											}
											if ( colisionMap.get(new Point(x, y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(x, y+1)) != ColisionMapObjects.GAPE ) {
												distance[x][y+1] = 1;
												direction[x][y+1] = PlayerAnimations.DOWN;
											}
											if ( colisionMap.get(new Point(x, y-1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(x, y-1)) != ColisionMapObjects.GAPE ) {
												distance[x][y-1] = 1;
												direction[x][y-1] = PlayerAnimations.UP;
											}
											
											for (int d = 1; d < 50; d++) {
												for ( int h = 0; h < 21 ; h++ ) {
													for ( int v = 0 ; v < 15 ; v++ ) {

														if (distance[h][v] == d) {

															if ( colisionMap.get(new Point(h, v+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(h, v+1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(h, v+1)) != ColisionMapObjects.DAMAGE && distance[h][v+1]==0) {
																if ( colisionMap.get(new Point(h, v+1)) != ColisionMapObjects.DANGEROUS_AREA ) {
																	pa = direction[h][v];
																	break;
																}
																else {
																	direction[h][v+1] = direction[h][v];
																	distance[h][v+1]=d+1;
																}
															}

															if ( colisionMap.get(new Point(h, v-1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(h, v-1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(h, v-1)) != ColisionMapObjects.DAMAGE && distance[h][v-1]==0) {
																if ( colisionMap.get(new Point(h, v-1)) != ColisionMapObjects.DANGEROUS_AREA ) {
																	pa = direction[h][v];
																	break;
																}
																else {
																	direction[h][v-1] = direction[h][v];
																	distance[h][v-1]=d+1;
																}
															}

															if ( colisionMap.get(new Point(h+1, v)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(h+1, v)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(h+1, v)) != ColisionMapObjects.DAMAGE && distance[h+1][v]==0) {
																if ( colisionMap.get(new Point(h+1, v)) != ColisionMapObjects.DANGEROUS_AREA ) {
																	pa = direction[h][v];
																	break;
																}
																else {
																	direction[h+1][v] = direction[h][v];
																	distance[h+1][v]=d+1;
																}
															}

															if ( colisionMap.get(new Point(h-1, v)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(h-1, v)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(h-1, v)) != ColisionMapObjects.DAMAGE && distance[h-1][v]==0) {
																if ( colisionMap.get(new Point(h-1, v)) != ColisionMapObjects.DANGEROUS_AREA ) {
																	pa = direction[h][v];
																	break;
																}
																else {
																	direction[h-1][v] = direction[h][v];
																	distance[h-1][v]=d+1;
																}
															}
														}
													}

													if ( pa != null ) {
														break;
													}
												}

												if ( pa != null ) {
													break;
												}
											}	

											if ( pa == PlayerAnimations.RIGHT) {
												x += 1;										
											}
											else if ( pa == PlayerAnimations.LEFT) {
												x -= 1;								
											}
											else if ( pa == PlayerAnimations.UP ) {
												y -= 1;						
											}
											else if ( pa == PlayerAnimations.DOWN ) {
												y += 1;									
											}											
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
								if ( (this.single.map.getAnimatedObjects().get(point1) != null && this.single.map.getAnimatedObjects().get(point1).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point2) != null && this.single.map.getAnimatedObjects().get(point2).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point3) != null && this.single.map.getAnimatedObjects().get(point3).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(point4) != null && this.single.map.getAnimatedObjects().get(point4).getDamage() != 0)  ) {
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
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();


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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE ) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y--;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE) {
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
					}
					else {
						if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE ) {
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
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.y < (this.size*(this.single.map.getHeight()-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().y == this.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x, this.y+this.size);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y+this.size-1);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE ) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y++;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE ) {
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
					}
					else {
						if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE ) {
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
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.x < (this.size*(this.single.map.getWidth()-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().x == this.x) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x+this.size, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x+this.size-1, this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE ) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x++;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE ) {
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
					}
					else {
						if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE) {
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
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();

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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE ) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x--;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE ) {
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
					}
					else {
						if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE ) {
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

		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();
		ConcurrentHashMap<Point, Objects> animatedObjects = this.single.map.getAnimatedObjects();
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
				colisionMap.put(p, ColisionMapObjects.DAMAGE);

				/* UP */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x, p.y-k)) != null ) {
						this.bombs.get(new Point(p.x, p.y-k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap.get(new Point (p.x,p.y-k)) != ColisionMapObjects.BLOCK ) {
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
						if ( colisionMap.get(new Point (p.x,p.y-k)) != ColisionMapObjects.GAPE ) {
							colisionMap.put(new Point(p.x,p.y-k), ColisionMapObjects.DAMAGE);
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(new Point(p.x,p.y-k)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap.get(new Point(p.x,p.y-k)) == ColisionMapObjects.DAMAGE ) {
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
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(new Point(p.x ,p.y-k)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(new Point(p.x ,p.y-k)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* DOWN */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x, p.y+k)) != null ) {
						this.bombs.get(new Point(p.x, p.y+k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap.get(new Point (p.x,p.y+k)) != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
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
						if ( colisionMap.get(new Point (p.x,p.y+k)) != ColisionMapObjects.GAPE ) {
							colisionMap.put(new Point(p.x,p.y+k), ColisionMapObjects.DAMAGE);
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(new Point(p.x,p.y+k)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap.get(new Point(p.x,p.y+k)) == ColisionMapObjects.DAMAGE ) {
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
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(new Point(p.x ,p.y+k)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(new Point(p.x ,p.y+k)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* LEFT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x-k, p.y)) != null ) {
						this.bombs.get(new Point(p.x-k, p.y)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap.get(new Point (p.x-k,p.y)) != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
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
						if ( colisionMap.get(new Point (p.x-k,p.y)) != ColisionMapObjects.GAPE ) {
							colisionMap.put(new Point(p.x-k,p.y), ColisionMapObjects.DAMAGE);
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(new Point(p.x-k,p.y)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap.get(new Point(p.x-k,p.y)) == ColisionMapObjects.DAMAGE ) {
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
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(new Point(p.x-k ,p.y)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(new Point(p.x-k ,p.y)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* RIGHT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x+k, p.y)) != null ) {
						this.bombs.get(new Point(p.x+k, p.y)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap.get(new Point (p.x+k,p.y)) != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
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
						if ( colisionMap.get(new Point (p.x+k,p.y)) != ColisionMapObjects.GAPE ) {
							colisionMap.put(new Point(p.x+k,p.y), ColisionMapObjects.DAMAGE );
						}
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(new Point(p.x+k,p.y)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap.get(new Point(p.x+k,p.y)) == ColisionMapObjects.DAMAGE ) {
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
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(new Point(p.x+k ,p.y)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(new Point(p.x+k ,p.y)).isFireWall() ) {
								/* On break */
								break;
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