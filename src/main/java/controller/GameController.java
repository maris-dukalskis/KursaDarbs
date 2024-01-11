package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;
import service.MainService;
import model.Game;

public class GameController {

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
    private Slider volumeSlider;

	@FXML
	public void initialize() {

		Game game = PlayerController.getGame();

		game.getBoard().setGrid(mainGrid);
		game.getWhitePiecesOutBoard().setGrid(whitePiecesOut);
		game.getBlackPiecesOutBoard().setGrid(blackPiecesOut);
		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();

		generateGraphicalGrid();

		Border border = new Border(
				new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, null));

		vboxWhite.setBorder(border);
		vboxBlack.setBorder(border);

		whitePlayerLabel.setText(player1.getName());
		blackPlayerLabel.setText(player2.getName());

		/*
		 * TODO
		 */
		int counterForPlayer1  = 3600000;
		int counterForPlayer2 = 3600000;
		// parveidot uz pulkstena laiku
		timerWhiteLabel.setText(String.valueOf(counterForPlayer1/600));
		timerBlackLabel.setText(String.valueOf(counterForPlayer2/600));

		whitePiecesOut.setBorder(border);
		blackPiecesOut.setBorder(border);
		//skaņas regulēšana
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {         
            double volume = newValue.doubleValue();
            BackgroundMusicPlayer.setVolume(volume);
        });
	}
	public static void popUps(GameState gameState){
		// izvedo popup ziņas programmā
			if(gameState.equals(GameState.CHECK)){
				Alert checkAlert = new Alert(Alert.AlertType.WARNING);
				checkAlert.setTitle("Check");
				checkAlert.setContentText("CHECK");
				checkAlert.showAndWait();
			}
			if(gameState.equals(GameState.CHECK_MATE)){
				Alert checkMateAlert = new Alert(Alert.AlertType.ERROR);
				checkMateAlert.setTitle("Check mate");
				checkMateAlert.setContentText("CHECK MATE");
				checkMateAlert.showAndWait();
			}
			if(gameState.equals(GameState.DRAW)){
				Alert drawAlert = new Alert(Alert.AlertType.NONE);
				drawAlert.setTitle("Draw");
				drawAlert.setContentText("DRAW");
				//drawAlert.setGraphic(new ImageView(this.getClass().getResource("Draw.png").toString()));
				drawAlert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				drawAlert.showAndWait();

			}


	}
	
	public static void timerForPlayer1(){
		//TODO
	}
	public static void timerForPlayer2(){
		//TODO
	}
	public static void generateGraphicalGrid() {
		for (byte i = 0; i <= 7; i++) {
			for (byte j = 0; j <= 7; j++) {
				Tile tile = PlayerController.getGame().getBoard().getTile((byte) i, (byte) j);
				generateAndSetImage(tile, i, j, PlayerController.getGame().getBoard().getGrid(), true,
						MainService.mainImageSize, MainService.mainImageSize);
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
			imageString += tile.getPiece().getColor().name().toLowerCase() + "_"
					+ tile.getPiece().getType().name().toLowerCase() + "_on";
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
				GameLogic.processClickedTile(tile);
			});
		}

		// viņš sākumā ņem column pēctam row(mums board ir row un tad column)
		grid.add(imageView, j, i);
	}

	public static void displayValidMoves(Tile clickedTile) {
		List<Move> moveList = GameLogic.generateAllMovesForColor(clickedTile.getPiece().getColor(), true);
		for (Move move : moveList) {
			Tile fromTile = move.getFromTile();
			if (!fromTile.equals(clickedTile)) {
				continue;
			}
			Tile toTile = move.getToTile();
			int i = toTile.getRow();
			int j = toTile.getColumn();
			String imageString = "";
			Piece piece = toTile.getPiece();
			if (piece == null) {
				if (((i + j) % 2) == 0) {
					imageString = "Green.jpg";
				} else {
					imageString = "Dark_Green.jpg";
				}
			} else {
				imageString += piece.getColor().name().toLowerCase() + "_" + piece.getType().name().toLowerCase();
				if (piece.getColor() == clickedTile.getPiece().getColor()) {
					imageString += "_onGreen.jpg";
				} else {
					imageString += "_onRed.jpg";
				}
			}
			GameController.setImage(toTile, imageString, i, j, PlayerController.getGame().getBoard().getGrid(), true,
					MainService.mainImageSize, MainService.mainImageSize);
		}
	}
}