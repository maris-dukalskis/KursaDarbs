package controller;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Board;
import model.Color;
import model.Game;
import model.Player;

public class PlayerController {

	private static Game gameInstance;

	public static Game getGame() {
		return gameInstance;
	}

	@FXML
	private TextField player1_input_text, player2_input_text;
	@FXML
	private ChoiceBox<String> player1StartColor, player2StartColor;
	@FXML
	private Slider volumeSlider;

	private Stage primaryStage;

	@FXML
	public void initialize() {
		player1StartColor.getItems().addAll("WHITE", "BLACK");
		player2StartColor.getItems().addAll("WHITE", "BLACK");
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			double volume = newValue.doubleValue();
			BackgroundMusicPlayer.setVolume(volume);
		});
	}

	@FXML
	private void startGame() throws IOException {
		BackgroundMusicPlayer.stopBackgroundMusic();
		BackgroundMusicPlayer.playBackgroundMusic("/audio/game_scene.wav");
		String player1Name = player1_input_text.getText();
		if (player1Name.isEmpty()) {
			player1Name = "Player1";
		}
		String player2Name = player2_input_text.getText();
		if (player2Name.isEmpty()) {
			player2Name = "Player2";
		}

		Color player1Color = Color.WHITE;
		String player1ColorText = player1StartColor.getValue();
		if (player1ColorText != null) {
			player1Color = Color.valueOf(player1ColorText);
		}
		Color player2Color = Color.WHITE;
		String player2ColorText = player2StartColor.getValue();
		if (player2ColorText != null) {
			player2Color = Color.valueOf(player2ColorText);
		}

		if (player1Color.equals(player2Color)) {
			Random random = new Random();
			int value = random.nextInt(2);
			if (value == 0) {
				player1Color = Color.WHITE;
				player2Color = Color.BLACK;
			} else {
				player1Color = Color.BLACK;
				player2Color = Color.WHITE;
			}
		}

		Player player1 = new Player(player1Name, player1Color);
		Player player2 = new Player(player2Name, player2Color);

		Board mainBoard = new Board(8, 8);
		mainBoard.placeInitialPieces();
		Board whitePiecesOutBoard = new Board(3, 5);
		Board blackPiecesOutBoard = new Board(3, 5);

		Game game = new Game(mainBoard, whitePiecesOutBoard, blackPiecesOutBoard, Color.WHITE);

		gameInstance = game;
		game.setPlayer1(player1);
		game.setPlayer2(player2);

		Scene myScene = FXMLLoader.load(getClass().getResource("/GameScene.fxml"));
		primaryStage = (Stage) ((Node) volumeSlider).getScene().getWindow();
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle(player1Name + " vs " + player2Name);

	}
}
