package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
public class ResultController {

	private Stage primaryStage;
	@FXML
	private ImageView background;

	@FXML
	private Label labelYouTried;

	@FXML
	private Label labelWinner;

	@FXML
	private Label labelLoser;

	@FXML
	private Button buttonPlayAgain;

	@FXML
	private Button buttonExit;

	@FXML
	private Label labelWinnerName;

	@FXML
	private Label labelLoserName;

	@FXML
	private Label textTime;

	@FXML
	private Label textTime1;

	@FXML
	public void playAgainClick(ActionEvent event) throws IOException {
		Object source = event.getSource();
		if (source instanceof Button && ((Button) source).getId().equals("buttonPlayAgain")) {

			Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
			primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(myScene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Player selection");
		}
	}

	@FXML
	/*
	 * public void initialize() {
	 * 
	 * Game game = PlayerController.getGame();
	 * 
	 * Player player1 = game.getPlayer1(); Player player2 = game.getPlayer2();
	 * 
	 * // TODO attiecīgi pēc uzvarētāja
	 * 
	 * labelWinnerName.setText(player1.getName());
	 * labelLoserName.setText(player2.getName()); }
	 * 
	 */

	public void exitButtonClick(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // izveido jaunu Alert logu
		alert.setTitle("Exit");
		// alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to exit?");

		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			System.exit(0);
		}

	}

}
