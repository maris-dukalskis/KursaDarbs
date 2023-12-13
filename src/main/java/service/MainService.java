package service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
