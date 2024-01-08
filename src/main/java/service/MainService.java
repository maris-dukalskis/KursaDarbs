package service;

import controller.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Board;
import model.Color;
import model.Game;


public class MainService extends Application {


	private static Game game;

	public static Game getGame() {
		return game;
	}

	private static Board boardInstance;

	public static Board getBoard() {
		return boardInstance;
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.placeInitialPieces();
		boardInstance = board;
		// vajadzēs pārlikt(game objektu izveido tikai kad sāk spēli)
		Game newGame = new Game();
		newGame.setMove(Color.WHITE);
		game = newGame;
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		BackgroundMusicPlayer.playBackgroundMusic("/skanja.wav");
		Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
		Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		String css = this.getClass().getResource("/style.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Main menu");
		stage.setScene(scene);
		stage.show();

	}

}
