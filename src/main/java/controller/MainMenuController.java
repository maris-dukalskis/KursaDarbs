package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    private Stage primaryStage;

    @FXML
    private Slider volumeSlider;

    @FXML
    public void startGameClicked() throws IOException {
        BackgroundMusicPlayer.stopBackgroundMusic();
        BackgroundMusicPlayer.playBackgroundMusic("/audio/player_selection_scene.wav");
        Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
        primaryStage = new Stage();
        primaryStage.setScene(myScene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Player selection");
    }

    @FXML
    public void tutorialClicked() {
        // TODO Autogenerated
    }

    @FXML
    public void initialize() {        
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {         
            double volume = newValue.doubleValue();
            BackgroundMusicPlayer.setVolume(volume);
        });
    }
}
