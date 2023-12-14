package model;

public class Game {

	private Player player1;
	private Player player2;
	private Board board;
	private Tile fromTile;
	private Color move; // which players move it is - 1 or 2

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

}
