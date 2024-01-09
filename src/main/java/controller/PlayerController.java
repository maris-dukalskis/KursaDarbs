package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Board;
import model.Color;
import model.Game;
import model.Player;

public class PlayerController implements Initializable {

	private static Game gameInstance;

	public static Game getGame() {
		return gameInstance;
	}

	@FXML
	private TextField player1_input_text, player2_input_text;
	@FXML
	private Button game_start_button;
	@FXML
	private ChoiceBox<String> player1StartColor, player2StartColor;
	@FXML
	private Label label1, label2;
	public String text1, text2;
	private Stage primaryStage;
	private String[] option = { "WHITE", "BLACK" };

	public void initialize(URL url, ResourceBundle resourceBundle) {
		player1StartColor.getItems().addAll(option);

		player2StartColor.getItems().addAll(option);

	}

	@FXML
	private void startGame(ActionEvent event) throws IOException {
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
			player1Color = Color.valueOf(player2ColorText);
		}
		// TODO sadalit pa krasam
		// TODO default names nestrada
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
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle(player1Name + " vs " + player2Name);
	}

}
