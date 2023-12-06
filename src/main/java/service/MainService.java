package service;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainService extends Application {

//	 	TODO
//		coinflip to choose colors
//	 	generate getters and setters
//	 	create algorithms for each piece
//		

	public static void main(String[] args) {
		//Board board = new Board();
		//board.printBoard();

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));

		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
    	primaryStage.setTitle("Player selection");
	}

}
