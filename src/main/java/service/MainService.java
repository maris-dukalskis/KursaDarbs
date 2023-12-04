package service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Board;

public class MainService extends Application{
	
	Button button, button2;
	

//	 	TODO
//		coinflip to choose colors
//	 	generate getters and setters
//		

	public static void main(String[] args) {
		launch(args);
		Board board = new Board();
		board.printBoard();
	}
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/StartGame.fxml"));
		Scene scene = new Scene(root);
		
		stage.setTitle("Start Game");
		stage.setScene(scene);
		stage.show();
		
		
}
}
