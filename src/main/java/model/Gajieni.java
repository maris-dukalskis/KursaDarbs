package model;

import javafx.scene.layout.GridPane;

public class Gajieni {

	/*
	 * Pārbauda vai kauliņš var kustēties no esošā Tile uz jauno Tile pēc kauliņa
	 * veida
	 */
	public static boolean isMoveValid(Color pieceColor, Tile fromTile, Tile toTile) {

		// pie from pārbaude vai ir null būs kad nospiež uz Tile grafiskajā vidē, lai
		// nav liekas pārbaudes
		Color fromPieceColor = fromTile.getPiece().getColor();
		Color toPieceColor = null;

		if (toTile.getPiece() != null) {
			toPieceColor = toTile.getPiece().getColor();
		}

		if (toPieceColor != null && fromPieceColor.equals(toPieceColor)) {
			return false;
		}

		byte fromRow = fromTile.getRow();
		byte fromColumn = fromTile.getColumn();

		byte toRow = toTile.getRow();
		byte toColumn = toTile.getColumn();

		byte differenceRow = (byte) Math.abs(toRow - fromRow);
		byte differenceColumn = (byte) Math.abs(toColumn - fromColumn);

		switch (fromTile.getPiece().getPieceType()) {
		case PAWN:
			boolean pawnMove = pawnCheckMove(fromColumn, fromRow, toColumn, toRow, pieceColor);
			return pawnMove;
		case BISHOP:
			boolean bishopMove = bishopCheckMove(differenceRow, differenceColumn);
			return bishopMove;
		case KNIGHT:
			boolean knightMove = knightCheckMove(differenceRow, differenceColumn);
			return knightMove;
		case ROOK:
			boolean rookMove = rookCheckMove(fromRow, fromColumn, toRow, toColumn);
			return rookMove;
		case QUEEN:
			boolean queenMove = queenCheckMove(differenceRow, differenceColumn, fromRow, fromColumn, toRow, toColumn);
			return queenMove;
		case KING:
			boolean kingMove = kingCheckMove(differenceRow, differenceColumn);
			return kingMove;
		default:
			return false;
		}
	}

	public static boolean pawnCheckMove(byte fromColumn, byte fromRow, byte toColumn, byte toRow, Color pieceColor) {

		// black - 2. rinda(1. index) from-To = negativs
		// white - 7. rinda(6. index) from-To = pozitivs

		// nav modulis, vajag zināt uz kuru pusi tieši iet
		byte differenceColumn = (byte) (fromColumn - toColumn);

		if (pieceColor == Color.BLACK) {
			byte x0 = 1; // prieks column check
			byte x1 = 1; // visur kur vajag 1 un pretejam -1
			byte x2 = -1; // visur kur vajag -1 un pretejam 1
			byte x3 = 2; // visur kur vajag 2 un pretejam -2
			byte x4 = -2; // visur kur vajag -2 un pretejam 2
			byte x5 = 7; // pedeja rinda(pawn promotion)
			Tile diagonalLeftTile = Board.getTile((byte) (fromColumn + 1), (byte) (fromRow - 1));
			Tile diagonalRightTile = Board.getTile((byte) (fromColumn + 1), (byte) (fromRow + 1));
			if (pawnCheckMovePerColor(fromColumn, fromRow, toColumn, toRow, differenceColumn, x0, x1, x2, x3, x4, x5,
					diagonalLeftTile, diagonalRightTile)) {
				return true;
			}
		}
		if (pieceColor == Color.WHITE) {
			byte x0 = 6;
			byte x1 = -1;
			byte x2 = 1;
			byte x3 = -2;
			byte x4 = 2;
			byte x5 = 0;
			Tile diagonalLeftTile = Board.getTile((byte) (fromColumn - 1), (byte) (fromRow - 1));
			Tile diagonalRightTile = Board.getTile((byte) (fromColumn - 1), (byte) (fromRow + 1));
			if (pawnCheckMovePerColor(fromColumn, fromRow, toColumn, toRow, differenceColumn, x0, x1, x2, x3, x4, x5,
					diagonalLeftTile, diagonalRightTile)) {
				return true;
			}
		}
		return false;
	}

	public static boolean pawnCheckMovePerColor(byte fromColumn, byte fromRow, byte toColumn, byte toRow,
			byte differenceColumn, byte x0, byte x1, byte x2, byte x3, byte x4, byte x5, Tile diagonalLeftTile,
			Tile diagonalRightTile) {
		Tile tileInFront = Board.getTile((byte) (fromColumn + x1), fromRow);
		if (fromColumn == x0) { // vai atrodas sākuma pozīcijā
			Tile secondTileInFront = Board.getTile((byte) (fromColumn + x3), fromRow);
			if (differenceColumn == x4 && tileInFront.getPiece() == null && secondTileInFront.getPiece() == null) {
				return true;
			}
			if (differenceColumn == x2) {
				return true;
			}
		}

		if (diagonalLeftTile.getColumn() == toColumn && diagonalLeftTile.getRow() == toRow
				&& diagonalLeftTile.getPiece() != null) { // nosists pa kreisi diagonale
			return true;
		}

		if (diagonalRightTile.getColumn() == toColumn && diagonalRightTile.getRow() == toRow
				&& diagonalRightTile.getPiece() != null) { // nosists pa labi diagonale
			return true;
		}

		if (differenceColumn == x2 && tileInFront.getPiece() == null) { // var pakusteties 1 uz priekšu
			if (toColumn == x5) {
				// te jāliek pawn promotion, vēl nav idejas kā tas notiks
			}
			return true;
		}
		return false;
	}

	public static boolean bishopCheckMove(byte differenceRow, byte differenceColumn) {
		if (differenceRow == differenceColumn) {
			return true;
		}
		return false;
	}

	public static boolean knightCheckMove(byte differenceRow, byte differenceColumn) {
		if ((differenceRow == 2 && differenceColumn == 1) || (differenceRow == 1 && differenceColumn == 2)) {
			return true;
		}
		return false;
	}

	public static boolean rookCheckMove(byte fromRow, byte fromColumn, byte toRow, byte toColumn) {
		if ((fromRow == toRow && fromColumn != toColumn) || (fromRow != toRow && fromColumn == toColumn)) {
			return true;
		}
		return false;
	}

	public static boolean kingCheckMove(byte differenceRow, byte differenceColumn) {
		if ((differenceRow + differenceColumn <= 2 && differenceRow + differenceColumn > 0) && (differenceRow != 2)
				&& (differenceColumn != 2)) {
			return true;
		}
		return false;
	}

	public static boolean queenCheckMove(byte differenceRow, byte differenceColumn, byte fromRow, byte fromColumn,
			byte toRow, byte toColumn) {
		if ((fromRow == toRow && fromColumn != toColumn) || (fromRow != toRow && fromColumn == toColumn)
				|| differenceRow == differenceColumn) {
			return true;
		}
		return false;
	}
	
	public static void checkClickedTile(Tile lastClicked,GridPane mainGrid) {
//		System.out.println(tile.getRow() + " " + tile.getColumn());
//		Game game = MainService.getGame();
//		if (game.getFromTile() == null) {
//			game.setFromTile(tile);
//		} else {
//			game.setToTile(tile);
//		}
//
//		if (game.getFromTile() != null && game.getToTile() != null) {
//			Image fromImage = new Image("/images/Green.jpg");
//			ImageView from = new ImageView(fromImage);
//			from.setFitHeight(93.75);
//			from.setFitWidth(93.75);
//			from.setPreserveRatio(true);
//			
//			Image toImage = new Image("/images/Green.jpg");
//			ImageView to = new ImageView(toImage);
//			to.setFitHeight(93.75);
//			to.setFitWidth(93.75);
//			to.setPreserveRatio(true);
//			
//			mainGrid.add(from, game.getFromTile().getColumn(), game.getFromTile().getRow());
//			mainGrid.add(to, game.getToTile().getColumn(), game.getToTile().getRow());
//		}
	}
}
