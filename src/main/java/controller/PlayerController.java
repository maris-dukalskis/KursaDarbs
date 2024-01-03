package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.io.IOException;

public class PlayerController extends Game {
	@FXML
	private TextField player1_input_text;
	@FXML
	private TextField player2_input_text;
	@FXML
	private ToggleButton player1_yes_option, player1_no_option, player2_yes_option, player2_no_option;
	@FXML
	private Button game_start_button;
	public String text1, text2;
	private Stage primaryStage;
	@FXML
	private void startGame(ActionEvent event) throws IOException {
		text1 = player1_input_text.getText();
		text2 = player2_input_text.getText();
		if (text1.equals("")) {
			text1 = "Player1";
		}
		if (text2.equals("")) {
			text2 = "Player2";
		}


		Scene myScene = FXMLLoader.load(getClass().getResource("/GameScene.fxml"));
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle(text1 + " vs " + text2);
	}

}
