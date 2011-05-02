package com.klob.Bomberklob.game;

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
					Bomb bomb = new Bomb(player.getBombSelected(), ResourcesManager.getBombsAnimations().get(player.getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, player);
					
					Map map = this.single.getMap();
					ConcurrentHashMap<Point, Integer> zoneDangereuses = map.getZoneDangereuses();
					zoneDangereuses.put(p,1);
					
					/* UP */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( this.bombs.get(new Point(p.x, p.y-k)) != null ) {
							break;
						}
						else if ( map.getBlocks()[p.x][p.y-k] == null ) {
							if ( map.getGrounds()[p.x][p.y-k].isFireWall() ) {
								break;
							}
							else {
								zoneDangereuses.put(new Point(p.x, p.y-k),1);
							}
						}
						else {
							break;
						}
					}

					/* DOWN */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( this.bombs.get(new Point(p.x, p.y+k)) != null ) {
							break;
						}
						else if ( map.getBlocks()[p.x][p.y+k] == null ) {
							if ( map.getGrounds()[p.x][p.y+k].isFireWall() ) {
								break;
							}
							else {
								zoneDangereuses.put(new Point(p.x, p.y+k),1);
							}
						}
						else {
							break;
						}
					}

					/* LEFT */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( this.bombs.get(new Point(p.x-k, p.y)) != null ) {
							break;
						}
						else if ( map.getBlocks()[p.x-k][p.y] == null ) {
							if ( map.getGrounds()[p.x-k][p.y].isFireWall() ) {
								break;
							}
							else {
								zoneDangereuses.put(new Point(p.x-k, p.y),1);
							}
						}
						else {
							break;
						}
					}

					/* RIGHT */
					for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
						if ( this.bombs.get(new Point(p.x+k, p.y)) != null ) {
							break;
						}
						else if ( map.getBlocks()[p.x+k][p.y] == null ) {
							if ( map.getGrounds()[p.x+k][p.y].isFireWall() ) {
								break;
							}
							else {
								zoneDangereuses.put(new Point(p.x+k, p.y),1);
							}
						}
						else {
							break;
						}
					}
					
					bomb.setPosition(ResourcesManager.tileToCo(p.x, p.y));
					bomb.playCurrentAnimationSound();
					this.bombs.put(p, bomb);
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
		Map map = this.single.getMap();
		ConcurrentHashMap<Point, Integer> zoneDangereuses = map.getZoneDangereuses();

		/* Bombes -------------------------------------------------- */

		this.updateBombs();			

		/* Joueurs ------------------------------------------------- */

		for (int i = 0 ; i < players.length ; i++ ) {
			if ( players[i] != null ) {

				if ( players[i].getPosition() != null ) {

					/* Si l'animation courante correspond à KILL et qu'elle est finie on supprime le personnage */
					if ( players[i].hasAnimationFinished() && players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
						/* On met joueur à null ce qui stopera le thread d'écoute */
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
						else if ( players[i].isDestructible() ) {
							Point point = players[i].getPosition();
							Point point1 = ResourcesManager.coToTile(point.x,point.y);
							Point point2 = ResourcesManager.coToTile(point.x+ResourcesManager.getSize()-1,point.y);
							Point point3 = ResourcesManager.coToTile(point.x+ResourcesManager.getSize()-1,point.y+ResourcesManager.getSize()-1);
							Point point4 = ResourcesManager.coToTile(point.x,point.y+ResourcesManager.getSize()-1);

							/* IA */
							if ( i != 0 && !players[i].getCurrentAnimation().equals(PlayerAnimations.TOUCHED.getLabel()) && !players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel()) ) {
								/*Si le bot n'a pas d'objectif*/
								if ( ((BotPlayer) players[i]).getObjectif() == null ) {
									/* Defensif (On est dans une zone dangereuse) */
									if ( (zoneDangereuses.get(point1) == Integer.valueOf(1)) || (zoneDangereuses.get(point2) == Integer.valueOf(1)) || (zoneDangereuses.get(point3) == Integer.valueOf(1)) || (zoneDangereuses.get(point4) == Integer.valueOf(1))) {
										((BotPlayer) players[i]).setObjectif(new Point((int)(Math.random() * (20-1)) + 1,(int)(Math.random() * (14-1)) + 1));
									}
									/* Offensif */
									else {
										
									}
								}
								else {
									Point objectif = ((BotPlayer) players[i]).getObjectif();
									String animation = players[i].getCurrentAnimation();
									
									/* Si on a atteind l'objectif */
									if ( point1.x == objectif.x && point1.y == objectif.y ) {
										((BotPlayer) players[i]).setObjectif(null);
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
									
									if ( point1.x < objectif.x && point1.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN_RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN_RIGHT);
										}										
									}
									else if ( point1.x > objectif.x && point1.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN_LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN_LEFT);
										}										
									}
									else if ( point1.x > objectif.x && point1.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP_LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP_LEFT);
										}										
									}
									else if ( point1.x < objectif.x && point1.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP_RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP_RIGHT);
										}										
									}
									else if ( point1.x < objectif.x ) {
										if ( !animation.equals(PlayerAnimations.RIGHT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.RIGHT);
										}										
									}
									else if ( point1.x > objectif.x  ) {
										if ( !animation.equals(PlayerAnimations.LEFT.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.LEFT);
										}										
									}
									else if ( point1.y > objectif.y ) {
										if ( !animation.equals(PlayerAnimations.UP.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.UP);
										}										
									}
									else if ( point1.y < objectif.y ) {
										if ( !animation.equals(PlayerAnimations.DOWN.getLabel())) {
											players[i].setCurrentAnimation(PlayerAnimations.DOWN);
										}										
									}
								}
							}
							
							if ( (map.getBlocks()[point1.x][point1.y] != null && map.getBlocks()[point1.x][point1.y].getDamage() != 0) || (map.getBlocks()[point2.x][point2.y] != null && map.getBlocks()[point2.x][point2.y].getDamage() != 0) ||(map.getBlocks()[point3.x][point3.y] != null && map.getBlocks()[point3.x][point3.y].getDamage() != 0) || (map.getBlocks()[point4.x][point4.y] != null && map.getBlocks()[point4.x][point4.y].getDamage() != 0)) {
								players[i].decreaseLife();
								if ( players[i].getLife() == 0 ) {
									players[i].setCurrentAnimation(PlayerAnimations.KILL);
								}
								else {
									players[i].setCurrentAnimation(PlayerAnimations.TOUCHED);
								}
							}
							else if ( (map.getGrounds()[point1.x][point1.y].getDamage() != 0) || ( map.getGrounds()[point2.x][point2.y].getDamage() != 0) || (map.getGrounds()[point3.x][point3.y].getDamage() != 0) || (map.getGrounds()[point4.x][point4.y].getDamage() != 0) ) {

								players[i].decreaseLife();
								if ( players[i].getLife() == 0 ) {
									players[i].setCurrentAnimation(PlayerAnimations.KILL);
								}
								else {
									players[i].setCurrentAnimation(PlayerAnimations.TOUCHED);
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
		this.bombs = new ConcurrentHashMap<Point, Bomb>();
		
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

		for (int i = 0 ; i < (ResourcesManager.getSize()/4) ; i++ ) {
			if ( this.y > this.size ) {

				this.nextTile = ResourcesManager.coToTile(this.x, this.y-1);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( this.bombs.get(nextTile) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit())) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y--;
							}
							else if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) ) {
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
							if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit())) {
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
						if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit())) {
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

		for (int i = 0 ; i < (ResourcesManager.getSize()/4) ; i++ ) {
			if ( this.y < (this.size*(this.single.map.getBlocks()[0].length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(this.x, this.y+this.size);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y+this.size-1);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( this.bombs.get(nextTile) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit())) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y++;
							}
							else if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) ) {
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
							if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit())) {
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
						if ( this.bombs.get(new Point(nextTile.x+1,nextTile.y)) == null && (this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) ) {
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

		for (int i = 0 ; i < (ResourcesManager.getSize()/4) ; i++ ) {
			if ( this.x < (this.size*(this.single.map.getBlocks().length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(this.x+this.size, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x+this.size-1, this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( this.bombs.get(nextTile) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit())) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x++;
							}
							else if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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
							if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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
						if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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

		for (int i = 0 ; i < (ResourcesManager.getSize()/4) ; i++ ) {
			if ( this.x > this.size ) {

				this.nextTile = ResourcesManager.coToTile(this.x-1, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( this.bombs.get(nextTile) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit())) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x--;
							}
							else if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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
							if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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
						if ( this.bombs.get(new Point(nextTile.x,nextTile.y+1)) == null && (this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit())) {
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
				map.addBlock(ResourcesManager.getObjects().get("firecenter").copy(), p);
				animatedObjects.put(new Point(p.x,p.y), map.getBlocks()[p.x][p.y]);

				/* UP */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(new Point(p.x, p.y-k)) != null ) {
						this.bombs.get(new Point(p.x, p.y-k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( map.getBlocks()[p.x][p.y-k] == null ) {
						/* Si le sol ne laisse pas passer le feu */
						if ( map.getGrounds()[p.x][p.y-k].isFireWall() ) {
							/* On sort */
							break;
						}
						else {
							/* On affiche le feu */
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y-k));
								animatedObjects.put(new Point(p.x,p.y-k), map.getBlocks()[p.x][p.y-k]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireup").copy(), new Point(p.x ,p.y-k));
								animatedObjects.put(new Point(p.x,p.y-k), map.getBlocks()[p.x][p.y-k]);
							}
						}
					}
					/* Si il y a un block */
					else {
						if ( map.getBlocks()[p.x][p.y-k].getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y-k));
								animatedObjects.put(new Point(p.x,p.y-k), map.getBlocks()[p.x][p.y-k]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireup").copy(), new Point(p.x ,p.y-k));
								animatedObjects.put(new Point(p.x,p.y-k), map.getBlocks()[p.x][p.y-k]);
							}
						}
						else {
							if ( map.getBlocks()[p.x][p.y-k].isFireWall() ) {
								if ( map.getBlocks()[p.x][p.y-k].isDestructible() ) {
									map.getBlocks()[p.x][p.y-k].destroy();
								}
								break;
							}
							else {
								if ( map.getBlocks()[p.x][p.y-k].isDestructible() ) {
									map.getBlocks()[p.x][p.y-k].destroy();
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
					else if ( map.getBlocks()[p.x][p.y+k] == null ) {
						if ( map.getGrounds()[p.x][p.y+k].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y+k));
								animatedObjects.put(new Point(p.x,p.y+k), map.getBlocks()[p.x][p.y+k]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("firedown").copy(), new Point(p.x ,p.y+k));
								animatedObjects.put(new Point(p.x,p.y+k), map.getBlocks()[p.x][p.y+k]);
							}
						}
					}
					else {
						if ( map.getBlocks()[p.x][p.y+k].getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y+k));
								animatedObjects.put(new Point(p.x,p.y+k), map.getBlocks()[p.x][p.y+k]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("firedown").copy(), new Point(p.x ,p.y+k));
								animatedObjects.put(new Point(p.x,p.y+k), map.getBlocks()[p.x][p.y+k]);
							}
						}
						else {
							if ( map.getBlocks()[p.x][p.y+k].isFireWall() ) {
								if ( map.getBlocks()[p.x][p.y+k].isDestructible() ) {
									map.getBlocks()[p.x][p.y+k].destroy();
								}
								break;
							}
							else {
								if ( map.getBlocks()[p.x][p.y+k].isDestructible() ) {
									map.getBlocks()[p.x][p.y+k].destroy();
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
					else if ( map.getBlocks()[p.x-k][p.y] == null ) {
						if ( map.getGrounds()[p.x-k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x-k ,p.y));
								animatedObjects.put(new Point(p.x-k,p.y), map.getBlocks()[p.x-k][p.y]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireleft").copy(), new Point(p.x-k ,p.y));
								animatedObjects.put(new Point(p.x-k,p.y), map.getBlocks()[p.x-k][p.y]);
							}
						}
					}
					else {
						if ( map.getBlocks()[p.x-k][p.y].getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x-k ,p.y));
								animatedObjects.put(new Point(p.x-k,p.y), map.getBlocks()[p.x-k][p.y]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireleft").copy(), new Point(p.x-k ,p.y));
								animatedObjects.put(new Point(p.x-k,p.y), map.getBlocks()[p.x-k][p.y]);
							}
						}
						else {							
							if ( map.getBlocks()[p.x-k][p.y].isFireWall() ) {
								if ( map.getBlocks()[p.x-k][p.y].isDestructible() ) {
									map.getBlocks()[p.x-k][p.y].destroy();
								}
								break;
							}
							else {
								if ( map.getBlocks()[p.x-k][p.y].isDestructible() ) {
									map.getBlocks()[p.x-k][p.y].destroy();
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
					else if ( map.getBlocks()[p.x+k][p.y] == null ) {
						if ( map.getGrounds()[p.x+k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x+k ,p.y));
								animatedObjects.put(new Point(p.x+k,p.y), map.getBlocks()[p.x+k][p.y]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireright").copy(), new Point(p.x+k ,p.y));
								animatedObjects.put(new Point(p.x+k,p.y), map.getBlocks()[p.x+k][p.y]);
							}
						}
					}
					else {
						if ( map.getBlocks()[p.x+k][p.y].getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel()) ) {
							if ( k < bomb.getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x+k ,p.y));
								animatedObjects.put(new Point(p.x+k,p.y), map.getBlocks()[p.x+k][p.y]);
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireright").copy(), new Point(p.x+k ,p.y));
								animatedObjects.put(new Point(p.x+k,p.y), map.getBlocks()[p.x+k][p.y]);
							}
						}
						else {
							if ( map.getBlocks()[p.x+k][p.y].isFireWall() ) {
								if ( map.getBlocks()[p.x+k][p.y].isDestructible() ) {
									map.getBlocks()[p.x+k][p.y].destroy();
								}
								break;
							}
							else {
								if ( map.getBlocks()[p.x+k][p.y].isDestructible() ) {
									map.getBlocks()[p.x+k][p.y].destroy();
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
