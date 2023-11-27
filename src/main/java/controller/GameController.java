package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameController {

	@FXML
	private GridPane mainGrid;

	@FXML
	private Button playButton;

	@FXML
	public void initialize() {

	}

	@FXML
	public void startGame() {

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Image image = new Image("/images/duck.png");
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(112.5);
				imageView.setFitWidth(112.5);
				imageView.setPreserveRatio(true);
				mainGrid.add(imageView, i, j);
			}
		}

	}

}
