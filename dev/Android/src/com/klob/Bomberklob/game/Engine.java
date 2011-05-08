package com.klob.Bomberklob.game;

import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Canvas;
import android.util.Log;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.BotPlayer;
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

					/* Zones dangereuses */
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
					if ( players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
						if ( players[i].hasAnimationFinished() ) {
							/* On met joueur à null ce qui stopera le thread d'écoute seulement pour le joueur humain */
							players[i].setPosition(null);
						}
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
							if ( i != 0 && players[i].getPosition() != null ) {
								/*Si le bot n'a pas d'objectif*/
								if ( players[i].getObjectif() == null ) {

									Point p = point1;

									/* Defensif (On est dans une zone dangereuse) */
									if ( colisionMap.get(point1) == ColisionMapObjects.DANGEROUS_AREA || colisionMap.get(point1) == ColisionMapObjects.BOMB) {

										p = safeAroundArea(point1);

										if ( p.x == point1.x && p.y == point1.y ) {
											p = pathFinding(p, colisionMap);									
										}
									}
									/* Offensif */
									else {
										int difficulty = ((BotPlayer) players[i]).getDifficulty();
										if ( (int)(Math.random() * (20-(10*difficulty))) == 0) {
											if ( (int)(Math.random() * (8-(3*difficulty))) == 1) {
												if ( difficulty != 0) {
													/* Recuperer le tableau de zones dangeureuses */
													ConcurrentHashMap<Point, ColisionMapObjects> colisionMap2 = new ConcurrentHashMap<Point, ColisionMapObjects>();

													/* Le recopier */
													colisionMap2.putAll(colisionMap);

													/* Ajouter une bombe */
													Point bombPoint = ResourcesManager.coToTile(players[i].getPosition().x+(ResourcesManager.getSize()/2), players[i].getPosition().y+(ResourcesManager.getSize()/2));

													if ( this.bombs.get(bombPoint) == null ) {
														if ( players[i].getBombNumber() > 0 ) {

															/* On crée une nouvelle bombe */
															Bomb bomb = new Bomb(players[i].getBombSelected(), ResourcesManager.getBombsAnimations().get(players[i].getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, players[i]);

															/* Coordonnées de la bombe posée */
															bomb.setPosition(ResourcesManager.tileToCo(bombPoint.x, bombPoint.y));

															boolean up, down, left, right;
															up = down = left = right = true;

															/* CENTER */
															colisionMap2.put(bombPoint,ColisionMapObjects.BOMB);

															for ( int k = 1 ; k < bomb.getPower() ; k++ ) {

																if ( up ) {
																	if ( colisionMap2.get(new Point(bombPoint.x, bombPoint.y-k)) != ColisionMapObjects.BLOCK && colisionMap2.get(new Point(bombPoint.x, bombPoint.y-k)) != ColisionMapObjects.BOMB ) {
																		colisionMap2.put(new Point(bombPoint.x, bombPoint.y-k),ColisionMapObjects.DANGEROUS_AREA);
																	}
																	else {
																		up = false;
																	}
																}

																if ( down ) {
																	if ( colisionMap2.get(new Point(bombPoint.x, bombPoint.y+k)) != ColisionMapObjects.BLOCK && colisionMap2.get(new Point(bombPoint.x, bombPoint.y+k)) != ColisionMapObjects.BOMB ) {
																		colisionMap2.put(new Point(bombPoint.x, bombPoint.y+k),ColisionMapObjects.DANGEROUS_AREA);
																	}
																	else {
																		down = false;
																	}
																}

																if ( left ) {
																	if ( colisionMap2.get(new Point(bombPoint.x-k, bombPoint.y)) != ColisionMapObjects.BLOCK && colisionMap2.get(new Point(bombPoint.x-k, bombPoint.y)) != ColisionMapObjects.BOMB ) {
																		colisionMap2.put(new Point(bombPoint.x-k, bombPoint.y),ColisionMapObjects.DANGEROUS_AREA);
																	}
																	else {
																		left = false;
																	}
																}

																if ( right ) {
																	if ( colisionMap2.get(new Point(bombPoint.x+k, bombPoint.y)) != ColisionMapObjects.BLOCK && colisionMap2.get(new Point(bombPoint.x+k, bombPoint.y)) != ColisionMapObjects.BOMB ) {
																		colisionMap2.put(new Point(bombPoint.x+k, bombPoint.y),ColisionMapObjects.DANGEROUS_AREA);
																	}
																	else {
																		right = false;
																	}
																}
															}

															/* Chercher une chemin de sortie */
															Point res = pathFinding(p, colisionMap2);

															/* Si il existe une sortie */
															if ( res.x != point1.x || res.y != point1.y ) {

																/* On l'ajoute dans la hash map de bombes */
																this.bombs.put(bombPoint, bomb);

																/* Zones dangereuses */
																this.single.map.setColisionMap(colisionMap2);

																/* On diminue la quantité des bombes que peut poser le joueur */
																try {
																	players[i].setBombNumber(players[i].getBombNumber()-1);
																} catch (BombPowerException e) {
																	e.printStackTrace();
																}
																
																/* On joue la musique */
																bomb.playCurrentAnimationSound();
															}
														}
													}
												}
												else {
													pushBomb(players[i]);
												}
											}
											else {									
												/* bouger d'une case */
												p = safeAroundArea(point1);
											}
										}
									}

									p.x = p.x*ResourcesManager.getSize();
									p.y = p.y*ResourcesManager.getSize();
									players[i].setObjectif(p);
								}
								else {
									Point objectif = players[i].getObjectif();

									/* Si on a atteind l'objectif */
									if ( point.x == objectif.x && point.y == objectif.y ) {
										players[i].setObjectif(null);
										stopPlayer(players[i]);
									}
									else if ( point.x < objectif.x && point.y < objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN_RIGHT);
									}
									else if ( point.x > objectif.x && point.y < objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN_LEFT);
									}
									else if ( point.x > objectif.x && point.y > objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP_LEFT);
									}
									else if ( point.x < objectif.x && point.y > objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP_RIGHT);
									}
									else if ( point.x < objectif.x ) {
										players[i].setCurrentAnimation(PlayerAnimations.RIGHT);
									}
									else if ( point.x > objectif.x  ) {
										players[i].setCurrentAnimation(PlayerAnimations.LEFT);
									}
									else if ( point.y > objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP);
									}
									else if ( point.y < objectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN);
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
					}
					players[i].update();
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

	public void stopPlayer(Player player) {

		String animation = player.getCurrentAnimation();

		if ( animation == PlayerAnimations.RIGHT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_RIGHT);
		}
		else if ( animation == PlayerAnimations.LEFT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_LEFT);
		}
		else if ( animation == PlayerAnimations.UP.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_UP);
		}
		else if ( animation == PlayerAnimations.DOWN.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_DOWN);
		}
		else if ( animation == PlayerAnimations.DOWN_RIGHT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_DOWN_RIGHT);
		}
		else if ( animation == PlayerAnimations.DOWN_LEFT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_DOWN_LEFT);
		}
		else if ( animation == PlayerAnimations.UP_RIGHT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_UP_RIGHT);
		}
		else if ( animation == PlayerAnimations.UP_LEFT.getLabel()) {
			player.setCurrentAnimation(PlayerAnimations.STOP_UP_LEFT);
		}
	}

	public Point safeAroundArea(Point point1) {

		int x = point1.x;
		int y = point1.y;
		ConcurrentHashMap<Point, ColisionMapObjects> colisionMap = this.single.map.getColisionMap();

		Vector<Integer> vect = new Vector<Integer>();
		vect.add(1);
		vect.add(2);
		vect.add(3);
		vect.add(4);
		vect.add(5);
		vect.add(6);
		vect.add(7);
		vect.add(8);


		do {
			int i = vect.remove((int)(Math.random() * vect.size()));

			switch (i) {            
			case 1:
				if ( colisionMap.get(new Point(x-1,y)) == ColisionMapObjects.EMPTY ) {
					x--;
				}
				break;
			case 2:
				if ( colisionMap.get(new Point(x+1,y)) == ColisionMapObjects.EMPTY ) {
					x++;
				}
				break;
			case 3:
				if ( colisionMap.get(new Point(x,y-1)) == ColisionMapObjects.EMPTY ) {
					y--;
				}
				break;
			case 4:
				if ( colisionMap.get(new Point(x,y+1)) == ColisionMapObjects.EMPTY ) {
					y++;
				}
				break;
			case 5:
				if ( colisionMap.get(new Point(x-1,y-1)) == ColisionMapObjects.EMPTY ) {
					if ( colisionMap.get(new Point(x-1, y)) == ColisionMapObjects.EMPTY ) {
						if ( colisionMap.get(new Point(x, y-1)) == ColisionMapObjects.EMPTY ) {
							x--;
							y--;
						}
					}
				}
				break;
			case 6:
				if ( colisionMap.get(new Point(x+1,y+1)) == ColisionMapObjects.EMPTY ) {
					if ( colisionMap.get(new Point(x+1, y)) == ColisionMapObjects.EMPTY ) {
						if ( colisionMap.get(new Point(x, y+1)) == ColisionMapObjects.EMPTY ) {
							x++;
							y++;
						}
					}
				}
				break;
			case 7:
				if ( colisionMap.get(new Point(x+1,y-1)) == ColisionMapObjects.EMPTY ) {
					if ( colisionMap.get(new Point(x+1, y)) == ColisionMapObjects.EMPTY ) {
						if ( colisionMap.get(new Point(x, y-1)) == ColisionMapObjects.EMPTY ) {
							x++;
							y--;
						}
					}
				}
				break;
			case 8:
				if ( colisionMap.get(new Point(x-1,y+1)) == ColisionMapObjects.EMPTY ) {
					if ( colisionMap.get(new Point(x-1, y)) == ColisionMapObjects.EMPTY ) {
						if ( colisionMap.get(new Point(x, y+1)) == ColisionMapObjects.EMPTY ) {
							x--;
							y++;
						}
					}
				}
				break;
			}
		} while ( !vect.isEmpty() && x == point1.x && y == point1.y );

		return new Point(x,y);
	}

	public Point pathFinding(Point p, ConcurrentHashMap<Point, ColisionMapObjects> colisionMap) {

		int[][] distance = new int[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
		PlayerAnimations[][] direction = new PlayerAnimations[ResourcesManager.MAP_WIDTH][ResourcesManager.MAP_HEIGHT];
		PlayerAnimations pa = null;

		distance[p.x][p.y] = 1;

		if ( colisionMap.get(new Point(p.x+1, p.y)) == ColisionMapObjects.DANGEROUS_AREA ) {
			distance[p.x+1][p.y] = 1;
			direction[p.x+1][p.y] = PlayerAnimations.RIGHT;
		}
		if ( colisionMap.get(new Point(p.x-1, p.y)) == ColisionMapObjects.DANGEROUS_AREA ) {
			distance[p.x-1][p.y] = 1;
			direction[p.x-1][p.y] = PlayerAnimations.LEFT;
		}
		if ( colisionMap.get(new Point(p.x, p.y+1)) == ColisionMapObjects.DANGEROUS_AREA ) {
			distance[p.x][p.y+1] = 1;
			direction[p.x][p.y+1] = PlayerAnimations.DOWN;
		}
		if ( colisionMap.get(new Point(p.x, p.y-1)) == ColisionMapObjects.DANGEROUS_AREA ) {
			distance[p.x][p.y-1] = 1;
			direction[p.x][p.y-1] = PlayerAnimations.UP;
		}

		for (int d = 1; d < 10; d++) {
			for ( int h = 1; h < ResourcesManager.MAP_WIDTH-1 ; h++ ) {
				for ( int v = 1 ; v < ResourcesManager.MAP_HEIGHT-1 ; v++ ) {

					if (distance[h][v] == d) {

						if ( distance[h][v+1]==0 ) {
							if ( colisionMap.get(new Point(h, v+1)) == ColisionMapObjects.EMPTY ) {
								pa = direction[h][v];
								break;
							}
							else if ( colisionMap.get(new Point(h, v+1)) == ColisionMapObjects.DANGEROUS_AREA ){
								direction[h][v+1] = direction[h][v];
								distance[h][v+1]=d+1;
							}
						}

						if ( distance[h][v-1]==0 ) {
							if ( colisionMap.get(new Point(h, v-1)) == ColisionMapObjects.EMPTY ) {
								pa = direction[h][v];
								break;
							}
							else if ( colisionMap.get(new Point(h, v-1)) == ColisionMapObjects.DANGEROUS_AREA ) {
								direction[h][v-1] = direction[h][v];
								distance[h][v-1]=d+1;
							}
						}

						if ( distance[h+1][v]==0 ) {
							if ( colisionMap.get(new Point(h+1, v)) == ColisionMapObjects.EMPTY ) {
								pa = direction[h][v];
								break;
							}
							else if ( colisionMap.get(new Point(h+1, v)) == ColisionMapObjects.DANGEROUS_AREA ) {
								direction[h+1][v] = direction[h][v];
								distance[h+1][v]=d+1;
							}
						}

						if ( distance[h-1][v]==0 ) {
							if ( colisionMap.get(new Point(h-1, v)) == ColisionMapObjects.EMPTY ) {
								pa = direction[h][v];
								break;
							}
							else if ( colisionMap.get(new Point(h-1, v)) == ColisionMapObjects.DANGEROUS_AREA ){
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
			return new Point(p.x+1,p.y);										
		}
		else if ( pa == PlayerAnimations.LEFT) {
			return new Point(p.x-1,p.y);
		}
		else if ( pa == PlayerAnimations.UP ) {
			return new Point(p.x,p.y-1);					
		}
		else if ( pa == PlayerAnimations.DOWN ) {
			return new Point(p.x,p.y+1);								
		}
		else {
			return p;
		}
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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE  && colisionMap.get(this.nextTile) != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y--;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BOMB) {
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
						if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BOMB) {
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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE && colisionMap.get(this.nextTile) != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y++;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BOMB) {
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
						if ( colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x+1 , this.nextTile.y)) != ColisionMapObjects.BOMB) {
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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE && colisionMap.get(this.nextTile) != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x++;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BOMB) {
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
						if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BOMB) {
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
					if ( colisionMap.get(this.nextTile) != ColisionMapObjects.BLOCK && colisionMap.get(this.nextTile) != ColisionMapObjects.GAPE && colisionMap.get(this.nextTile) != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x--;
						}
						else {
							if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BOMB) {
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
						if ( colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BLOCK && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.GAPE && colisionMap.get(new Point(this.nextTile.x , this.nextTile.y+1)) != ColisionMapObjects.BOMB) {
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
					else if ( colisionMap.get(new Point (p.x,p.y-k)) != ColisionMapObjects.BLOCK) {
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