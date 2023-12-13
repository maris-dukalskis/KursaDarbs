package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class PlayerController {
    @FXML
    private TextField player1_input_text;
    @FXML
    private TextField player2_input_text;
    @FXML
    private ToggleButton player1_yes_option , player1_no_option , player2_yes_option , player2_no_option;
    @FXML
    private Button game_start_button;
    private String text1, text2;


    @FXML
    private void startGame(ActionEvent event){

            text1 = player1_input_text.getText();
            text2 = player2_input_text.getText();

            if(text1 != null){
                text1 = "Player1";
            }
            if(text2 != null){
                text2 = "Player2";
            }

    }

}
