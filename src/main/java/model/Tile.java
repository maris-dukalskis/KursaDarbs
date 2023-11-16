package model;

public class Tile {

	// mainigie
	private int row;
	private int column;
	private Piece piece;
	private Color color;

	// konstruktori

	public Tile(int inputrow, int inputColumn, Piece inputPiece, Color inputColor) {
		setRow(inputrow);
		setColumn(inputColumn);
		setPiece(inputPiece);
		setColor(color);
	}

	// get
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	public Piece getPiece() {
		return piece;
	}

	// set
	public void setRow(int inputrow) {
		row = inputrow;
	}

	public void setColumn(int inputColumn) {
		column = inputColumn;
	}

	public void setColor(Color inputColor) {
		if (inputColor != null) {
			color = inputColor;
		} else {
			color = Color.BLACK;
		}
	}

	public void setPiece(Piece inputPiece) {
		if (inputPiece != null) {
			piece = inputPiece;
		} else {
			piece = null; // izmainas
		}
	}

}
