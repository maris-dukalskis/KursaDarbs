package model;

public class Piece {
	// variables
	private Color color;
	private PieceType type;
	private Tile tile;
	private boolean isInPlay;

	// Konstruktors
	public Piece(Color inputColor, PieceType inputType, Tile inputTile, boolean inputIsInPlay) {
		setColor(inputColor);
		setType(inputType);
		setTile(tile);
		setIsInPlay(isInPlay);

	}

	public Piece() {

	}

	// getteri
	public Color getColor() {
		return color;
	}

	public PieceType getPieceType() {
		return type;
	}

	public Tile getTile() {
		return tile;
	}

	public boolean getIsInPlay() {
		return isInPlay;
	}

	// setteri
	public void setColor(Color inputColor) {
		if (inputColor != null) {
			color = inputColor;
		} else {
			color = Color.BLACK;
		}
	}

	public void setType(PieceType inputType) {
		if (inputType != null) {
			type = inputType;
		} else {
			type = PieceType.PAWN;
		}
	}

	public void setTile(Tile inputTile) {
		if (inputTile != null) {
			tile = inputTile;
		} else {
			tile = null;
		}
	}

	public void setIsInPlay(boolean inputIsInPlay) {
		isInPlay = inputIsInPlay;

	}
}