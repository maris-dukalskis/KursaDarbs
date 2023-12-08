package model;

public class Game {

	private Player player1;
	private Player player2;
	private Board board;
	private Tile fromTile;
	private Tile toTile;
	private Color move; // which players move it is - 1 or 2

	public Tile getFromTile() {
		return fromTile;
	}

	public void setFromTile(Tile fromTile) {
		this.fromTile = fromTile;
	}

	public Tile getToTile() {
		return toTile;
	}

	public void setToTile(Tile toTile) {
		this.toTile = toTile;
	}

}
