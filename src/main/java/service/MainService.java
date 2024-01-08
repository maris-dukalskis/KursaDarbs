package service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Board;
import model.Color;
import model.Game;
import java.io.IOException;

public class MainService extends Application {
	private Stage primaryStage;

	// TODO
	// coinflip to choose colors
	// Rokāde
	// enemy check(parāda ekrāna vidū)
	// enemy check mate(beidzas spēle)
	// pawn promotion

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

		Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
		Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		String css = this.getClass().getResource("/style.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Main menu");
		stage.setScene(scene);
		stage.show();

	}
	
	//@FXML
	//public void initialize()throws IOException{
	//	showResultScene();
	//}
	
	private void showResultScene() throws IOException{
		try {
			System.out.println("Trying");
		Scene myScene = FXMLLoader.load(getClass().getResource("/resultScene.fxml"));
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Result");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
