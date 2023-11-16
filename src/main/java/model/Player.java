package model;

public class Player {

	private String name;
	private String password;
	private int moveCount = 0;
	private GameState gameState;

	public Player(String name, String password, GameState gameState) {
		setName(name);
		setPassword(password);
		setGameState(gameState);
		
		//testing something else
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) { // move check to login page
		if (password != null && password.matches("^(?=.*[A-Za-z])(?=.*d)[A-Za-zd]{8,}$")) { // 8 characters,atleast 1
																							// letter, one number
			this.password = password;
		} else {
			this.password = null;
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

}
