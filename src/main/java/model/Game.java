package model;

public class Game {

	private Player player1;
	private Player player2;
	private Board board;
	private Board whitePiecesOutBoard;
	private Board blackPiecesOutBoard;
	private Tile fromTile;
	private Color move; // which players move it is - White or Black
	private GameState gameState;

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

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
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

}