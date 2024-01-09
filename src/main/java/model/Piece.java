package model;

public class Piece {

	private Color color;
	private PieceType type;
	private boolean hasMoved;

	public Piece(Color inputColor, PieceType inputType) {
		setColor(inputColor);
		setType(inputType);
		setHasMoved(false);
	}

	public Color getColor() {
		return color;
	}

	public PieceType getType() {
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

	public boolean hasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	@Override
	public String toString() {
		return "Piece [color=" + color + ", type=" + type + "]";
	}

}