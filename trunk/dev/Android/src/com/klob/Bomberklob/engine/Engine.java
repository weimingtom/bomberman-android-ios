package com.klob.Bomberklob.engine;

import java.util.Vector;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

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
			this.single.getPlayers()[0].setCurrentAnimation(playerAnimation.getLabel());
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= this.x) && ((this.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								this.y++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= this.y) && ((this.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								this.x--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
						if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
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
		this.single.update();

		Player[] players = this.single.getPlayers();
		Vector<Bomb> bombs;
		Map map = this.single.getMap();

		for (int i = 0 ; i < players.length ; i++ ) {
			if ( players[i].getPosition() != null ) {
				players[i].update();
				bombs = players[i].getBombsPlanted();
				for(int j = 0; j < bombs.size() ; j++ ) {
					if ( bombs.get(j).hasAnimationFinished() ) {
						Point p = ResourcesManager.coToTile(bombs.get(j).getPosition().x, bombs.get(j).getPosition().y);
						map.addBlock(ResourcesManager.getObjects().get("firecenter").copy(), p);
						
						/* UP */
						for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
							if ( map.getBlocks()[p.x][p.y-k] == null) {
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
								if ( map.getBlocks()[p.x][p.y-k].isDestructible() ) {
									map.getBlocks()[p.x][p.y-k].destroy();
								}
								break;
							}
						}
						
						/* DOWN */
						for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
							if ( map.getBlocks()[p.x][p.y+k] == null) {
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
								if ( map.getBlocks()[p.x][p.y+k].isDestructible() ) {
									map.getBlocks()[p.x][p.y+k].destroy();
								}
								break;
							}
						}
						
						/* LEFT */
						for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
							if ( map.getBlocks()[p.x-k][p.y] == null) {
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
								if ( map.getBlocks()[p.x-k][p.y].isDestructible() ) {
									map.getBlocks()[p.x-k][p.y].destroy();
								}
								break;
							}
						}
						
						/* RIGHT */
						for ( int k = 1 ; k < bombs.get(j).getPower() ; k++ ) {
							if ( map.getBlocks()[p.x+k][p.y] == null) {
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
								if ( map.getBlocks()[p.x+k][p.y].isDestructible() ) {
									map.getBlocks()[p.x+k][p.y].destroy();
								}
								break;
							}
						}
						bombs.remove(j);
					}
				}
			}
		}
	}
}
