package controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

	public class TutorialSceneController {
		
		private Stage primaryStage;

	    @FXML
	    private AnchorPane AnchorPane;

	    @FXML
	    private Button gotItButton;

	    
	    @FXML
	    public void GotItClick(ActionEvent event) throws IOException {
	 	   Object source = event.getSource();
	 	   if (source instanceof Button && ((Button) source).getId().equals("gotItButton")) {
	 		   
	 		 Scene myScene = FXMLLoader.load(getClass().getResource("/PlayerSelection.fxml"));
	     	primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	     	primaryStage.setScene(myScene);
	     	primaryStage.show();
	     	primaryStage.setResizable(false);
	     	primaryStage.setTitle("Player selection");  
	 	   }	
	     }


}


