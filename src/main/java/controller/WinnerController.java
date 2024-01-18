package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Game;
import model.Player;

public class WinnerController {

	@FXML
	Label winnerLabel, loserLabel;

	@FXML
	Button playAgainButton, exitButton;

	@FXML
	public void initialize() {

		Game game = PlayerController.getGame();

		Player winner = game.getPlayerByColor(game.getMove().opposite());
		Player loser = game.getPlayerByColor(game.getMove());

		winnerLabel.setText(winner.getName());
		loserLabel.setText(loser.getName());

	}

	@FXML
	public void playAgainClick(ActionEvent event) throws IOException {
		Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Player selection");

	}

	@FXML
	public void exitButtonClick(ActionEvent event) throws IOException {
		System.exit(0);
	}

}
