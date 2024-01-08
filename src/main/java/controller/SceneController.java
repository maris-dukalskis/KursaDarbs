package controller;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
public class SceneController{
	
	private Stage primaryStage;
	
	@FXML
    private ImageView background;

    @FXML
    private Label labelYouTried;

    @FXML
    private Label labelWinner;

    @FXML
    private Label labelLoser;

    @FXML
    private Button buttonPlayAgain;

    @FXML
    private Button buttonExit;

    @FXML
    private Label labelWinnerName;

    @FXML
    private Label labelLoserName;

    @FXML
    private Label ltextPoints;

    @FXML
    private Label ltextPoints2;

    @FXML
    private Label winnerPoints;

    @FXML
    private Label loserPoints;

    @FXML
    private Label textTime;

    @FXML
    private Label textTime1;
    
    
    
    @FXML
    public void playAgainClick(ActionEvent event) throws IOException {
    	Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
    	primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	primaryStage.setScene(myScene);
    	primaryStage.show();
    	primaryStage.setResizable(false);
    	primaryStage.setTitle("Player selection");
    	
    }
    
    
    
    
    	
   
	
	
	}


	

