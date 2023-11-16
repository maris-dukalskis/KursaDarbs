package model;

public class Piece {
	
	// variables
	private Color color;
	private PieceType type;
	private boolean isInPlay;

	// Konstruktors
	public Piece(Color inputColor, PieceType inputType, boolean inputIsInPlay) {
		setColor(inputColor);
		setType(inputType);
		setIsInPlay(isInPlay);
	}

	// getteri
	public Color getColor() {
		return color;
	}

	public PieceType getPieceType() {
		return type;
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

	public void setIsInPlay(boolean inputIsInPlay) {
		isInPlay = inputIsInPlay;

	}
}