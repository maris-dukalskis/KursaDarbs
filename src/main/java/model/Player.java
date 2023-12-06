package model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class Player {
	@FXML
	private String name;
	private int moveCount = 0;
	private GameState gameState;
	private Color color;
	private TextField player1_input_text;
	private TextField player2_input_text;
	private ToggleButton player1_yes_option , player1_no_option , player2_yes_option , player2_no_option;
	private Button game_start_button;


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
