package controller;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Player;
import javafx.scene.Node;

import model.Game;

import java.io.IOException;

//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
public class ResultController {
	
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
    private Label textTime;

    @FXML
    private Label textTime1;
    
    
    @FXML
    public void playAgainClick(ActionEvent event) throws IOException {
 	   Object source = event.getSource();
 	   if (source instanceof Button && ((Button) source).getId().equals("buttonPlayAgain")) {
 		   
 		 Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
     	primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
     	primaryStage.setScene(myScene);
     	primaryStage.show();
     	primaryStage.setResizable(false);
     	primaryStage.setTitle("Player selection");  
 	   }	
     }
    
    @FXML
	public void initialize() {
    	
    	Game game = PlayerController.getGame();
    	
    	Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();
		
    	labelWinnerName.setText(player1.getName());
    	labelLoserName.setText(player2.getName());
    }
    
    /* TODO pārveidot
    
    public void exitButtonClick(ActionEvent event) throws IOException {
 	   JFrame frame = new JFrame("Exit"); //izveido jaunu logu ar nosaukumu "Exit"(Java Swing komponents)
 	   if(JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", 
 			   JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION)//showConfirmDialog jābūt vienādām ar YES_NO_OPTION
 		   //YES_NO_OPTION vienmēr ir vienāds ar sevi
 	   {
 		   System.exit(0);
 	   }
	}
    */
    
    
    
}


	

