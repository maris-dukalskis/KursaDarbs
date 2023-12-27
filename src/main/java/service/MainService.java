package service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Board;
import model.Game;

public class MainService extends Application {

//	 	TODO
//		coinflip to choose colors
//		add checks for mate and checkmate
// 		add checks for piece moves
//		add green tile colors for possible moves

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
		board.printBoard();
		boardInstance = board;
		// vajadzēs pārlikt(game objektu izveido tikai kad sāk spēli)
		Game newGame = new Game();
		game = newGame;
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/StartGame.fxml"));
		Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		String css = this.getClass().getResource("/style.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Start Game");
		stage.setScene(scene);
		stage.show();

	}

}
