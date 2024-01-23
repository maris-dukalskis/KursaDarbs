package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Board;
import model.Color;
import model.Game;
import model.GameLogic;
import model.GameState;
import model.Move;
import model.Piece;
import model.PieceType;
import model.Player;
import model.Tile;
import service.MainService;

public class GameController {

	private static GameController instance;

	public static GameController getInstance() {
		return instance;
	}

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private GridPane mainGrid;

	@FXML
	private VBox whiteVBox, blackVBox;

	@FXML
	private Label whitePlayerNameLabel, blackPlayerNameLabel;

	@FXML
	private Label whitePlayerTimerLabel, blackPlayerTimerLabel;

	@FXML
	private GridPane whitePiecesOutGridPane, blackPiecesOutGridPane;

	@FXML
	private Slider volumeSlider;

	@FXML
	private Label currentMoveColorLabel;

	@FXML
	private Button blackDrawButton, blackResignButton, whiteDrawButton, whiteResignButton;

	@FXML
	public void initialize() {
		instance = this;

		Game game = PlayerController.getGame();

		volumeSlider.setValue(BackgroundMusicPlayer.getVolume());

		game.getBoard().setGrid(mainGrid);
		game.getWhitePiecesOutBoard().setGrid(whitePiecesOutGridPane);
		game.getBlackPiecesOutBoard().setGrid(blackPiecesOutGridPane);
		game.setAnchorPane(anchorPane);
		game.setCurrentMoveColorLabel(currentMoveColorLabel);
		Player whitePlayer = game.getWhitePlayer();
		Player blackPlayer = game.getBlackPlayer();
		whitePlayer.setTimerLabel(whitePlayerTimerLabel);
		blackPlayer.setTimerLabel(blackPlayerTimerLabel);

		generateGraphicalGrid(true);

		Border border = new Border(
				new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, null));

		whiteVBox.setBorder(border);
		blackVBox.setBorder(border);

		whitePlayerNameLabel.setText(whitePlayer.getName());
		blackPlayerNameLabel.setText(blackPlayer.getName());

		long timer = 3600000;

		whitePlayerTimerLabel.setText(formatTimer(timer));
		blackPlayerTimerLabel.setText(formatTimer(timer));

		whitePiecesOutGridPane.setBorder(border);
		blackPiecesOutGridPane.setBorder(border);

		currentMoveColorLabel.setText("Current move: " + game.getMove().name());
		// skaņas regulēšana
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			double volume = newValue.doubleValue();
			BackgroundMusicPlayer.setVolume(volume);
		});

		blackDrawButton.setOnMouseClicked(event -> {
			drawButtonLogic(game.getMove(), Color.BLACK);
		});
		whiteDrawButton.setOnMouseClicked(event -> {
			drawButtonLogic(game.getMove(), Color.WHITE);
		});
		blackResignButton.setOnMouseClicked(event -> {
			resignButtonLogic(game.getMove(), Color.BLACK);
		});
		whiteResignButton.setOnMouseClicked(event -> {
			resignButtonLogic(game.getMove(), Color.WHITE);
		});

	}

	public static void exitApplication() {
		Game game = PlayerController.getGame();

		// pie timera pārbauda vai ir kāda no krāsām, ja nav krāsa tad timeris apstājas
		game.setMove(null);
	}

	public static String formatTimer(long timer) {
		long hours = timer / 1000 / 60 / 60;
		// mums it kā vairāk par stundu nevajadzētu būt, tāpēc nevajag minūtes un
		// sekundes
		if (hours == 1) {
			return hours + ":00:00";
		}
		long minutes = timer / 1000 / 60 % 60;
		long seconds = timer / 1000 % 60;
		if (minutes >= 1) {
			return "00:" + minutes + ":" + seconds;
		}
		return "00:00:" + seconds;
	}

	public void popUps(GameState gameState) {
		Game game = PlayerController.getGame();
		// izvedo popup ziņas programmā
		if (gameState.equals(GameState.CHECK)) {
			Alert checkAlert = new Alert(Alert.AlertType.INFORMATION);
			checkAlert.setTitle("Check");
			checkAlert.setHeaderText("CHECK");
			checkAlert.showAndWait();
		}
		if (gameState.equals(GameState.CHECK_MATE)) {
			game.setWinner(game.getMove().opposite());
			Alert checkMateAlert = new Alert(Alert.AlertType.NONE);
			checkMateAlert.setTitle("Check mate");
			checkMateAlert.setHeaderText("CHECK MATE");
			checkMateAlert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			// nezinu kā tas tieši strādā, bet strādā, tas laikam tas pats, kas click
			// eventi, viņš gaida kad uzspiež kaut kur
			Optional<ButtonType> result = checkMateAlert.showAndWait();
			if (result.get() == ButtonType.CLOSE) {
				try {
					moveToEndController("/WinnerScene.fxml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		if (gameState.equals(GameState.DRAW)) {
			Alert drawAlert = new Alert(Alert.AlertType.NONE);
			drawAlert.setTitle("Draw");
			drawAlert.setHeaderText("DRAW");
			drawAlert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			Optional<ButtonType> result = drawAlert.showAndWait();
			if (result.get() == ButtonType.CLOSE) {
				try {
					moveToEndController("/DrawScene.fxml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void updateTimer(Label label, String timer) {
		// ņemts no interneta, savieno tā, lai izpilda uz Main Thread,bez šī met error,
		// ka nav īstajā Thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				label.setText(timer);
			}
		});

	}

	public static void timerForPlayerWhite(Game game) {
		Thread player1Thread = new Thread(() -> {
			while (game.getMove() == Color.WHITE) {
				try {
					// 100, jo ja uzliek uz 1000 tad iespējams 2x timeri uzlikt, ja abi spēlētāji
					// uzspiež pārāk ātri
					Thread.sleep(100);
					game.getWhitePlayer().decreaseTimerTime();
					// ja timeris ir beidzies
					if (game.getWhitePlayer().getTimer() < 1000) {
						GameController.getInstance().showOutOfTimeAlert();
						game.setWinner(game.getMove().opposite());
						game.setMove(null);
					}

					updateTimer(game.getWhitePlayer().getTimerLabel(), formatTimer(game.getWhitePlayer().getTimer()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		player1Thread.start();
	}

	public static void timerForPlayerBlack(Game game) {
		Thread player2Thread = new Thread(() -> {
			while (game.getMove() == Color.BLACK) {
				try {
					Thread.sleep(100);
					game.getBlackPlayer().decreaseTimerTime();
					// ja timeris ir beidzies
					if (game.getBlackPlayer().getTimer() < 1000) {
						GameController.getInstance().showOutOfTimeAlert();
						game.setWinner(game.getMove().opposite());
						game.setMove(null);
					}
					updateTimer(game.getBlackPlayer().getTimerLabel(), formatTimer(game.getBlackPlayer().getTimer()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		player2Thread.start();
	}

	public static void generateGraphicalGrid(boolean setClickEvent) {
		for (byte i = 0; i <= 7; i++) {
			for (byte j = 0; j <= 7; j++) {
				Tile tile = PlayerController.getGame().getBoard().getTile((byte) i, (byte) j);
				generateAndSetImage(tile, i, j, PlayerController.getGame().getBoard().getGrid(), setClickEvent,
						MainService.mainImageSize, MainService.mainImageSize);
			}
		}
	}

	public static void addKnockedOutPiece(Piece piece, GridPane grid, Board board) {
		// outer ir kā mainīgā nosaukums for ciklam, tas, lai var ārējo ciklu beigt no
		// iekšējā
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

		// tas attīra grid no visiem iepriekšējiem imageview, pirms šī viņi vienkārši
		// krāsās līdz dators vairs nevarēja pavilkt līdzi
		if (tile.getView() != null && grid.getChildren().contains(tile.getView())) {
			grid.getChildren().remove(tile.getView());
		}
		tile.setView(imageView);
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

	public static void displayPawnPromotion(Piece piece, Tile tile, Game game) {
		Color pieceColor = piece.getColor();

		double tileLength = (PlayerController.getGame().getBoard().getGrid().getWidth() / 8);
		// ja rinda indeks ir lielāks par 4 tad iedod 4, citādi izvēlni parāda ārpus
		// scene
		double rowPosition = tileLength * (tile.getRow() >= 4 ? 4 : tile.getRow());
		double columnPosition = tileLength * tile.getColumn();
		List<PieceType> pieceTypes = new ArrayList<>();
		pieceTypes.add(PieceType.BISHOP);
		pieceTypes.add(PieceType.QUEEN);
		pieceTypes.add(PieceType.KNIGHT);
		pieceTypes.add(PieceType.ROOK);
		GridPane grid = new GridPane();
		game.getAnchorPane().getChildren().add(grid);
		grid.setLayoutX(columnPosition);
		grid.setLayoutY(rowPosition);
		for (int i = 0; i < 4; i++) {
			PieceType type = pieceTypes.get(i);
			String imageString = pieceColor.name().toLowerCase() + "_" + type.name().toLowerCase() + "_onYellow.jpg";
			Image image = new Image("/images/" + imageString);
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(MainService.mainImageSize);
			imageView.setFitWidth(MainService.mainImageSize);
			imageView.setPreserveRatio(true);

			imageView.setOnMouseClicked(event -> {
				tile.setPiece(new Piece(pieceColor, type));
				GameLogic.processPostMove(game);
				game.getAnchorPane().getChildren().remove(grid);
			});

			grid.add(imageView, 0, i);
		}
	}

	public void resignButtonLogic(Color move, Color buttonPressed) {
		if (move != buttonPressed) {
			return;
		}
		PlayerController.getGame().setWinner(move);
		try {
			moveToEndController("/WinnerScene.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawButtonLogic(Color move, Color buttonPressed) {
		Game game = PlayerController.getGame();
		if (!game.getPlayerByColor(buttonPressed.opposite()).isDraw()) {
			game.getPlayerByColor(buttonPressed).setDraw(true);
			return;
		}
		game.setGameState(GameState.DRAW);
		try {
			moveToEndController("/DrawScene.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveToEndController(String fxml) throws IOException {
		volumeSlider.setValue(BackgroundMusicPlayer.getVolume());
		BackgroundMusicPlayer.stopBackgroundMusic();
		BackgroundMusicPlayer.playBackgroundMusic("/audio/end_scene.wav");
		Scene myScene = FXMLLoader.load(getClass().getResource(fxml));
		Stage primaryStage = (Stage) ((Node) volumeSlider).getScene().getWindow();
		primaryStage.setScene(myScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Game Over");
		BackgroundMusicPlayer.setVolume(volumeSlider.getValue());
	}

	public void showOutOfTimeAlert() {
		// tas pats kas ar timer update
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				GameController.generateGraphicalGrid(false);
				Alert alert = new Alert(Alert.AlertType.NONE);
				alert.setTitle("Out of time");
				alert.setHeaderText("Out of time");
				alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.CLOSE) {
					try {
						moveToEndController("/WinnerScene.fxml");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}
}