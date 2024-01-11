package service;

import controller.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainService extends Application {

	// TODO
	// coinflip to choose colors
	// Rokāde
	// enemy check(parāda ekrāna vidū)
	// enemy check mate(beidzas spēle)
	// pawn promotion

	public static double mainImageSize = 93.75;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		BackgroundMusicPlayer.playBackgroundMusic("/audio/start_scene.wav");
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