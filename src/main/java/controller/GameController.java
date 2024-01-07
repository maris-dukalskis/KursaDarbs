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
import model.Board;
import model.Color;
import model.Game;
import model.Moves;
import model.Piece;
import model.Tile;

public class GameController {

	private static Game gameInstance;

	public static Game getGame() {
		return gameInstance;
	}

	private static Board boardInstance;

	public static Board getBoard() {
		return boardInstance;
	}

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

		Board mainBoard = new Board(8, 8);
		mainBoard.placeInitialPieces();
		boardInstance = mainBoard;
		Board whitePiecesOutBoard = new Board(3, 5);
		Board blackPiecesOutBoard = new Board(3, 5);
		Game newGame = new Game(mainGrid, whitePiecesOut, blackPiecesOut, mainBoard, whitePiecesOutBoard,
				blackPiecesOutBoard, Color.WHITE);
		gameInstance = newGame;

		generateGraphicalGrid();

		Border border = new Border(
				new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, null));

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

	public static void generateGraphicalGrid() {
		for (byte i = 0; i <= 7; i++) {
			for (byte j = 0; j <= 7; j++) {
				Tile tile = getBoard().getTile((byte) i, (byte) j);
				generateAndSetImage(tile, i, j, getGame().getMainGrid(), true, 93.75, 93.75);
			}
		}
	}

	public static void addKnockedOutPiece(Piece piece, GridPane grid, Board board) {
		outer: for (byte i = 0; i <= 2; i++) {
			for (byte j = 0; j <= 4; j++) {
				Tile tile = board.getTile((byte) i, (byte) j);
				if (tile.getPiece() != null) {
					continue;
				}
				tile.setPiece(piece);
				generateAndSetImage(tile, i, j, grid, false, 50, 50);
				break outer;
			}
		}
	}

	public static void generateAndSetImage(Tile tile, byte i, byte j, GridPane grid, boolean setClickEvent,
			double height, double width) {
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
		setImage(tile, imageString, i, j, grid, setClickEvent, height, width);
	}

	public static void setImage(Tile tile, String imageString, int i, int j, GridPane grid, boolean setClickEvent,
			double height, double width) {
		Image image = new Image("/images/" + imageString);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height); // gridpane platums un garums dalīts ar 8 93.75
		imageView.setFitWidth(width);
		imageView.setPreserveRatio(true);

		/*
		 * Šis ir gandrīz tas pats kas parasta metode tikai uzrakstīts nedaudz citādi
		 * event ir kā metodei padotais mainīgais viss kas ir {} , ir tas ko metode
		 * izpilda katru reizi kad uzspiež uz konkrētās bildes tiek palaista šī metodei
		 */
		if (setClickEvent) {
			imageView.setOnMouseClicked(event -> {
				Moves.processClickedTile(tile);
			});
		}

		// viņš sākumā ņem column pēctam row(mums board ir row un tad column)
		grid.add(imageView, j, i);
	}
}
