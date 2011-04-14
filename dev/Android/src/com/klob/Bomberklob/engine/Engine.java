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

		}
		else if ( playerAnimation == PlayerAnimations.DOWN_RIGHT) {

		}
		else if ( playerAnimation == PlayerAnimations.UP_LEFT) {

		}
		else if ( playerAnimation == PlayerAnimations.UP_RIGHT) {

		}

		if ( !this.single.getPlayers()[0].getCurrentAnimation().equals(playerAnimation.getLabel()) ) {
			this.single.getPlayers()[0].setCurrentAnimation(playerAnimation.getLabel());
		}
	}

	private void moveUp(Player player) {

		this.x = 0;
		this.y = 0;

		for (int i = 0 ; i < player.getSpeed() ; i++ ) {
			if ( player.getPosition().y > this.size ) {

				this.nextTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y-1);
				this.currentTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= player.getPosition().x) && ((player.getPosition().x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								System.out.println("MONTE");
								y--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									System.out.println("MILIEU");
									y--;
								}
								else {
									if ( player.getPosition().x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
										System.out.println("DROITE|BLOCK OK|GROUND KO|DECALE GAUCHE");
										x--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( player.getPosition().x <= ((this.currentTile.x*this.size)+(this.size/2)) ) {
									System.out.println("DROITE|BLOCK KO|DECALE GAUCHE");
									x--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									if ( player.getPosition().x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
										System.out.println("GAUCHE|BLOCK OK|GROUND KO|DECALE DROITE");
										x++;
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
								if ( player.getPosition().x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
									System.out.println("GAUCHE|BLOCK KO|DECALE DROITE");
									x++;
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
					System.out.println("MEME CASE");
					this.y--;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(player.getPosition().x+x, player.getPosition().y+y));		
	}


	private void moveDown(Player player) {

		this.x = 0;
		this.y = 0;

		for (int i = 0 ; i < player.getSpeed() ; i++ ) {
			if ( player.getPosition().y < (this.size*(this.single.map.getBlocks().length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y+this.size);
				this.currentTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.x*this.size) <= player.getPosition().x) && ((player.getPosition().x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
								System.out.println("DESCEND");
								y++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									System.out.println("MILIEU");
									y++;
								}
								else {
									if ( player.getPosition().x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
										System.out.println("DROITE|BLOCK OK|GROUND KO|DECALE GAUCHE");
										x--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( player.getPosition().x <= ((this.currentTile.x*this.size)+(this.size/2)) ) {
									System.out.println("DROITE|BLOCK KO|DECALE GAUCHE");
									x--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( this.single.map.getBlocks()[this.nextTile.x+1][this.nextTile.y] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x+1][this.nextTile.y].isHit() ) {
									if ( player.getPosition().x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
										System.out.println("GAUCHE|BLOCK OK|GROUND KO|DECALE DROITE");
										x++;
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
								if ( player.getPosition().x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
									System.out.println("GAUCHE|BLOCK KO|DECALE DROITE");
									x++;
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
					System.out.println("MEME CASE");
					this.y++;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(player.getPosition().x+x, player.getPosition().y+y));		
	}
	
	private void moveRight(Player player) {
		
		this.x = 0;
		this.y = 0;

		for (int i = 0 ; i < player.getSpeed() ; i++ ) {
			if ( player.getPosition().x < (this.size*(this.single.map.getBlocks()[0].length-1)) ) {

				this.nextTile = ResourcesManager.coToTile(player.getPosition().x+this.size, player.getPosition().y);
				this.currentTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= player.getPosition().y) && ((player.getPosition().y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								System.out.println("DROITE");
								x++;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									System.out.println("MILIEU");
									x++;
								}
								else {
									if ( player.getPosition().y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
										System.out.println("BAS|BLOCK OK|GROUND KO|DECALE HAUT");
										y--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( player.getPosition().y <= ((this.currentTile.y*this.size)+(this.size/2)) ) {
									System.out.println("BAS|BLOCK KO|DECALE HAUT");
									y--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									if ( player.getPosition().y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
										System.out.println("HAUT|BLOCK OK|GROUND KO|DECALE BAS");
										y++;
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
								if ( player.getPosition().y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
									System.out.println("HAUT|BLOCK KO|DECALE BAS");
									y++;
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
					System.out.println("MEME CASE");
					this.x++;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(player.getPosition().x+x, player.getPosition().y+y));
	}
	
	private void moveLeft(Player player) {
		
		this.x = 0;
		this.y = 0;

		for (int i = 0 ; i < player.getSpeed() ; i++ ) {
			if ( player.getPosition().x > this.size ) {

				this.nextTile = ResourcesManager.coToTile(player.getPosition().x-1, player.getPosition().y);
				this.currentTile = ResourcesManager.coToTile(player.getPosition().x, player.getPosition().y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y] == null ) {
						if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y].isHit() ) {
							if ( ( (this.currentTile.y*this.size) <= player.getPosition().y) && ((player.getPosition().y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
								System.out.println("GAUCHE");
								x--;
							}
							else if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									System.out.println("MILIEU");
									x--;
								}
								else {
									if ( player.getPosition().y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
										System.out.println("BAS|BLOCK OK|GROUND KO|DECALE HAUT");
										y--;
									}
									else {
										break;
									}
								}
							}
							else {
								if ( player.getPosition().y <= ((this.currentTile.y*this.size)+(this.size/2)) ) {
									System.out.println("BAS|BLOCK KO|DECALE HAUT");
									y--;
								}
								else {
									break;
								}
							}
						}
						else {
							if ( this.single.map.getBlocks()[this.nextTile.x][this.nextTile.y+1] == null ) {
								if ( !this.single.map.getGrounds()[this.nextTile.x][this.nextTile.y+1].isHit() ) {
									if ( player.getPosition().y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
										System.out.println("HAUT|BLOCK OK|GROUND KO|DECALE BAS");
										y++;
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
								if ( player.getPosition().y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
									System.out.println("HAUT|BLOCK KO|DECALE BAS");
									y++;
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
					System.out.println("MEME CASE");
					this.x--;
				}
			}
			else {
				break;
			}
		}		
		player.setPosition(new Point(player.getPosition().x+x, player.getPosition().y+y));			
	}

	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

	public void update() {
		this.single.update();
	}

}
