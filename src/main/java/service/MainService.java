package service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Board;
import model.Game;

public class MainService extends Application {

//	 	TODO
//		coinflip to choose colors
//		add checks for mate and checkmate
// 		add checks for piece moves
//		add green tile colors for possible moves
	
	private static Game instance;
	
	public static Game getGame() {
		return instance;
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.printBoard();
		
		// vajadzēs pārlikt(game objektu izveido tikai kad sāk spēli)
		Game newGame = new Game();
		instance = newGame;
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene myScene = FXMLLoader.load(getClass().getResource("/GameScene.fxml"));

		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("{Player1} vs {Player2}");
	}

}
