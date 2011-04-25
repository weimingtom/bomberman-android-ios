package com.klob.Bomberklob.game;

import java.util.Vector;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

import android.graphics.Canvas;
import android.util.Log;

public class Engine {

	private Single single;

	private Point nextTile = null, currentTile = null;
	private int size,x, y;


	private boolean bombBoolean = true;
	private Thread bombThread;	


	/* Constructeur -------------------------------------------------------- */

	public Engine(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		this.single = new Single(mapName, enemies, gametype, random, difficulty);
		this.size = ResourcesManager.getSize();
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
						for (int i = 0 ; i < single.getPlayers().length ; i++ ) {
							Vector<Bomb> bombs = single.getPlayers()[i].getBombsPlanted();
							for (int j = 0 ; j < bombs.size() ; j++ ) {
								bombs.get(j).updateTime();
							}
						}
					}
					Log.i("Bombs Thread","Thread done");
				};
			};
			this.bombThread.start();
		}
	}

	/* Méthodes publiques -------------------------------------------------- */

	public void move(PlayerAnimations playerAnimation) {
		if ( playerAnimation == PlayerAnimations.UP) {
			moveUp(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.DOWN) {
			moveDown(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.LEFT) {
			moveLeft(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.RIGHT) {
			moveRight(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.DOWN_LEFT) {
			downLeft(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.DOWN_RIGHT) {
			downRight(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.UP_LEFT) {
			upLeft(this.single.getPlayers()[0]);
		}
		else if ( playerAnimation == PlayerAnimations.UP_RIGHT) {
			upRight(this.single.getPlayers()[0]);
		}

		if ( !this.single.getPlayers()[0].getCurrentAnimation().equals(playerAnimation.getLabel()) ) {
			this.single.getPlayers()[0].setCurrentAnimation(playerAnimation);
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit()) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit()) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit()) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit()) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y].isHit()) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null || !this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1].isHit()) {
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

	public void pushBomb(Player player) {
		this.single.pushBomb(player);
	}

	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

	public void update() {
		Player[] players = this.single.getPlayers();
		Map map = this.single.getMap();

		for (int i = 0 ; i < players.length ; i++ ) {
			if ( players[i] != null ) {

				/* Bombes -------------------------------------------------- */

				this.updateBombs(players[i].getBombsPlanted());				

				/* Joueurs ------------------------------------------------- */

				if ( players[i].getPosition() != null ) {

					/* Si l'animation courante correspond à KILL et qu'elle est finie on supprime le personnage */
					if ( players[i].hasAnimationFinished() && players[i].getCurrentAnimation().equals(PlayerAnimations.KILL.getLabel())) {
						/* On met la position à null et non la valeur du joueur dans le tableau car sinon on ne mettra jamais ses bombes à jour une fois qu'il est mort */
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
						players[i].update();
					}
				}

			}
		}
		this.single.getMap().update();
	}

	private void updateBombs(Vector<Bomb> bombs) {
		Map map = this.single.getMap();

		for(int j = 0; j < bombs.size() ; j++ ) {

			/* Si la bombe doit exploser */
			if ( bombs.get(j).timeElapsed() && bombs.get(j).getCurrentAnimation().equals(ObjectsAnimations.ANIMATE.getLabel())) {
				/* On passe son animation à DESTROY */
				bombs.get(j).setCurrentAnimation(ObjectsAnimations.DESTROY);
			}
			/* Si la bombe a comme animation DESTROY et qu'elle est finie alors on ajoute les flammes et on supprime la bombe */
			else if ( bombs.get(j).hasAnimationFinished() && bombs.get(j).getCurrentAnimation().equals(ObjectsAnimations.DESTROY.getLabel())) {

				Point p = ResourcesManager.coToTile(bombs.get(j).getPosition().x, bombs.get(j).getPosition().y);
				map.addBlock(ResourcesManager.getObjects().get("firecenter").copy(), p);

				/* UP */
				for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
					if ( map.getBlocks()[p.x][p.y-k] == null ) {
						if ( map.getGrounds()[p.x][p.y-k].isFireWall() ) {
							break;
						}
						else {
							if ( k < bombs.get(j).getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y-k));
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireup").copy(), new Point(p.x ,p.y-k));
							}
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

				/* DOWN */
				for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
					if ( map.getBlocks()[p.x][p.y+k] == null ) {
						if ( map.getGrounds()[p.x][p.y+k].isFireWall() ) {
							break;
						}
						else {
							if ( k < bombs.get(j).getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firevertical").copy(), new Point(p.x ,p.y+k));
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("firedown").copy(), new Point(p.x ,p.y+k));
							}
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

				/* LEFT */
				for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
					if ( map.getBlocks()[p.x-k][p.y] == null ) {
						if ( map.getGrounds()[p.x-k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bombs.get(j).getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x-k ,p.y));
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireleft").copy(), new Point(p.x-k ,p.y));
							}
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

				/* RIGHT */
				for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
					if ( map.getBlocks()[p.x+k][p.y] == null ) {
						if ( map.getGrounds()[p.x+k][p.y].isFireWall() ) {
							break;
						}
						else {
							if ( k < bombs.get(j).getPower()-1 ) {
								map.addBlock(ResourcesManager.getObjects().get("firehorizontal").copy(), new Point(p.x+k ,p.y));
							}
							else {
								map.addBlock(ResourcesManager.getObjects().get("fireright").copy(), new Point(p.x+k ,p.y));
							}
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

				/* Puis on supprime la bombe */
				bombs.remove(j);
			}
		}
	}
}
