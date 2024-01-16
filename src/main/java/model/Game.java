package model;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Game {

	private Player whitePlayer;
	private Player blackPlayer;
	private Tile fromTile;
	private Color move; // which players move it is - White or Black
	private GameState gameState;

	private Board board;
	private Board whitePiecesOutBoard;
	private Board blackPiecesOutBoard;
	private AnchorPane anchorPane;
	private Label currentMoveColorLabel;

	public Game(Board board, Board whitePiecesOutBoard, Board blackPiecesOutBoard, Color move) {
		this.board = board;
		this.whitePiecesOutBoard = whitePiecesOutBoard;
		this.blackPiecesOutBoard = blackPiecesOutBoard;
		this.move = move;
	}

	public Tile getFromTile() {
		return fromTile;
	}

	public void setFromTile(Tile fromTile) {
		this.fromTile = fromTile;
	}

	public Player getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(Player whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public Player getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(Player blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Color getMove() {
		return move;
	}

	public void setMove(Color move) {
		this.move = move;
	}

	public Board getWhitePiecesOutBoard() {
		return whitePiecesOutBoard;
	}

	public void setWhitePiecesOutBoard(Board whitePiecesOutBoard) {
		this.whitePiecesOutBoard = whitePiecesOutBoard;
	}

	public Board getBlackPiecesOutBoard() {
		return blackPiecesOutBoard;
	}

	public void setBlackPiecesOutBoard(Board blackPiecesOutBoard) {
		this.blackPiecesOutBoard = blackPiecesOutBoard;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public void setAnchorPane(AnchorPane anchorPane) {
		this.anchorPane = anchorPane;
	}

	public Label getCurrentMoveColorLabel() {
		return currentMoveColorLabel;
	}

	public void setCurrentMoveColorLabel(Label currentMoveColorLabel) {
		this.currentMoveColorLabel = currentMoveColorLabel;
	}

	public Player getPlayerByColor(Color color) {
		if (color == Color.WHITE) {
			return this.whitePlayer;
		}
		return this.blackPlayer;
	}

}