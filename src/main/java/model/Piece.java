package model;

public class Piece {

	private Color color;
	private PieceType type;

	public Piece(Color inputColor, PieceType inputType) {
		setColor(inputColor);
		setType(inputType);
	}

	public Color getColor() {
		return color;
	}

	public PieceType getPieceType() {
		return type;
	}

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

	@Override
	public String toString() {
		return "Piece [color=" + color + ", type=" + type + "]";
	}

}