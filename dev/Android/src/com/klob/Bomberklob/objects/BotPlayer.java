package com.klob.Bomberklob.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.klob.Bomberklob.game.ColisionMapObjects;
import com.klob.Bomberklob.game.Single;
import com.klob.Bomberklob.resources.PathFindingNode;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class BotPlayer extends Player {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int difficulty;
	
	private Single single;

	/* Pathfinding */
	private Map<Point, PathFindingNode> openList;
	private Map<Point, PathFindingNode> closeList;
	//private ArrayList<Point> openListArray;
	private int H, F, G, minimumStrok;

	private Map<Point, Integer> distance;
	private Map<Point, PlayerAnimations> direction;
	private List<Integer> vect;
	

	public BotPlayer(String imageName, Hashtable<String, AnimationSequence> animations, PlayerAnimations currentAnimation, boolean hit, int level, boolean fireWall, int damages, int life, int powerExplosion, int timeExplosion, int speed, int shield, int bombNumber, int immortal, int difficulty, Single single) {
		super(imageName, animations, currentAnimation, hit, level, fireWall, damages, life, powerExplosion, timeExplosion, speed, shield, bombNumber, immortal);
		this.difficulty = difficulty;

		this.openList = new HashMap<Point, PathFindingNode>();
		this.closeList = new HashMap<Point, PathFindingNode>();
		this.distance = new HashMap<Point, Integer>();
		this.direction = new HashMap<Point, PlayerAnimations>();
		this.vect = new ArrayList<Integer>();
		this.single = single;
	}

	public BotPlayer(BotPlayer botPlayer) {
		super(botPlayer);
		this.difficulty = botPlayer.difficulty;		

		this.openList = new HashMap<Point, PathFindingNode>();
		this.closeList = new HashMap<Point, PathFindingNode>();
		this.distance = new HashMap<Point, Integer>();
		this.direction = new HashMap<Point, PlayerAnimations>();
		this.vect = new ArrayList<Integer>();
		this.single = botPlayer.single;
	}
	
	/* Getters ------------------------------------------------------------- */
	
	public int getDifficulty() {
		return difficulty;
	}
	
	/* Setters ------------------------------------------------------------- */
	
	public void setDifficulty(int difficulty) {
		if ( difficulty > 0 && difficulty < 4 ) {
			this.difficulty = difficulty;
		}
	}

	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public BotPlayer copy() {
		return new BotPlayer(this);
	}
	
	@Override
	public void update () {
		super.update();
		ColisionMapObjects[][] colisionMap = single.getMap().getColisionMap();
		Point tileUpLeft = ResourcesManager.coToTile(this.position.x,this.position.y), playerObjectif;
		
		/*Si le bot n'a pas d'objectif*/
		if ( this.position.x == this.objectif.x && this.position.y == this.objectif.y ) {
			
			/* Defensif (On est dans une zone dangereuse) */
			if ( colisionMap[tileUpLeft.x][tileUpLeft.y] == ColisionMapObjects.DANGEROUS_AREA || colisionMap[tileUpLeft.x][tileUpLeft.y] == ColisionMapObjects.BOMB) {

				playerObjectif = safeAroundArea(tileUpLeft, colisionMap);                

				if ( this.objectif.x == tileUpLeft.x && this.objectif.y == tileUpLeft.y ) {
					playerObjectif = pathFinding(tileUpLeft, colisionMap);
				}
			}
			/* Offensif */
			else {

				int timeBomb = Integer.MAX_VALUE;
/*
				for(Point entry : bombs.keySet()) {
					if ( equals(bombs.get(entry).getPlayer())) {
						timeBomb = Math.min(bombs.get(entry).getTime(),timeBomb);
					}
				}
*/
				/*if ( timeBomb > 15 ) {*/
					if ( (int)(Math.random() * (20-(10*this.difficulty))) == 0) {/*
						if ( 0 != difficulty && players[0].getPosition() != null ) {
							Point prout = ResourcesManager.coToTile(players[0].getPosition().x, players[0].getPosition().y);
							playerObjectif = pathFinding(tileUpLeft, prout,colisionMap);
							if ( colisionMap[playerObjectif.x][playerObjectif.y] == ColisionMapObjects.BLOCK && getBombNumber() > 0) {
								playerObjectif = iaPushBomb(players[i], colisionMap.clone());
							}
							else if ( colisionMap[this.objectif.x][this.objectif.y] == ColisionMapObjects.EMPTY && getBombNumber() > 0 && this.objectif.x == prout.x && this.objectif.y == prout.y) {
								playerObjectif = pushBomb(players[i], colisionMap.clone());
							}
							else if ( colisionMap[this.objectif.x][this.objectif.y] != ColisionMapObjects.EMPTY ) {
								this.objectif.x = tileUpLeft.x;
								this.objectif.y = tileUpLeft.y;
							}
						}/*
						else {*/
							playerObjectif = safeAroundArea(tileUpLeft, colisionMap);
					}
					/*	}
					}
				}*/
				else {
					playerObjectif = tileUpLeft;
				}
			}
			this.objectif.x = playerObjectif.x*ResourcesManager.getSize();
			this.objectif.y = playerObjectif.y*ResourcesManager.getSize();									
		}

		if ( this.position.x != this.objectif.x || this.position.y != this.objectif.y ) {

			if ( this.position.x < this.objectif.x && this.position.y < this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.DOWN_RIGHT);
			}
			else if ( this.position.x > this.objectif.x && this.position.y < this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.DOWN_LEFT);
			}
			else if ( this.position.x > this.objectif.x && this.position.y > this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.UP_LEFT);
			}
			else if ( this.position.x < this.objectif.x && this.position.y > this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.UP_RIGHT);
			}
			else if ( this.position.x < this.objectif.x ) {
				setCurrentAnimation(PlayerAnimations.RIGHT);
			}
			else if ( this.position.x > this.objectif.x  ) {
				setCurrentAnimation(PlayerAnimations.LEFT);
			}
			else if ( this.position.y > this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.UP);
			}
			else if ( this.position.y < this.objectif.y ) {
				setCurrentAnimation(PlayerAnimations.DOWN);
			}
		}
		else {
			stopPlayer();
		}
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

					position = ResourcesManager.getPoint(h, v);
					point.x = position.x;
					point.y = position.y;

					if ( this.distance.get(ResourcesManager.getPoint(h, v)) == Integer.valueOf(d) ) {

						point.y = v+1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								pa = this.direction.get(ResourcesManager.getPoint(h, v));
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ){
								this.direction.put(ResourcesManager.getPoint(h, v+1), this.direction.get(ResourcesManager.getPoint(h, v)));
								this.distance.put(ResourcesManager.getPoint(h, v+1), d+1);
							}
						}

						point.y = v-1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								pa = this.direction.get(ResourcesManager.getPoint(h, v));
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ) {
								this.direction.put(ResourcesManager.getPoint(h, v-1), this.direction.get(ResourcesManager.getPoint(h, v)));
								this.distance.put(ResourcesManager.getPoint(h, v-1), d+1);
							}
						}

						point.x = h+1;
						point.y = v;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								pa = this.direction.get(ResourcesManager.getPoint(h, v));
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ) {
								this.direction.put(ResourcesManager.getPoint(h+1, v), this.direction.get(ResourcesManager.getPoint(h, v)));
								this.distance.put(ResourcesManager.getPoint(h+1, v), d+1);
							}
						}

						point.x = h-1;
						if ( this.distance.get(point) == null ) {
							this.distance.put(point, 0);
						}

						if ( this.distance.get(point) == Integer.valueOf(0) ) {
							if ( colisionMap[point.x][point.y] == ColisionMapObjects.EMPTY ) {
								pa = this.direction.get(ResourcesManager.getPoint(h, v));
								break;
							}
							else if ( colisionMap[point.x][point.y] == ColisionMapObjects.DANGEROUS_AREA ){
								this.direction.put(ResourcesManager.getPoint(h-1, v), this.direction.get(ResourcesManager.getPoint(h, v)));
								this.distance.put(ResourcesManager.getPoint(h-1, v), d+1);
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
		}
		else if ( pa == PlayerAnimations.LEFT) {
			point.x = playerTile.x-1;
			point.y = playerTile.y;
		}
		else if ( pa == PlayerAnimations.UP ) {
			point.x = playerTile.x;
			point.y = playerTile.y-1;
		}
		else if ( pa == PlayerAnimations.DOWN ) {
			point.x = playerTile.x;
			point.y = playerTile.y+1;
		}
		else {
			point.x = playerTile.x;
			point.y = playerTile.y;
		}
		
		return point;
	}

	public Point pathFinding(Point sourcePoint, Point destinationPoint, ColisionMapObjects[][] colisionMap) {

		openList.clear();
		closeList.clear();

		ConcurrentHashMap<Point, Objects> animatedObjects = this.single.getMap().getAnimatedObjects();

		Point tile = new Point(), source = new Point(sourcePoint.x, sourcePoint.y), res = new Point();
		openList.put(source, new PathFindingNode(0, 0, 0, source));
		int i, j;

		do {

			minimumStrok = Integer.MAX_VALUE;
			
			for (Entry<Point, PathFindingNode> entry : openList.entrySet()) {
                if ( entry.getValue().F <= minimumStrok) {
                        minimumStrok = entry.getValue().F;
                        tile = entry.getKey();
                }
			}
			
			closeList.put(tile, openList.remove(tile));

			for (i = tile.x-1 ; i < tile.x+2 ; i++ ) {
				for (j = tile.y-1 ; j < tile.y+2 ; j++ ) {

					if ( closeList.get(ResourcesManager.getPoint(i, j)) == null) {

						if ( colisionMap[i][j] == ColisionMapObjects.BLOCK ) {

							if ( animatedObjects.get(ResourcesManager.getPoint(i, j)) != null ) {

								if ( i == tile.x || j == tile.y ) {

									H = (Math.abs(destinationPoint.x-i) + Math.abs(destinationPoint.y-j))*10;

									addInOpenList(40, i, j, tile);
								}
							}
							else {
								closeList.put(ResourcesManager.getPoint(i, j), new PathFindingNode(0, 0, 0, tile));
							}
						}
						else if ( colisionMap[i][j] == ColisionMapObjects.EMPTY ) {

							H = (Math.abs(destinationPoint.x-i) + Math.abs(destinationPoint.y-j))*10;

							if ( i == tile.x || j == tile.y ) {                                                       
								addInOpenList(10, i, j, tile);
							}
							else {
								if ( i > tile.x && colisionMap[tile.x+1][tile.y] == ColisionMapObjects.EMPTY) {
									if ( j > tile.y && colisionMap[tile.x][tile.y+1] == ColisionMapObjects.EMPTY) {
										addInOpenList(14, i, j, tile);
									}
									else if ( colisionMap[tile.x][tile.y-1] == ColisionMapObjects.EMPTY ){
										addInOpenList(14, i, j, tile);
									}
								}
								else if ( i < tile.x && colisionMap[tile.x-1][tile.y] == ColisionMapObjects.EMPTY) {
									if ( j > tile.y && colisionMap[tile.x][tile.y+1] == ColisionMapObjects.EMPTY) {
										addInOpenList(14, i, j, tile);
									}
									else if ( colisionMap[tile.x][tile.y-1] == ColisionMapObjects.EMPTY ){
										addInOpenList(14, i, j, tile);
									}
								}
							}
						}
						else if ( colisionMap[i][j] == ColisionMapObjects.FIRE ) {

							H = (Math.abs(destinationPoint.x-i) + Math.abs(destinationPoint.y-j))*10;

							if ( i == tile.x || j == tile.y ) {                                                       
								addInOpenList(15, i, j, tile);
							}
							else {
								if ( i > tile.x && colisionMap[tile.x+1][tile.y] == ColisionMapObjects.EMPTY) {
									if ( j > tile.y && colisionMap[tile.x][tile.y+1] == ColisionMapObjects.EMPTY) {
										addInOpenList(14, i, j, tile);
									}
									else if ( colisionMap[tile.x][tile.y-1] == ColisionMapObjects.EMPTY ){
										addInOpenList(14, i, j, tile);
									}
								}
								else if ( i < tile.x && colisionMap[tile.x-1][tile.y] == ColisionMapObjects.EMPTY) {
									if ( j > tile.y && colisionMap[tile.x][tile.y+1] == ColisionMapObjects.EMPTY) {
										addInOpenList(14, i, j, tile);
									}
									else if ( colisionMap[tile.x][tile.y-1] == ColisionMapObjects.EMPTY ){
										addInOpenList(14, i, j, tile);
									}
								}
							}
						}
						else {
							closeList.put(ResourcesManager.getPoint(i, j), new PathFindingNode(0, 0, 0, tile));
						}
					}
				}
			}
		} while ( closeList.get(destinationPoint) == null && !openList.isEmpty() ) ;

		if ( closeList.get(destinationPoint) != null )  {

			res.set(destinationPoint.x,destinationPoint.y);

			while ( closeList.get(res).father != source ) {
				res.set(closeList.get(res).father.x, closeList.get(res).father.y);
			}	
		}
		else {
			res.set(source.x,source.y);
		}

		return res;
	}

	private void addInOpenList(int value, int i, int j, Point currentTile) {
		
		Point tile = ResourcesManager.getPoint(i, j);

		G = value+closeList.get(currentTile).G;

		F = G+H;

		if ( openList.get(tile) == null ) {
			openList.put(tile, new PathFindingNode(F, G, H, currentTile));
			//this.openListArray.add(tile);
		}
		else if ( G < openList.get(tile).G ) {
			openList.get(tile).F = F;
			openList.get(tile).G = G;
			openList.get(tile).father = currentTile;
		}
	}

	private Point pushBomb(ColisionMapObjects[][] colisionMap) {

		Point point1, p = new Point();
		point1 = ResourcesManager.coToTile(position.x, position.y);

		/* Ajouter une bombe */
		Point bombPoint = ResourcesManager.coToTile(position.x+(ResourcesManager.getSize()/2), position.y+(ResourcesManager.getSize()/2));

		if ( this.single.getBombs().get(bombPoint) == null ) {
			if ( bombNumber > 0 ) {

				/* On crée une nouvelle bombe */
				Bomb bomb = new Bomb(bombSelected, ResourcesManager.getBombsAnimations().get(bombSelected), ObjectsAnimations.ANIMATE, true, 1, false, 0, 1, this);

				/* Coordonnées de la bombe posée */
				bomb.setPosition(ResourcesManager.tileToCo(bombPoint.x, bombPoint.y));

				boolean up, down, left, right;
				up = down = left = right = true;

				/* CENTER */
				colisionMap[bombPoint.x][bombPoint.y] = ColisionMapObjects.BOMB;

				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {

					if ( up ) {
						if ( colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.DAMAGE && colisionMap[bombPoint.x][bombPoint.y-k] != ColisionMapObjects.FIRE) {
							colisionMap[bombPoint.x][bombPoint.y-k] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							up = false;
						}
					}

					if ( down ) {
						if ( colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.DAMAGE && colisionMap[bombPoint.x][bombPoint.y+k] != ColisionMapObjects.FIRE) {
							colisionMap[bombPoint.x][bombPoint.y+k] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							down = false;
						}
					}

					if ( left ) {
						if ( colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.DAMAGE && colisionMap[bombPoint.x-k][bombPoint.y] != ColisionMapObjects.FIRE) {
							colisionMap[bombPoint.x-k][bombPoint.y] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							left = false;
						}
					}

					if ( right ) {
						if ( colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.BLOCK && colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.BOMB && colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.DAMAGE && colisionMap[bombPoint.x+k][bombPoint.y] != ColisionMapObjects.FIRE) {
							colisionMap[bombPoint.x+k][bombPoint.y] = ColisionMapObjects.DANGEROUS_AREA;
						}
						else {
							right = false;
						}
					}
				}

				/* Chercher une chemin de sortie */
				p.x = point1.x;
				p.y = point1.y;
				p = pathFinding(point1, colisionMap);

				/* Si il existe une sortie */
				if ( p.x != point1.x || p.y != point1.y ) {

					/* On joue la musique */
					bomb.playCurrentAnimationSound();

					/* On l'ajoute dans la hash map de bombes */
					this.single.getBombs().put(bombPoint, bomb);

					/* Zones dangereuses */
					this.single.getMap().setColisionMap(colisionMap);

					/* On diminue la quantité des bombes que peut poser le joueur */
					bombNumber--;
				}
			}
		}
		return p;
	}
}
