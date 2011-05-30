package com.klob.Bomberklob.game;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.klob.Bomberklob.model.Model;
import com.klob.Bomberklob.objects.AnimationSequence;
import com.klob.Bomberklob.objects.Bomb;
import com.klob.Bomberklob.objects.BotPlayer;
import com.klob.Bomberklob.objects.HumanPlayer;
import com.klob.Bomberklob.objects.Objects;
import com.klob.Bomberklob.objects.ObjectsAnimations;
import com.klob.Bomberklob.objects.Player;
import com.klob.Bomberklob.objects.PlayerAnimations;
import com.klob.Bomberklob.resources.Point;
import com.klob.Bomberklob.resources.ResourcesManager;

public class Single extends Game {

	private int difficulty, size;

	
	/* Constructeur -------------------------------------------------------- */

	public Single(String mapName, int enemies, String gametype, boolean random, int difficulty) {
		super(mapName, enemies, gametype, random);
		this.difficulty = difficulty;
		this.size = ResourcesManager.getSize();
		initGame();
	}
	
	/* Méthodes publiques -------------------------------------------------- */
	
	@Override
	public void initGame() {
		ArrayList<String> colors = new ArrayList<String>();
		Point position;
		int size = ResourcesManager.getSize();
		
		for(Entry<String, Hashtable<String, AnimationSequence>> entry : ResourcesManager.getPlayersAnimations().entrySet()) {
			colors.add(entry.getKey());
		}
		
		if ( colors.size() >= this.players.length ) {
			this.players[0] = new HumanPlayer(Model.getUser().getColor(), ResourcesManager.getPlayersAnimations().get(Model.getUser().getColor()), PlayerAnimations.IDLE,gameType.hit, 1, gameType.fireWall, gameType.damages, gameType.life, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, gameType.immortal);
			position = this.map.getPlayers()[0];
			this.players[0].setPosition(new Point(position.x*size, position.y*size));
			colors.remove(this.players[0].getImageName());
			
			for (int i = 1 ; i < this.players.length ; i++ ) {
				int j = (int)(Math.random() * (colors.size()));
				this.players[i] = new BotPlayer(colors.get(j), ResourcesManager.getPlayersAnimations().get(colors.get(j)), PlayerAnimations.IDLE,gameType.hit, 1, gameType.fireWall, gameType.damages, gameType.life, gameType.powerExplosion, gameType.timeExplosion, gameType.speed, gameType.shield, gameType.bombNumber, gameType.immortal, this.difficulty, this);
				position = this.map.getPlayers()[i];
				this.players[i].setPosition(new Point(position.x*size, position.y*size));
				this.players[i].setObjectif(new Point(position.x*size, position.y*size));
				colors.remove(j);
			}
		}
	}
	
	public void pauseGame() {

	}
	
	public void restartGame() {

		/* Remise à 0 du vecteur de bombes */
		this.bombs.clear();

		/* Thread des bombes mis à l'arrêt */
		this.setBombThreadRunning(false);
		
		this.map.restart();
		this.players = new Player[this.players.length];
		
		/* Nouvelle initialisation de la partie */
		initGame();
	}

	@Override
	public void pushBomb(Player player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		updateBombs();		
		this.map.update();
	}
	
	private void updateBombs() {

		ColisionMapObjects[][] colisionMap = map.getColisionMap();
		ConcurrentHashMap<Point, Objects> animatedObjects = map.getAnimatedObjects();
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
				object.setPosition(new Point(p.x*size, p.y*size));                          
				animatedObjects.put(p, object);
				colisionMap[p.x][p.y] = ColisionMapObjects.FIRE;

				/* UP */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(ResourcesManager.getPoint(p.x, p.y-k)) != null ) {
						this.bombs.get(ResourcesManager.getPoint(p.x, p.y-k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap[p.x][p.y-k] != ColisionMapObjects.BLOCK) {
						/* On affiche le feu */
						if ( k < bomb.getPower()-1 ) {
							object = ResourcesManager.getObjects().get("firevertical").copy();
						}
						else {
							object = ResourcesManager.getObjects().get("fireup").copy();
						}
						object.setPosition(new Point(p.x*size, (p.y-k)*size));
						animatedObjects.put(new Point(p.x,p.y-k), object);
						colisionMap[p.x][p.y-k] = ColisionMapObjects.FIRE;
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(ResourcesManager.getPoint(p.x, p.y-k)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap[p.x][p.y-k] == ColisionMapObjects.FIRE ) {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firevertical").copy();
							}
							else {
								object = ResourcesManager.getObjects().get("fireup").copy();
							}
							object.setPosition(new Point(p.x*size, (p.y-k)*size));
							animatedObjects.put(new Point(p.x,p.y-k), object);
						}
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(ResourcesManager.getPoint(p.x, p.y-k)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(ResourcesManager.getPoint(p.x, p.y-k)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* DOWN */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(ResourcesManager.getPoint(p.x, p.y+k)) != null ) {
						this.bombs.get(ResourcesManager.getPoint(p.x, p.y+k)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap[p.x][p.y+k] != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
						if ( k < bomb.getPower()-1 ) {
							object = ResourcesManager.getObjects().get("firevertical").copy();
						}
						else {
							object = ResourcesManager.getObjects().get("firedown").copy();
						}
						object.setPosition(new Point(p.x*size, (p.y+k)*size));
						animatedObjects.put(new Point(p.x,p.y+k), object);
						colisionMap[p.x][p.y+k] = ColisionMapObjects.FIRE;
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(ResourcesManager.getPoint(p.x, p.y+k)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap[p.x][p.y+k] == ColisionMapObjects.FIRE ) {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firevertical").copy();
							}
							else {
								object = ResourcesManager.getObjects().get("firedown").copy();
							}
							object.setPosition(new Point(p.x*size, (p.y+k)*size));
							animatedObjects.put(new Point(p.x,p.y+k), object);
						}
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(ResourcesManager.getPoint(p.x, p.y+k)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(ResourcesManager.getPoint(p.x, p.y+k)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* LEFT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(ResourcesManager.getPoint(p.x-k, p.y)) != null ) {
						this.bombs.get(ResourcesManager.getPoint(p.x-k, p.y)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap[p.x-k][p.y] != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
						if ( k < bomb.getPower()-1 ) {
							object = ResourcesManager.getObjects().get("firehorizontal").copy();
						}
						else {
							object = ResourcesManager.getObjects().get("fireleft").copy();
						}
						object.setPosition(new Point((p.x-k)*size, p.y*size));
						animatedObjects.put(new Point(p.x-k,p.y), object);
						colisionMap[p.x-k][p.y] = ColisionMapObjects.FIRE;
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(ResourcesManager.getPoint(p.x-k, p.y)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap[p.x-k][p.y] == ColisionMapObjects.FIRE ) {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firehorizontal").copy();
							}
							else {
								object = ResourcesManager.getObjects().get("fireleft").copy();
							}
							object.setPosition(new Point((p.x-k)*size, p.y*size));
							animatedObjects.put(new Point(p.x-k,p.y), object);
						}
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(ResourcesManager.getPoint(p.x-k, p.y)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(ResourcesManager.getPoint(p.x-k, p.y)).isFireWall() ) {
								/* On break */
								break;
							}
						}
					}
				}

				/* RIGHT */
				for ( int k = 1 ; k < bomb.getPower() ; k++ ) {
					/* Si une bombe est présente */
					if ( this.bombs.get(ResourcesManager.getPoint(p.x+k, p.y)) != null ) {
						this.bombs.get(ResourcesManager.getPoint(p.x+k, p.y)).destroy();
					}
					/* Si il n'y a pas de block */
					else if ( colisionMap[p.x+k][p.y] != ColisionMapObjects.BLOCK ) {
						/* On affiche le feu */
						if ( k < bomb.getPower()-1 ) {
							object = ResourcesManager.getObjects().get("firehorizontal").copy();
						}
						else {
							object = ResourcesManager.getObjects().get("fireright").copy();
						}
						object.setPosition(new Point((p.x+k)*size, p.y*size));
						animatedObjects.put(new Point(p.x+k,p.y), object);
						colisionMap[p.x+k][p.y] = ColisionMapObjects.FIRE;
					}
					/* Si il y a un block */
					else {
						/* Si il n'est pas destructible */
						if ( animatedObjects.get(ResourcesManager.getPoint(p.x+k, p.y)) == null ) {
							/* On break */
							break;
						}
						/* Si c'est du feu */
						else if ( colisionMap[p.x+k][p.y] == ColisionMapObjects.FIRE ) {
							if ( k < bomb.getPower()-1 ) {
								object = ResourcesManager.getObjects().get("firehorizontal").copy();
							}
							else {
								object = ResourcesManager.getObjects().get("fireright").copy();
							}
							object.setPosition(new Point((p.x+k)*size, p.y*size));
							animatedObjects.put(new Point(p.x+k,p.y), object);
						}
						/* Si il est destructible */
						else {
							/* On le detruit */
							animatedObjects.get(ResourcesManager.getPoint(p.x+k, p.y)).destroy();
							/* Si il ne laisse pas passer le feu */
							if ( animatedObjects.get(ResourcesManager.getPoint(p.x+k, p.y)).isFireWall() ) {
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
