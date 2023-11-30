package model;

public class Tile {

	// mainigie
	private byte row; // horizontal
	private byte column; // vertical
	private Piece piece;
	private Color color;

	// konstruktori

	public Tile(byte inputrow, byte inputColumn, Piece inputPiece, Color inputColor) {
		setRow(inputrow);
		setColumn(inputColumn);
		setPiece(inputPiece);
		setColor(color);
	}

	// get
	public byte getRow() {
		return row;
	}

	public byte getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	public Piece getPiece() {
		return piece;
	}

	// set
	public void setRow(byte inputrow) {
		row = inputrow;
	}

	public void setColumn(byte inputColumn) {
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
