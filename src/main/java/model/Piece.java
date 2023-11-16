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

	/*
	 * Pārbauda vai kauliņš var kustēties no esošā Tile uz jauno Tile pēc kauliņa
	 * veida
	 */
	public boolean isMoveValid(Color pieceColor, int turnCount, Tile fromTile, Tile toTile) {
		switch (this.getPieceType()) {
		case PAWN:

			break;
		case BISHOP:

			break;
		case KNIGHT:
			boolean knightMove = knightCheckMove(fromTile, toTile);
			return knightMove;
		case ROOK:
			boolean rookMove = rookCheckMove(fromTile, toTile);
			return rookMove;
		case QUEEN:

			break;
		case KING:
			boolean kingMove = kingCheckMove(fromTile, toTile);
			return kingMove;
		default:
			return false;
		}
		return false;
	}

	public boolean knightCheckMove(Tile fromTile, Tile toTile) {
		byte fromRow = fromTile.getRow();
		byte fromColumn = fromTile.getColumn();

		byte toRow = toTile.getRow();
		byte toColumn = toTile.getColumn();

		byte differenceRow = (byte) Math.abs(toRow - fromRow);
		byte differenceColumn = (byte) Math.abs(toColumn - fromColumn);

		if ((differenceRow == 2 && differenceColumn == 1) || (differenceRow == 1 && differenceColumn == 2)) {
			return true;
		}
		return false;
	}

	public boolean rookCheckMove(Tile fromTile, Tile toTile) {
		byte fromRow = fromTile.getRow();
		byte fromColumn = fromTile.getColumn();

		byte toRow = toTile.getRow();
		byte toColumn = toTile.getColumn();

		if ((fromRow == toRow && fromColumn != toColumn) || (fromRow != toRow && fromColumn == toColumn)) {
			return true;
		}
		return false;
	}

	public boolean kingCheckMove(Tile fromTile, Tile toTile) {
		byte fromRow = fromTile.getRow();
		byte fromColumn = fromTile.getColumn();

		byte toRow = toTile.getRow();
		byte toColumn = toTile.getColumn();

		byte differenceRow = (byte) Math.abs(toRow - fromRow);
		byte differenceColumn = (byte) Math.abs(toColumn - fromColumn);

		if ((differenceRow + differenceColumn <= 2 && differenceRow + differenceColumn > 0) && (differenceRow != 2)
				&& (differenceColumn != 2)) {
			return true;
		}
		return false;
	}
}