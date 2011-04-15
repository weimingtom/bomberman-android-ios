package com.klob.Bomberklob.engine;

import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resourcesmanager.ResourcesManager;

import android.graphics.Canvas;

public class Engine {

	private Single single;

	private Point nextTile = null, currentTile = null;
	private int size,x, y;

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

		for (int i = 0 ; i < 1 ; i++ ) {
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

		for (int i = 0 ; i < 1 ; i++ ) {
			if ( this.y < (this.size*(this.single.map.getBlocks()[0].length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(this.x, this.y+this.size);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

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

		for (int i = 0 ; i < 1 ; i++ ) {
			if ( this.x < (this.size*(this.single.map.getBlocks().length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(this.x+this.size, this.y);
				this.currentTile = ResourcesManager.coToTile(this.x, this.y);

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

		for (int i = 0 ; i < 1 ; i++ ) {
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

	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

	public void update() {
		this.single.update();
	}

}
