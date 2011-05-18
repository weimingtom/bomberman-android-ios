package com.klob.Bomberklob.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.klob.Bomberklob.resources.PathFindingNode;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class Engine {

	private Single single;

	private Point nextTile = new Point(), currentTile = new Point(), tileUpLeft, tileUpRight, tileDownLeft, tileDownRight, playerPosition, playerObjectif;
	private int size,x, y;


	private boolean bombBoolean = true;
	private Thread bombThread;	
	private ConcurrentHashMap<Point, Bomb> bombs;	
	
	private Iterator<Bomb> bombsIterator;

	/* Pathfinding */
	private Map<Point, PathFindingNode> openList;
	private Map<Point, PathFindingNode> closeList;
	private PathFindingNode wall = new PathFindingNode(0, 0, 0, null);

	private Map<Point, Integer> distance;
	private Map<Point, PlayerAnimations> direction;
	private List<Integer> vect;

	/* Constructeur -------------------------------------------------------- */

	public Engine(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		this.single = new Single(mapName, enemies, gametype, random, difficulty);
		this.size = ResourcesManager.getSize();
		this.bombs = new ConcurrentHashMap<Point, Bomb>();
		this.x = 0;
		this.y = 0;
		this.openList = new HashMap<Point, PathFindingNode>();
		this.closeList = new HashMap<Point, PathFindingNode>();
		this.distance = new HashMap<Point, Integer>();
		this.direction = new HashMap<Point, PlayerAnimations>();
		this.vect = new ArrayList<Integer>();
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
		for (this.bombsIterator = bombs.values().iterator() ; this.bombsIterator.hasNext() ;){
			this.bombsIterator.next().onDraw(canvas, size); 
		}
	}

	public void update() {
		Player[] players = this.single.getPlayers();
		ColisionMapObjects[][] colisionMap;

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
							this.playerObjectif = players[i].getObjectif();
							this.playerPosition = players[i].getPosition();
							colisionMap = this.single.map.getColisionMap();
							this.tileUpLeft = ResourcesManager.coToTile(this.playerPosition.x,this.playerPosition.y);
							this.tileUpRight = ResourcesManager.coToTile(this.playerPosition.x+ResourcesManager.getSize()-1,this.playerPosition.y);
							this.tileDownRight = ResourcesManager.coToTile(this.playerPosition.x+ResourcesManager.getSize()-1,this.playerPosition.y+ResourcesManager.getSize()-1);
							this.tileDownLeft = ResourcesManager.coToTile(this.playerPosition.x,this.playerPosition.y+ResourcesManager.getSize()-1);

							/* IA */
							if ( i != 0 && players[i].getPosition() != null ) {
								/*Si le bot n'a pas d'objectif*/
								if ( this.playerObjectif == null || (this.playerPosition.x == this.playerObjectif.x && this.playerPosition.y == this.playerObjectif.y)) {

									int difficulty = ((BotPlayer) players[i]).getDifficulty();

									/* Defensif (On est dans une zone dangereuse) */
									if ( colisionMap[this.tileUpLeft.x][this.tileUpLeft.y] == ColisionMapObjects.DANGEROUS_AREA || colisionMap[this.tileUpLeft.x][this.tileUpLeft.y] == ColisionMapObjects.BOMB) {

										this.playerObjectif = safeAroundArea(this.tileUpLeft, colisionMap);                

										if ( this.playerObjectif.x == this.tileUpLeft.x && this.playerObjectif.y == this.tileUpLeft.y ) {
											this.playerObjectif = pathFinding(this.tileUpLeft, colisionMap);
										}
									}
									/* Offensif */
									else {

										int timeBomb = Integer.MAX_VALUE;

										for(Point entry : bombs.keySet()) {
											if ( players[i].equals(bombs.get(entry).getPlayer())) {
												timeBomb = Math.min(bombs.get(entry).getTime(),timeBomb);
											}
										}

										if ( timeBomb > 15 ) {
											if ( (int)(Math.random() * (20-(10*difficulty))) == 0) {
												if ( 0 != difficulty && players[0].getPosition() != null ) {
													this.playerObjectif = pathFinding(this.tileUpLeft, ResourcesManager.coToTile(players[0].getPosition().x, players[0].getPosition().y),colisionMap);
													if ( colisionMap[this.playerObjectif.x][this.playerObjectif.y] == ColisionMapObjects.BLOCK && players[i].getBombNumber() > 0) {
														this.playerObjectif = iaPushBomb(players[i], colisionMap.clone());
													}
													else if ( colisionMap[this.playerObjectif.x][this.playerObjectif.y] != ColisionMapObjects.EMPTY ) {
														this.playerObjectif.x = this.tileUpLeft.x;
														this.playerObjectif.y = this.tileUpLeft.y;
													}
												}
												else {
													this.playerObjectif = safeAroundArea(this.tileUpLeft, colisionMap);
												}
											}
										}
										else {
											this.playerObjectif.x = this.tileUpLeft.x;
											this.playerObjectif.y = this.tileUpLeft.y;
										}
									}
									this.playerObjectif.x = this.playerObjectif.x*ResourcesManager.getSize();
									this.playerObjectif.y = this.playerObjectif.y*ResourcesManager.getSize();									
								}

								if ( this.playerObjectif == null || this.playerPosition.x != this.playerObjectif.x || this.playerPosition.y != this.playerObjectif.y ) {

									if ( this.playerPosition.x < this.playerObjectif.x && this.playerPosition.y < this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN_RIGHT);
									}
									else if ( this.playerPosition.x > this.playerObjectif.x && this.playerPosition.y < this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN_LEFT);
									}
									else if ( this.playerPosition.x > this.playerObjectif.x && this.playerPosition.y > this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP_LEFT);
									}
									else if ( this.playerPosition.x < this.playerObjectif.x && this.playerPosition.y > this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP_RIGHT);
									}
									else if ( this.playerPosition.x < this.playerObjectif.x ) {
										players[i].setCurrentAnimation(PlayerAnimations.RIGHT);
									}
									else if ( this.playerPosition.x > this.playerObjectif.x  ) {
										players[i].setCurrentAnimation(PlayerAnimations.LEFT);
									}
									else if ( this.playerPosition.y > this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.UP);
									}
									else if ( this.playerPosition.y < this.playerObjectif.y ) {
										players[i].setCurrentAnimation(PlayerAnimations.DOWN);
									}
									players[i].setObjectif(this.playerObjectif);
									move(players[i]);
								}
								else {
									stopPlayer(players[i]);
								}
							}
							else {
								move(players[i]);
							}


							/* On vérifie que le joueur n'est pas sur une case lui causant des dommages */
							if ( players[i].isDestructible() ) {
								if ( (this.single.map.getAnimatedObjects().get(this.tileUpLeft) != null && this.single.map.getAnimatedObjects().get(this.tileUpLeft).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(this.tileUpRight) != null && this.single.map.getAnimatedObjects().get(this.tileUpRight).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(this.tileDownRight) != null && this.single.map.getAnimatedObjects().get(this.tileDownRight).getDamage() != 0) || (this.single.map.getAnimatedObjects().get(this.tileDownLeft) != null && this.single.map.getAnimatedObjects().get(this.tileDownLeft).getDamage() != 0)  ) {
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

	public Point safeAroundArea(Point point1, ColisionMapObjects[][] colisionMap) {

		int x = point1.x;
		int y = point1.y;

		vect.clear();
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
				if ( colisionMap[x-1][y] == ColisionMapObjects.EMPTY ) {
					x--;
				}
				break;
			case 2:
				if ( colisionMap[x+1][y] == ColisionMapObjects.EMPTY ) {
					x++;
				}
				break;
			case 3:
				if ( colisionMap[x][y-1] == ColisionMapObjects.EMPTY ) {
					y--;
				}
				break;
			case 4:
				if ( colisionMap[x][y+1] == ColisionMapObjects.EMPTY ) {
					y++;
				}
				break;
			case 5:
				if ( colisionMap[x-1][y-1] == ColisionMapObjects.EMPTY ) {
					if ( colisionMap[x-1][y] == ColisionMapObjects.EMPTY ) {
						if ( colisionMap[x][y-1] == ColisionMapObjects.EMPTY ) {
							x--;
							y--;
						}
					}
				}
				break;
			case 6:
				if ( colisionMap[x+1][y+1] == ColisionMapObjects.EMPTY ) {
					if ( colisionMap[x+1][y] == ColisionMapObjects.EMPTY ) {
						if ( colisionMap[x][y+1] == ColisionMapObjects.EMPTY ) {
							x++;
							y++;
						}
					}
				}
				break;
			case 7:
				if ( colisionMap[x+1][y-1] == ColisionMapObjects.EMPTY ) {
					if ( colisionMap[x+1][y] == ColisionMapObjects.EMPTY ) {
						if ( colisionMap[x][y-1] == ColisionMapObjects.EMPTY ) {
							x++;
							y--;
						}
					}
				}
				break;
			case 8:
				if ( colisionMap[x-1][y+1] == ColisionMapObjects.EMPTY ) {
					if ( colisionMap[x-1][y] == ColisionMapObjects.EMPTY ) {
						if ( colisionMap[x][y+1] == ColisionMapObjects.EMPTY ) {
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

	public Point pathFinding(Point playerTile, ColisionMapObjects[][] colisionMap) {

		PlayerAnimations pa = null;
		Point point = new Point(), position;

		this.distance.put(ResourcesManager.getPoint(playerTile.x, playerTile.y), 1);

		if ( colisionMap[playerTile.x+1][playerTile.y] == ColisionMapObjects.DANGEROUS_AREA ) {
			this.distance.put(ResourcesManager.getPoint(playerTile.x+1, playerTile.y), 1);
			this.direction.put(ResourcesManager.getPoint(playerTile.x+1, playerTile.y), PlayerAnimations.RIGHT);
		}

		if ( colisionMap[playerTile.x-1][playerTile.y] == ColisionMapObjects.DANGEROUS_AREA ) {
			this.distance.put(ResourcesManager.getPoint(playerTile.x-1, playerTile.y), 1);
			this.direction.put(ResourcesManager.getPoint(playerTile.x-1, playerTile.y), PlayerAnimations.LEFT);
		}

		if ( colisionMap[playerTile.x][playerTile.y+1] == ColisionMapObjects.DANGEROUS_AREA ) {
			this.distance.put(ResourcesManager.getPoint(playerTile.x, playerTile.y+1), 1);
			this.direction.put(ResourcesManager.getPoint(playerTile.x, playerTile.y+1), PlayerAnimations.DOWN);
		}

		if ( colisionMap[playerTile.x][playerTile.y-1] == ColisionMapObjects.DANGEROUS_AREA ) {
			this.distance.put(ResourcesManager.getPoint(playerTile.x, playerTile.y-1), 1);
			this.direction.put(ResourcesManager.getPoint(playerTile.x, playerTile.y-1), PlayerAnimations.UP);
		}

		for (int d = 1; d < 20; d++) {
			for ( int h = 1; h < ResourcesManager.MAP_WIDTH-1 ; h++ ) {

				for ( int v = 1 ; v < ResourcesManager.MAP_HEIGHT-1 ; v++ ) {

					point.x = h;
					point.y = v;
					/* FIXME */
					point = new Point(h,v);

					if ( this.distance.get(point) == Integer.valueOf(d) ) {

						point.y = v+1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								point.y = v;
								pa = this.direction.get(point);
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ){
								point.y = v;
								position = new Point(h, v+1);
								this.direction.put(position, this.direction.get(point));
								this.distance.put(position, d+1);
							}
						}

						point.y = v-1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								point.y = v;
								pa = this.direction.get(point);
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ) {
								point.y = v;
								position = new Point(h, v-1);
								this.direction.put(position, this.direction.get(point));
								this.distance.put(position, d+1);
							}
						}

						point.x = h+1;
						point.y = v;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								point.x = h;
								pa = this.direction.get(point);
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ) {
								point.x = h;
								position = new Point(h+1, v);
								this.direction.put(position, this.direction.get(point));
								this.distance.put(position, d+1);
							}
						}

						point.x = h-1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								point.x = h;
								pa = this.direction.get(point);
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ){
								point.x = h;
								position = new Point(h-1, v);
								this.direction.put(position, this.direction.get(point));
								this.distance.put(position, d+1);
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
		
		this.distance.clear();
		this.direction.clear();

		if ( pa == PlayerAnimations.RIGHT) {
			point.x = playerTile.x+1;
			point.y = playerTile.y;
			return point;                                                                           
		}
		else if ( pa == PlayerAnimations.LEFT) {
			point.x = playerTile.x-1;
			point.y = playerTile.y;
			return point;   
		}
		else if ( pa == PlayerAnimations.UP ) {
			point.x = playerTile.x;
			point.y = playerTile.y-1;
			return point;                                   
		}
		else if ( pa == PlayerAnimations.DOWN ) {
			point.x = playerTile.x;
			point.y = playerTile.y+1;
			return point;                                                           
		}
		else {
			point.x = playerTile.x;
			point.y = playerTile.y;
			return point;
		}
	}

	public Point pathFinding(Point sourcePoint, Point destinationPoint, ColisionMapObjects[][] colisionMap) {

		ConcurrentHashMap<Point, Objects> animatedObjects = this.single.map.getAnimatedObjects();
		int H, F, G, minimumStrok;
		
		Point tile = new Point();
		Point source = new Point();
		Point currentTile = new Point();
		source.x = currentTile.x = sourcePoint.x;
		source.y = currentTile.y = sourcePoint.y;
		openList.put(source, new PathFindingNode(0, 0, 0, source));

		do {

			minimumStrok = Integer.MAX_VALUE;
			for (Point entry : openList.keySet()) {
				if ( openList.get(entry).F <= minimumStrok) {
					minimumStrok = openList.get(entry).F;
					currentTile = entry;
				}
			}
			
			closeList.put(currentTile, openList.remove(currentTile));

			for (int i = currentTile.x-1 ; i < currentTile.x+2 ; i++ ) {
				for (int j = currentTile.y-1 ; j < currentTile.y+2 ; j++ ) {

					tile.set(i, j);

					/* Si la case en cours ne fait pas partie des ignorées ou de la closedList */
					if ( closeList.get(tile) == null) {
						
						/* Si l'objet est un bloc */
						if ( colisionMap[i][j] == ColisionMapObjects.BLOCK ) {

							/* Si il est destructible */
							if ( animatedObjects.get(tile) != null ) {

								/* Si il n'est pas sur une diagonale */
								if ( i == currentTile.x || j == currentTile.y ) {

									/* On calcule l'heuristique */
									H = (Math.abs(destinationPoint.x-i) + Math.abs(destinationPoint.y-j))*10;

									/* Le coût du déplacement */
									G = 40+closeList.get(currentTile).G;

									/* Et le coût total du chemin */
									F = G+H;

									/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
									if ( openList.get(tile) == null ) {
										openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
									}
									else if ( G < openList.get(tile).G ) {
										openList.get(tile).F = F;
										openList.get(tile).G = G;
										openList.get(tile).father = currentTile;
									}
								}
							}
							else {
								/* On l'ajoute à la closeList */
								closeList.put(new Point(i,j), this.wall);
							}
						}
						else if ( colisionMap[i][j] == ColisionMapObjects.EMPTY ) {

							/* On calcule l'heuristique */
							H = (Math.abs(destinationPoint.x-i) + Math.abs(destinationPoint.y-j))*10;

							/* Si il n'est pas sur une diagonale */
							if ( i == currentTile.x || j == currentTile.y ) {

								/* Le coût du déplacement */
								G = 10+closeList.get(currentTile).G;

								/* Et le coût total du chemin */
								F = G+H;

								/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
								if ( openList.get(tile) == null ) {										
									//System.out.print(" RAJOUT : " + tile.toString() );
									openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
								}
								else if ( G < openList.get(tile).G ) {
									openList.get(tile).F = F;
									openList.get(tile).G = G;
									openList.get(tile).father = currentTile;
								}
							}
							else {  
								/* Seulement si elle n'est pas gênée par une autre case càd par une case à droite ou au dessous, diagonale oblige ! */ 
								if ( i > currentTile.x && colisionMap[currentTile.x+1][currentTile.y] == ColisionMapObjects.EMPTY) {
									if ( j > currentTile.y && colisionMap[currentTile.x][currentTile.y+1] == ColisionMapObjects.EMPTY) {

										/* Le coût du déplacement */
										G = 14+closeList.get(currentTile).G;

										/* Et le coût total du chemin */
										F = G+H;                                                        
                                                           
										/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
										if ( openList.get(tile) == null ) {										
											//System.out.print(" RAJOUT : " + tile.toString() );
											openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
										}
										else if ( G < openList.get(tile).G ) {
											openList.get(tile).F = F;
											openList.get(tile).G = G;
											openList.get(tile).father = currentTile;
										}
									}
									else if ( colisionMap[currentTile.x][currentTile.y-1] == ColisionMapObjects.EMPTY ){

										/* Le coût du déplacement */
										G = 14+closeList.get(currentTile).G;

										/* Et le coût total du chemin */
										F = G+H;                                                        
                                                           
										/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
										if ( openList.get(tile) == null ) {										
											//System.out.print(" RAJOUT : " + tile.toString() );
											openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
										}
										else if ( G < openList.get(tile).G ) {
											openList.get(tile).F = F;
											openList.get(tile).G = G;
											openList.get(tile).father = currentTile;
										}
									}
								}
								else if ( i < currentTile.x && colisionMap[currentTile.x-1][currentTile.y] == ColisionMapObjects.EMPTY) {
									if ( j > currentTile.y && colisionMap[currentTile.x][currentTile.y+1] == ColisionMapObjects.EMPTY) {

										/* Le coût du déplacement */
										G = 14+closeList.get(currentTile).G;

										/* Et le coût total du chemin */
										F = G+H;                                                        
                                                          
										/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
										if ( openList.get(tile) == null ) {										
											//System.out.print(" RAJOUT : " + tile.toString() );
											openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
										}
										else if ( G < openList.get(tile).G ) {
											openList.get(tile).F = F;
											openList.get(tile).G = G;
											openList.get(tile).father = currentTile;
										}
									}
									else if ( colisionMap[currentTile.x][currentTile.y-1] == ColisionMapObjects.EMPTY ){

										/* Le coût du déplacement */
										G = 14+closeList.get(currentTile).G;

										/* Et le coût total du chemin */
										F = G+H;
										
										/* Puis on l'ajoute dans la liste des cases à parcourir si elle n'existe pas ou si le nouveau coût est moindre */
										if ( openList.get(tile) == null ) {										
											//System.out.print(" RAJOUT : " + tile.toString() );
											openList.put(new Point(i,j), new PathFindingNode(F, G, H, currentTile));
										}
										else if ( G < openList.get(tile).G ) {
											openList.get(tile).F = F;
											openList.get(tile).G = G;
											openList.get(tile).father = currentTile;
										}
									}
								}
							}
						}
						else {
							closeList.put(new Point(i,j), this.wall);
						}
					}
				}
			}
		} while ( closeList.get(destinationPoint) == null && !openList.isEmpty() ) ;

		
		/* Si la case de fin a bien été trouvé */
		if ( closeList.get(destinationPoint) != null )  {

			/* On remonte jusqu'à la case suivant notre point de départ */
			currentTile = closeList.get(destinationPoint).father;

			while ( closeList.get(currentTile).father != source ) {
				currentTile = closeList.get(currentTile).father;
			}
		}
		else {
			currentTile = source;
		}
		
		openList.clear();
		closeList.clear();
		
		return currentTile;
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
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();


		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.y > this.size ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().y == this.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x,this.y-1);
				this.currentTile = ResourcesManager.coToTile(this.x,this.y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE  && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y--;
						}
						else {
							if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
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
						if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
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
		player.setPosition(this.x, this.y);
	}

	private void moveDown(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.y < (this.size*(this.single.map.getHeight()-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().y == this.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(this.x,this.y+this.size);
				this.currentTile = ResourcesManager.coToTile(this.x,this.y+this.size-1);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							this.y++;
						}
						else {
							if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
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
						if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
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
		player.setPosition(this.x, this.y);
	}

	private void moveRight(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.x < (this.size*(this.single.map.getWidth()-1)) ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().x == this.x) {
						break;
					}
				}
				
				this.nextTile = ResourcesManager.coToTile(this.x+this.size,this.y);
				this.currentTile = ResourcesManager.coToTile(this.x+this.size-1,this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x++;
						}
						else {
							if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
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
						if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
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
		player.setPosition(this.x, this.y);  
	}

	private void moveLeft(Player player) {

		this.x = player.getPosition().x;
		this.y = player.getPosition().y;
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( this.x > this.size ) {

				if (player.getObjectif() != null) {
					if (player.getObjectif().x == this.x) {
						break;
					}
				}
				
				this.nextTile = ResourcesManager.coToTile(this.x-1,this.y);
				this.currentTile = ResourcesManager.coToTile(this.x,this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							this.x--;
						}
						else {
							if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
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
						if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
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
		player.setPosition(this.x, this.y);                  
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


	private void updateBombs() {

		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();
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
				colisionMap[p.x][p.y] = ColisionMapObjects.DAMAGE;

				/* UP */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x, p.y-k)) != null ) {
						this.bombs.get(new Point(p.x, p.y-k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap[p.x][p.y-k] != ColisionMapObjects.BLOCK) {
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
						if ( colisionMap[p.x][p.y-k] != ColisionMapObjects.GAPE ) {
							colisionMap[p.x][p.y-k] = ColisionMapObjects.DAMAGE;
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
						else if ( colisionMap[p.x][p.y-k] == ColisionMapObjects.DAMAGE ) {
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
					else if ( colisionMap[p.x][p.y+k] != ColisionMapObjects.BLOCK ) {
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
						if ( colisionMap[p.x][p.y+k] != ColisionMapObjects.GAPE ) {
							colisionMap[p.x][p.y+k] = ColisionMapObjects.DAMAGE;
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
						else if ( colisionMap[p.x][p.y+k] == ColisionMapObjects.DAMAGE ) {
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
					else if ( colisionMap[p.x-k][p.y] != ColisionMapObjects.BLOCK ) {
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
						if ( colisionMap[p.x-k][p.y] != ColisionMapObjects.GAPE ) {
							colisionMap[p.x-k][p.y] = ColisionMapObjects.DAMAGE;
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
						else if ( colisionMap[p.x-k][p.y] == ColisionMapObjects.DAMAGE ) {
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
					else if ( colisionMap[p.x+k][p.y] != ColisionMapObjects.BLOCK ) {
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
						if ( colisionMap[p.x+k][p.y] != ColisionMapObjects.GAPE ) {
							colisionMap[p.x+k][p.y] = ColisionMapObjects.DAMAGE;
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
						else if ( colisionMap[p.x+k][p.y] == ColisionMapObjects.DAMAGE ) {
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


	private Point iaPushBomb(Player player, ColisionMapObjects[][] colisionMap) {

		Point point1 = player.getPosition(), p;
		p = point1 = ResourcesManager.coToTile(point1.x, point1.y);

		/* Ajouter une bombe */
		Point bombPoint = ResourcesManager.coToTile(player.getPosition().x+(ResourcesManager.getSize()/2), player.getPosition().y+(ResourcesManager.getSize()/2));

		if ( this.bombs.get(bombPoint) == null ) {
			if ( player.getBombNumber() > 0 ) {

				/* On crée une nouvelle bombe */
				Bomb bomb = new Bomb(player.getBombSelected(), ResourcesManager.getBombsAnimations().get(player.getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, player);

				/* Coordonnées de la bombe posée */
				bomb.setPosition(ResourcesManager.tileToCo(bombPoint.x, bombPoint.y));

				boolean up, down, left, right;
				up = down = left = right = true;

				/* CENTER */
				colisionMap[bombPoint.x][bombPoint.y] = ColisionMapObjects.BOMB;

				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {

					if ( up ) {
						if ( colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.DAMAGE) {
							colisionMap[bombPoint.x][bombPoint.y-k] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							up = false;
						}
					}

					if ( down ) {
						if ( colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.DAMAGE) {
							colisionMap[bombPoint.x][bombPoint.y+k] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							down = false;
						}
					}

					if ( left ) {
						if ( colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.DAMAGE) {
							colisionMap[bombPoint.x-k][bombPoint.y] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							left = false;
						}
					}

					if ( right ) {
						if ( colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.DAMAGE) {
							colisionMap[bombPoint.x+k][bombPoint.y] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							right = false;
						}
					}
				}

				/* Chercher une chemin de sortie */
				p = pathFinding(point1, colisionMap);

				/* Si il existe une sortie */
				if ( p.x != point1.x || p.y != point1.y ) {

					/* On joue la musique */
					bomb.playCurrentAnimationSound();

					/* On l'ajoute dans la hash map de bombes */
					this.bombs.put(bombPoint, bomb);

					/* Zones dangereuses */
					this.single.map.setColisionMap(colisionMap);

					/* On diminue la quantité des bombes que peut poser le joueur */
					try {
						player.setBombNumber(player.getBombNumber()-1);
					} catch (BombPowerException e) {
						e.printStackTrace();
					}

				}
			}
		}

		return p;
	}

}