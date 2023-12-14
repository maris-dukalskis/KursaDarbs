package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import service.MainService;

public class Gajieni {

	/*
	 * Pārbauda vai kauliņš var kustēties no esošā Tile uz jauno Tile pēc kauliņa
	 * veida
	 */
	public static boolean isMoveValid(Tile fromTile, Tile toTile) {

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
			boolean pawnMove = pawnCheckMove(fromColumn, fromRow, toColumn, toRow, differenceColumn, fromPieceColor);
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

	public static boolean pawnCheckMove(byte fromColumn, byte fromRow, byte toColumn, byte toRow, byte differenceColumn,
			Color pieceColor) {

		// black - 2. rinda(1. index) from-To = negativs
		// white - 7. rinda(6. index) from-To = pozitivs

		// nav modulis, vajag zināt uz kuru pusi tieši iet
		byte differenceRow = (byte) (fromRow - toRow);

		if (pieceColor == Color.BLACK) {
			byte x0 = 1; // prieks row check
			byte x1 = 1; // visur kur vajag 1 un pretejam -1
			byte x2 = -1; // visur kur vajag -1 un pretejam 1
			byte x3 = 2; // visur kur vajag 2 un pretejam -2
			byte x4 = -2; // visur kur vajag -2 un pretejam 2
			byte x5 = 7; // pedeja rinda(pawn promotion)
			Tile diagonalLeftTile = Board.getTile((byte) (fromRow + 1), (byte) (fromColumn - 1));
			Tile diagonalRightTile = Board.getTile((byte) (fromRow + 1), (byte) (fromColumn + 1));
			if (pawnCheckMovePerColor(fromColumn, fromRow, toColumn, toRow, differenceColumn, differenceRow, x0, x1, x2,
					x3, x4, x5, diagonalLeftTile, diagonalRightTile)) {
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
			Tile diagonalLeftTile = Board.getTile((byte) (fromRow - 1), (byte) (fromColumn - 1));
			Tile diagonalRightTile = Board.getTile((byte) (fromRow - 1), (byte) (fromColumn + 1));
			if (pawnCheckMovePerColor(fromColumn, fromRow, toColumn, toRow, differenceColumn, differenceRow, x0, x1, x2,
					x3, x4, x5, diagonalLeftTile, diagonalRightTile)) {
				return true;
			}
		}
		return false;
	}

	public static boolean pawnCheckMovePerColor(byte fromColumn, byte fromRow, byte toColumn, byte toRow,
			byte differenceColumn, byte differenceRow, byte x0, byte x1, byte x2, byte x3, byte x4, byte x5,
			Tile diagonalLeftTile, Tile diagonalRightTile) {
		Tile tileInFront = Board.getTile((byte) (fromRow + x1), fromColumn);
		if (fromRow == x0) { // vai atrodas sākuma pozīcijā
			Tile secondTileInFront = Board.getTile((byte) (fromRow + x3), fromColumn);
			/*
			 * kad iet 2 soļus uz priekšu, pārbauda vai priekšā gan pirmajā gan otrajā tile
			 * nekā nav(pawn nevar pārlekt pāri citiem kauliņiem)
			 */
			if (differenceRow == x4 && differenceColumn == 0 && tileInFront.getPiece() == null
					&& secondTileInFront.getPiece() == null) {
				return true;
			}
		}
		boolean isAllowedToMove = false;
		if (diagonalLeftTile.getColumn() == toColumn && diagonalLeftTile.getRow() == toRow
				&& diagonalLeftTile.getPiece() != null) { // nosists pa kreisi diagonale
			isAllowedToMove = true;
		}

		if (diagonalRightTile.getColumn() == toColumn && diagonalRightTile.getRow() == toRow
				&& diagonalRightTile.getPiece() != null) { // nosists pa labi diagonale
			isAllowedToMove = true;
		}
		/*
		 * var pakusteties 1 uz priekšu
		 */
		if (differenceRow == x2 && differenceColumn == 0 && tileInFront.getPiece() == null) {
			isAllowedToMove = true;
		}
		if (toRow == x5 && isAllowedToMove) {
			// te jāliek pawn promotion, vēl nav idejas kā tas notiks
		}
		return isAllowedToMove;
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

	public static void checkClickedTile(Tile lastClicked, GridPane mainGrid) {
		Game game = MainService.getGame();
		/*
		 * vai tas ir pirmais uzspiestais tile(no kura kustās)
		 */
		if (game.getFromTile() == null) {
			if (lastClicked.getPiece() != null) {
				game.setFromTile(lastClicked);
				displayValidMoves(lastClicked, mainGrid);
			}
			return;
		}

//		/*
//		 * vai vispār ir uzspiests uz kāda kauliņa
//		 */
//		if (lastClicked.getPiece() == null) {
//			System.out.println("Nav kauliņš");
//			return;
//		}

//		Piece piece = lastClicked.getPiece();
		/*
		 * vai ir uzspiests uz pareizās krāsas kauliņa
		 */
//		if (piece.getColor() != game.getMove()) {
//			return;
//		}

		Tile fromTile = game.getFromTile();
		Piece fromPiece = fromTile.getPiece();

		if (!isMoveValid(fromTile, lastClicked)) {
			game.setFromTile(null);
			System.out.println("Nav valid move");
			return;
		}
		lastClicked.setPiece(fromPiece);
		fromTile.setPiece(null);
		setGraphicTile(lastClicked, (byte) lastClicked.getRow(), (byte) lastClicked.getColumn(), mainGrid);
		setGraphicTile(fromTile, (byte) fromTile.getRow(), (byte) fromTile.getColumn(), mainGrid);

		game.setFromTile(null);
		MainService.getBoard().printBoard();
		System.out.println("");
		resetValidMoves(mainGrid);

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

	public static void displayValidMoves(Tile clickedTile, GridPane mainGrid) {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = Board.getTile((byte) i, (byte) j);
				if (!isMoveValid(clickedTile, tile)) {
					continue;
				}
				String imageString = "";
				Piece piece = tile.getPiece();
				if (piece == null) {
					imageString = "Green.jpg";
				} else {
					if (piece.getColor() == clickedTile.getPiece().getColor()) {
						continue;
					}
					imageString += piece.getColor().name().toLowerCase() + "_";
					imageString += piece.getPieceType().name().toLowerCase() + "_onRed.jpg";
				}
				Image image = new Image("/images/" + imageString);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(93.75); // gridpane platums un garums dalīts ar 8
				imageView.setFitWidth(93.75);
				imageView.setPreserveRatio(true);

				imageView.setOnMouseClicked(event -> {
					Gajieni.checkClickedTile(tile, mainGrid);
				});

				mainGrid.add(imageView, j, i);
			}
		}
	}

	public static void resetValidMoves(GridPane mainGrid) {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = Board.getTile((byte) i, (byte) j);

				String imageString = "";
				if (tile.getPiece() != null) {
					String pieceColor = tile.getPiece().getColor().name().toLowerCase();
					imageString += pieceColor + "_";
					String pieceType = tile.getPiece().getPieceType().name().toLowerCase();
					imageString += pieceType + "_on";
				}
				String tileColor = "";
				if (((i + j) % 2) == 0) {
					tileColor = "White.jpg";
				} else {
					tileColor = "Black.jpg";
				}
				imageString += tileColor;

				Image image = new Image("/images/" + imageString);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(93.75); // gridpane platums un garums dalīts ar 8
				imageView.setFitWidth(93.75);
				imageView.setPreserveRatio(true);

				imageView.setOnMouseClicked(event -> {
					Gajieni.checkClickedTile(tile, mainGrid);
				});

				mainGrid.add(imageView, j, i);
			}
		}
	}

	public static void setGraphicTile(Tile tile, byte i, byte j, GridPane mainGrid) {
		String imageString = "";

		if (tile.getPiece() != null) {
			String pieceColor = tile.getPiece().getColor().name().toLowerCase();
			imageString += pieceColor + "_";
			String pieceType = tile.getPiece().getPieceType().name().toLowerCase();
			imageString += pieceType + "_on";
		}
		String tileColor = "";
		if (((i + j) % 2) == 0) {
			tileColor = "White.jpg";
		} else {
			tileColor = "Black.jpg";
		}
		imageString += tileColor;

		Image image = new Image("/images/" + imageString);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(93.75); // gridpane platums un garums dalīts ar 8
		imageView.setFitWidth(93.75);
		imageView.setPreserveRatio(true);

		imageView.setOnMouseClicked(event -> {
			Gajieni.checkClickedTile(tile, mainGrid);
		});

		mainGrid.add(imageView, j, i);
	}

}
