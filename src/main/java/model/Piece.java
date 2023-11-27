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

		Color fromPieceColor = fromTile.getPiece().getColor();
		Color toPieceColor = null;

		if (toTile.getPiece() != null) {
			toPieceColor = toTile.getPiece().getColor();
		}

		byte fromRow = fromTile.getRow();
		byte fromColumn = fromTile.getColumn();

		byte toRow = toTile.getRow();
		byte toColumn = toTile.getColumn();

		byte differenceRow = (byte) Math.abs(toRow - fromRow);
		byte differenceColumn = (byte) Math.abs(toColumn - fromColumn);

		if (toPieceColor != null && fromPieceColor.equals(toPieceColor)) {
			return false;
		}
		switch (this.getPieceType()) {
		case PAWN:

			break;
		case BISHOP:
			boolean bishoptMove = bishopCheckMove(differenceRow, differenceColumn);
			return bishoptMove;
		case KNIGHT:
			boolean knightMove = knightCheckMove(differenceRow, differenceColumn);
			return knightMove;
		case ROOK:
			boolean rookMove = rookCheckMove(fromRow, fromColumn, toRow, toColumn);
			return rookMove;
		case QUEEN:

			break;
		case KING:
			boolean kingMove = kingCheckMove(differenceRow, differenceColumn);
			return kingMove;
		default:
			return false;
		}
		return false;
	}

	public boolean bishopCheckMove(byte differenceRow, byte differenceColumn) {
		if (differenceRow == differenceColumn){
			return true;
		}
		return false;
	}
	
	public boolean knightCheckMove(byte differenceRow, byte differenceColumn) {
		if ((differenceRow == 2 && differenceColumn == 1) || (differenceRow == 1 && differenceColumn == 2)) {
			return true;
		}
		return false;
	}

	public boolean rookCheckMove(byte fromRow, byte fromColumn, byte toRow, byte toColumn) {
		if ((fromRow == toRow && fromColumn != toColumn) || (fromRow != toRow && fromColumn == toColumn)) {
			return true;
		}
		return false;
	}

	public boolean kingCheckMove(byte differenceRow, byte differenceColumn) {
		if ((differenceRow + differenceColumn <= 2 && differenceRow + differenceColumn > 0) && (differenceRow != 2)
				&& (differenceColumn != 2)) {
			return true;
		}
		return false;
	}

	public boolean queenCheckMove (byte differenceRow, byte differenceColumn){
		if((0 < differenceRow + differenceColumn && differenceRow + differenceColumn <= 2) && (differenceRow != 2) &&
				(differenceColumn !=2) && (differenceColumn % 2 == differenceRow % 2) ){

			return true;
		}
		return false;
	}
}