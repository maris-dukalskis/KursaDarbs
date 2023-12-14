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
import model.Gajieni;
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
		generateGraphicalGrid();

		Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));

		vboxWhite.setBorder(border);
		vboxBlack.setBorder(border);

		/*
		 * pagaidām laiks ir tikai skatam, pēctam būs jāpievieno īsts timeris
		 */
		timerWhite.setText("60:00");
		timerWhite.setEditable(false);
		timerBlack.setText("60:00");
		timerBlack.setEditable(false);

		whitePiecesOut.setBorder(border);
		blackPiecesOut.setBorder(border);
	}

	public void generateGraphicalGrid() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {

				String imageString = "";

				Tile tile = Board.getTile((byte) i, (byte) j);
				if (tile.getPiece() != null) {
					String pieceColor = tile.getPiece().getColor().name().toLowerCase();
					imageString += pieceColor + "_";
					String pieceType = tile.getPiece().getPieceType().name().toLowerCase();
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
				imageView.setFitHeight(93.75); // gridpane platums un garums dalīts ar 8
				imageView.setFitWidth(93.75);
				imageView.setPreserveRatio(true);

				/*
				 * Šis ir gandrīz tas pats kas parasta metode tikai uzrakstīts nedaudz citādi
				 * event ir kā metodei padotais mainīgais viss kas ir {} , ir tas ko metode
				 * izpilda katru reizi kad uzspiež uz konkrētās bildes tiek palaista šī metodei
				 */
				imageView.setOnMouseClicked(event -> {
					Gajieni.checkClickedTile(tile, mainGrid);
				});

				// viņš sākumā ņem column pēctam row(mums board ir row un tad column)
				mainGrid.add(imageView, j, i);
			}
		}
	}
}
