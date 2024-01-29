package service;

import controller.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainService extends Application {
	public static double mainImageSize = 93.75;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		BackgroundMusicPlayer.playBackgroundMusic("/audio/start_scene.wav");
		Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/style.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Main menu");
		stage.setScene(scene);
		stage.show();
		

	}

}