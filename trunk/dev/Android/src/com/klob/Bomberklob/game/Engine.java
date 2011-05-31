package com.klob.Bomberklob.game;

import android.graphics.Canvas;

import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.objects.exceptions.BombPowerException;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

/**
 * Class representing the game engine
 */

public class Engine {

	private Single single;

	private Point nextTile = new Point(), currentTile = new Point(), tileUpLeft, tileUpRight, tileDownLeft, tileDownRight, playerPosition;
	private int size;

	/* Constructeur -------------------------------------------------------- */

	/**
	 * Creates an engine according to the parameters passed
	 * @param mapName Name of the map
	 * @param enemies Number of enemies
	 * @param gametype Type of the game
	 * @param random True if players are placed randomly
	 * @param difficulty Difficulty of the enemies
	 */
	public Engine(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		this.single = new Single(mapName, enemies, gametype, random, difficulty);
		this.size = ResourcesManager.getSize();
	}

	/* Getters ------------------------------------------------------------- */

	/**
	 * Returns an instance of the current single game
	 * @return An instance of the current single game
	 */
	
	public Single getSingle() {
		return single;
	}

	/* Setters ------------------------------------------------------------- */

	/**
	 * Updates the instance of the current single game
	 * @param The instance of the current single game
	 */
	
	public void setSingle(Single single) {
		this.single = single;
	}	

	/* Méthodes publiques -------------------------------------------------- */		

	/**
	 * Allows a player to place a bomb on the map
	 * @param The player who wants to plant a bomb
	 */
	public void pushBomb(Player player) {

		if ( player != null ) {
			Point p = ResourcesManager.coToTile(player.getPosition().x+(ResourcesManager.getSize()/2), player.getPosition().y+(ResourcesManager.getSize()/2));

			if ( this.single.getBombs().get(p) == null ) {
				if ( player.getBombNumber() > 0 ) {

					/* On crée une nouvelle bombe */
					Bomb bomb = new Bomb(player.getBombSelected(), ResourcesManager.getBombsAnimations().get(player.getBombSelected()), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, player);

					/* Coordonnées de la bombe posée */
					bomb.setPosition(ResourcesManager.tileToCo(p.x, p.y));

					/* On joue la musique */
					bomb.playCurrentAnimationSound();

					/* On l'ajoute dans la hash map de bombes */
					this.single.getBombs().put(p, bomb);

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

	/**
	 * Draws the current single game in the canvas according to the desired size
	 * 
	 * @param canvas A canvas
	 * @param size The desired size
	 */
	public void onDraw(Canvas canvas, int size) {
		this.single.onDraw(canvas,size);
	}

	/**
	 * Update the current single game
	 */	
	public void update() {
		Player[] players = this.single.getPlayers();

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
							this.playerPosition = players[i].getPosition();
							this.tileUpLeft = ResourcesManager.coToTile(this.playerPosition.x,this.playerPosition.y);
							this.tileUpRight = ResourcesManager.coToTile(this.playerPosition.x+ResourcesManager.getSize()-1,this.playerPosition.y);
							this.tileDownRight = ResourcesManager.coToTile(this.playerPosition.x+ResourcesManager.getSize()-1,this.playerPosition.y+ResourcesManager.getSize()-1);
							this.tileDownLeft = ResourcesManager.coToTile(this.playerPosition.x,this.playerPosition.y+ResourcesManager.getSize()-1);

							move(players[i]);

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

	/**
	 * Restart the curent single game
	 */
	public void restartGame () {		
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

		Point position = player.getPosition();
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		
		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( position.y > this.size ) {

				if (player.getObjective() != null) {
					if (player.getObjective().y == position.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(position.x,position.y-1);
				this.currentTile = ResourcesManager.coToTile(position.x,position.y);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE  && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= position.x) && ((position.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							player.moveUp();
						}
						else {
							if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
								player.moveUp();
							}
							else {
								if ( position.x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
									player.moveLeft();
								}
								else {
									break;
								}
							}
						}
					}
					else {
						if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
							if ( position.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
								player.moveRight();
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
					player.moveUp();
				}
			}
			else {
				break;
			}
		}
	}

	private void moveDown(Player player) {

		Point position = player.getPosition();
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( position.y < (this.size*(this.single.map.getHeight()-1)) ) {

				if (player.getObjective() != null) {
					if (player.getObjective().y == position.y) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(position.x,position.y+this.size);
				this.currentTile = ResourcesManager.coToTile(position.x,position.y+this.size-1);

				if ( this.nextTile.y != this.currentTile.y ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.x*this.size) <= position.x) && ((position.x+this.size) <= ((this.currentTile.x*this.size)+this.size)) ) {
							player.moveDown();
						}
						else {
							if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
								player.moveDown();
							}
							else {
								if ( position.x < ((this.currentTile.x*this.size)+(this.size/2)) ) {
									player.moveLeft();
								}
								else {
									break;
								}
							}
						}
					}
					else {
						if ( colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x+1][this.nextTile.y] != ColisionMapObjects.BOMB) {
							if ( position.x > ((this.currentTile.x*this.size)+(this.size/2)) ) {
								player.moveRight();
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
					player.moveDown();
				}                               
			}
			else {
				break;
			}
		}
	}

	private void moveRight(Player player) {

		Point position = player.getPosition();
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( position.x < (this.size*(this.single.map.getWidth()-1)) ) {

				if (player.getObjective() != null) {
					if (player.getObjective().x == position.x) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(position.x+this.size,position.y);
				this.currentTile = ResourcesManager.coToTile(position.x+this.size-1,position.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= position.y) && ((position.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							player.moveRight();
						}
						else {
							if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
								player.moveRight();
							}
							else {
								if ( position.y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
									player.moveUp();
								}
								else {
									break;
								}
							}
						}
					}
					else {
						if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
							if ( position.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
								player.moveDown();
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
					player.moveRight();
				}
			}
			else {
				break;
			}
		}
	}

	private void moveLeft(Player player) {

		Point position = player.getPosition();
		ColisionMapObjects[][] colisionMap = this.single.map.getColisionMap();

		for (int i = 0 ; i < (ResourcesManager.getSize()/4)*player.getSpeed() ; i++ ) {
			if ( position.x > this.size ) {

				if (player.getObjective() != null) {
					if (player.getObjective().x == position.x) {
						break;
					}
				}

				this.nextTile = ResourcesManager.coToTile(position.x-1,position.y);
				this.currentTile = ResourcesManager.coToTile(position.x,position.y);

				if ( this.nextTile.x != this.currentTile.x ) {
					if ( colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y] != ColisionMapObjects.BOMB) {
						if ( ( (this.currentTile.y*this.size) <= position.y) && ((position.y+this.size) <= ((this.currentTile.y*this.size)+this.size)) ) {
							player.moveLeft();
						}
						else {
							if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
								player.moveLeft();
							}
							else {
								if ( position.y < ((this.currentTile.y*this.size)+(this.size/2)) ) {
									player.moveUp();
								}
								else {
									break;
								}
							}
						}
					}
					else {
						if ( colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BLOCK && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.GAPE && colisionMap[this.nextTile.x][this.nextTile.y+1] != ColisionMapObjects.BOMB) {
							if ( position.y > ((this.currentTile.y*this.size)+(this.size/2)) ) {
								player.moveDown();
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
					player.moveLeft();
				}
			}
			else {
				break;
			}
		}
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

}