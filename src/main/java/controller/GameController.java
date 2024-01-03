package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Moves;
import model.Tile;
import service.MainService;

public class GameController extends PlayerController {

	@FXML
	private GridPane mainGrid;

	@FXML
	private VBox vboxWhite;

	@FXML
	private VBox vboxBlack;

	@FXML
	private Label whitePlayerLabel;

	@FXML
	private Label blackPlayerLabel;

	@FXML
	private Label timerWhiteLabel;

	@FXML
	private Label timerBlackLabel;

	@FXML
	private GridPane whitePiecesOut;

	@FXML
	private GridPane blackPiecesOut;

	@FXML
	public void initialize() {
		generateGraphicalGrid(mainGrid);

		Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));

		vboxWhite.setBorder(border);
		vboxBlack.setBorder(border);

		whitePlayerLabel.setText("Player1 ");
		blackPlayerLabel.setText("Player2 ");

		/*
		 * pagaidām laiks ir tikai skatam, pēctam būs jāpievieno īsts timeris
		 */
		timerWhiteLabel.setText("60:00");
		timerBlackLabel.setText("60:00");

		whitePiecesOut.setBorder(border);
		blackPiecesOut.setBorder(border);
	}

	public static void generateGraphicalGrid(GridPane mainGrid) {
		for (byte i = 0; i <= 7; i++) {
			for (byte j = 0; j <= 7; j++) {
				Tile tile = MainService.getBoard().getTile((byte) i, (byte) j);
				generateAndSetImage(tile, i, j, mainGrid);
			}
		}
	}

	public static void generateAndSetImage(Tile tile, byte i, byte j, GridPane mainGrid) {
		String imageString = "";

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
		setImage(tile, imageString, i, j, mainGrid);
	}

	public static void setImage(Tile tile, String imageString, int i, int j, GridPane mainGrid) {
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
			Moves.processClickedTile(tile, mainGrid);
		});

		// viņš sākumā ņem column pēctam row(mums board ir row un tad column)
		mainGrid.add(imageView, j, i);
	}
}
