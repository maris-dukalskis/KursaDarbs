package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class MainMenuController {

	private Stage primaryStage;
	private Stage secondaryStage;

	@FXML
	private Slider volumeSlider;

	@FXML
	public void startGameClicked() throws IOException {
		volumeSlider.setValue(BackgroundMusicPlayer.getVolume());
		BackgroundMusicPlayer.stopBackgroundMusic();
		BackgroundMusicPlayer.playBackgroundMusic("/audio/player_selection_scene.wav");
		Scene playerSelectionScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
		primaryStage = (Stage) ((Node) volumeSlider).getScene().getWindow();
		primaryStage.setScene(playerSelectionScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Player selection");

		BackgroundMusicPlayer.setVolume(volumeSlider.getValue());
	}

	@FXML
	public void tutorialClicked() throws IOException {
//		volumeSlider.setValue(BackgroundMusicPlayer.getVolume());
//		BackgroundMusicPlayer.stopBackgroundMusic();
//		BackgroundMusicPlayer.playBackgroundMusic("/audio/player_selection_scene.wav");
		Scene tutorialScene = FXMLLoader.load(getClass().getResource("/TutorialScene.fxml"));
		secondaryStage = (Stage) ((Node) volumeSlider).getScene().getWindow();
		secondaryStage.setScene(tutorialScene);
		secondaryStage.show();
		secondaryStage.setResizable(false);
		secondaryStage.setTitle("Tutorial");
	}

	@FXML
	public void initialize() {
		volumeSlider.setValue(BackgroundMusicPlayer.getVolume());

		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			double volume = newValue.doubleValue();
			BackgroundMusicPlayer.setVolume(volume);
		});
	}
}
