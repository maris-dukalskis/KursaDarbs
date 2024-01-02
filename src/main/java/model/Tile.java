package model;

public class Tile {

	private byte row; // horizontal
	private byte column; // vertical
	private Piece piece;

	public Tile(byte inputRow, byte inputColumn, Piece inputPiece) {
		setRow(inputRow);
		setColumn(inputColumn);
		setPiece(inputPiece);
	}

	public byte getRow() {
		return row;
	}

	public byte getColumn() {
		return column;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setRow(byte inputRow) {
		row = inputRow;
	}

	public void setColumn(byte inputColumn) {
		column = inputColumn;
	}

	public void setPiece(Piece inputPiece) {
		if (inputPiece != null) {
			piece = inputPiece;
		} else {
			piece = null;
		}
	}

	public Tile getCopy() {
		return new Tile(this.row, this.column, this.piece);
	}

	@Override
	public String toString() {
		return "Tile [row=" + row + ", column=" + column + ", piece=" + piece + "]";
	}

	public Tile clone() {
		return new Tile(this.row, this.column, this.piece);
	}

}
