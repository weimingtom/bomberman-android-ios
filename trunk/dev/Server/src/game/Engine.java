package game;

public class Engine {
	
	private Game game;
	
	public Engine(Game game) {
		super();
		this.game = game;
	}

	/**
	 * Get the instance of the Game
	 * @return Game game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Set the instance of the Game
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
}