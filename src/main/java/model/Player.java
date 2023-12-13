package model;


public class Player {
	private String name;
	private int moveCount = 0;
	private GameState gameState;
	private Color color;

	public Player(String name, GameState gameState, Color color) {
		setName(name);
		setGameState(gameState);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && name.matches("[A-Z]{1}[a-z]+")) {
			this.name = name;
		} else {
			this.name = "Invalid name";
		}
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		this.moveCount++;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		if (gameState != null) {
			this.gameState = gameState;
		} else {
			this.gameState = GameState.NORMAL;
		}
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
