package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Board;
import model.Tile;

public class GameController {

	@FXML
	private GridPane mainGrid;

	@FXML
	private VBox vboxWhite;

	@FXML
	private VBox vboxBlack;

	@FXML
	private TextArea timerWhite;

	@FXML
	private TextArea timerBlack;

	@FXML
	private GridPane whitePiecesOut;

	@FXML
	private GridPane blackPiecesOut;

	@FXML
	public void initialize() {

		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				String imageString = "";

				Tile tile = Board.getTile((byte) i, (byte) j);
				if (tile.getPiece() != null) {
					String pieceColor = Board.getTile((byte) i, (byte) j).getPiece().getColor().name().toLowerCase();
					imageString += pieceColor + "_";
					String pieceType = Board.getTile((byte) i, (byte) j).getPiece().getPieceType().name().toLowerCase();
					imageString += pieceType + "_on";
				}
				String tileColor = "";
				if (((i + j) % 2) == 0) {
					tileColor = "White.jpg";
				} else {
					tileColor = "Black.jpg";
				}
				imageString += tileColor;

				Image image = new Image("/images/" + imageString);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(93.75);
				imageView.setFitWidth(93.75);
				imageView.setPreserveRatio(true);

				// viņš sākumā ņem column pēctam row
				mainGrid.add(imageView, j, i);
			}
		}

		vboxWhite.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		vboxBlack.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));

		timerWhite.setText("60:00");
		timerWhite.setEditable(false);
		timerBlack.setText("60:00");
		timerBlack.setEditable(false);

		whitePiecesOut.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		blackPiecesOut.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
	}

}