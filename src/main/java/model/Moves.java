package model;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import javafx.scene.layout.GridPane;
import service.MainService;

public class Moves {

	/*
	 * Pārbauda vai kauliņš var kustēties no esošā Tile uz jauno Tile pēc kauliņa
	 * veida
	 */
	public static boolean isMoveValid(Board board, Tile fromTile, Tile toTile, boolean processSelfCheck) {

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

		boolean move = false;

		switch (fromTile.getPiece().getPieceType()) {
		case PAWN:
			move = isPawnMoveValid(board, fromColumn, fromRow, toColumn, toRow, differenceColumn, fromPieceColor);
			break;
		case BISHOP:
			move = isBishopMoveValid(board, differenceRow, differenceColumn, fromRow, fromColumn, toRow, toColumn,
					fromTile, toTile);
			break;
		case KNIGHT:
			move = isKnightMoveValid(differenceRow, differenceColumn);
			break;
		case ROOK:
			move = isRookMoveValid(board, fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow,
					fromTile, toTile);
			break;
		case QUEEN:
			move = isQueenMoveValid(board, fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow,
					fromTile, toTile);
			break;
		case KING:
			move = isKingMoveValid(differenceRow, differenceColumn);
			break;
		default:
		}
		if (!processSelfCheck) {
			return move;
		}
		if (!move) {
			return false;
		}

		Board boardCopy = (Board) MainService.getBoard().clone();

		Tile fromTileCopy = boardCopy.getTile(fromTile.getRow(), fromTile.getColumn());
		Tile toTileCopy = boardCopy.getTile(toTile.getRow(), toTile.getColumn());

		fromTileCopy.setPiece(null);
		toTileCopy.setPiece(fromTile.getPiece());

		Tile selfKing = boardCopy.getTileByTypeAndColor(PieceType.KING, fromTile.getPiece().getColor());
		Color opponentColor = fromTile.getPiece().getColor().opposite();

		GameState gameState = getSelfCheckState(boardCopy, selfKing, opponentColor);

		switch (gameState) {
		case CHECK:
			move = false;
			break;
		case CHECK_MATE:
			move = false;
			break;
		default:
		}
		return move;
	}

	public static boolean isPawnMoveValid(Board board, byte fromColumn, byte fromRow, byte toColumn, byte toRow,
			byte differenceColumn, Color pieceColor) {

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
			Tile diagonalLeftTile = board.getTile((byte) (fromRow + 1), (byte) (fromColumn - 1));
			Tile diagonalRightTile = board.getTile((byte) (fromRow + 1), (byte) (fromColumn + 1));
			if (isPawnMoveValidPerColor(board, fromColumn, fromRow, toColumn, toRow, differenceColumn, differenceRow,
					x0, x1, x2, x3, x4, x5, diagonalLeftTile, diagonalRightTile)) {
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
			Tile diagonalLeftTile = board.getTile((byte) (fromRow - 1), (byte) (fromColumn - 1));
			Tile diagonalRightTile = board.getTile((byte) (fromRow - 1), (byte) (fromColumn + 1));
			if (isPawnMoveValidPerColor(board, fromColumn, fromRow, toColumn, toRow, differenceColumn, differenceRow,
					x0, x1, x2, x3, x4, x5, diagonalLeftTile, diagonalRightTile)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPawnMoveValidPerColor(Board board, byte fromColumn, byte fromRow, byte toColumn, byte toRow,
			byte differenceColumn, byte differenceRow, byte x0, byte x1, byte x2, byte x3, byte x4, byte x5,
			Tile diagonalLeftTile, Tile diagonalRightTile) {
		Tile tileInFront = board.getTile((byte) (fromRow + x1), fromColumn);
		if (fromRow == x0) { // vai atrodas sākuma pozīcijā
			Tile secondTileInFront = board.getTile((byte) (fromRow + x3), fromColumn);
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
		if (diagonalLeftTile != null && diagonalLeftTile.getColumn() == toColumn && diagonalLeftTile.getRow() == toRow
				&& diagonalLeftTile.getPiece() != null) { // nosists pa kreisi diagonale
			isAllowedToMove = true;
		}

		if (diagonalRightTile != null && diagonalRightTile.getColumn() == toColumn
				&& diagonalRightTile.getRow() == toRow && diagonalRightTile.getPiece() != null) { // nosists pa labi
																									// diagonale
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

	public static boolean isBishopMoveValid(Board board, byte differenceRow, byte differenceColumn, byte fromRow,
			byte fromColumn, byte toRow, byte toColumn, Tile fromTile, Tile toTile) {

		if (differenceRow != differenceColumn) {
			return false; // Nav diagonāla kustība
		}
		// Nosaka pārvietošanās virzienu
		int columnDirection = getDirection(fromColumn, toColumn);
		int rowDirection = getDirection(fromRow, toRow);

		// Pārbauda vai ceļa nav figūru
		for (byte i = 1; i < differenceRow; i++) {
			byte checkColumn = (byte) (fromColumn + i * columnDirection);
			byte checkRow = (byte) (fromRow + i * rowDirection);

			// Ja ceļā ir figūra, tad:
			if (board.getTile(checkRow, checkColumn).getPiece() != null) {
				return false;
			}
		}
		return true;
	}

	public static boolean isKnightMoveValid(byte differenceRow, byte differenceColumn) {
		if ((differenceRow == 2 && differenceColumn == 1) || (differenceRow == 1 && differenceColumn == 2)) {
			return true;
		}
		return false;
	}

	public static boolean isRookMoveValid(Board board, byte fromRow, byte fromColumn, byte toRow, byte toColumn,
			byte differenceColumn, byte differenceRow, Tile fromTile, Tile toTile) {
		if ((fromRow == toRow && fromColumn != toColumn) || (fromRow != toRow && fromColumn == toColumn)) {

			int columnDirection = getDirection(fromColumn, toColumn);
			int rowDirection = getDirection(fromRow, toRow);

			if (differenceRow != 0) {// parbauda rindai
				for (byte i = 1; i < differenceRow; i++) {
					byte checkRow = (byte) (fromRow + i * rowDirection);

					// ja priekšā ir figūra
					if (board.getTile(checkRow, fromColumn).getPiece() != null) {
						return false;
					}
				}
			} else {// parbauda kolonai
				for (byte i = 1; i < differenceColumn; i++) {
					byte checkColumn = (byte) (fromColumn + i * columnDirection);
					// vai lauciņš priekšā nav tukšs
					if (board.getTile(fromRow, checkColumn).getPiece() != null) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	private static int getDirection(byte from, byte to) {
		if (to > from) {
			return 1;
		} else if (to < from)
			return -1;
		return 0; // nekustās
	}

	public static boolean isQueenMoveValid(Board board, byte fromRow, byte fromColumn, byte toRow, byte toColumn,
			byte differenceColumn, byte differenceRow, Tile fromTile, Tile toTile) {
		if (isRookMoveValid(board, fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow, fromTile,
				toTile)
				|| isBishopMoveValid(board, differenceRow, differenceColumn, fromRow, fromColumn, toRow, toColumn,
						fromTile, toTile)) {
			return true;
		}
		return false;
	}

	public static boolean isKingMoveValid(byte differenceRow, byte differenceColumn) {
		if ((differenceRow + differenceColumn <= 2 && differenceRow + differenceColumn > 0) && (differenceRow != 2)
				&& (differenceColumn != 2)) {
			return true;
		}
		return false;
	}

	public static void processClickedTile(Tile lastClicked, GridPane mainGrid) {
		Game game = MainService.getGame();
		Board board = MainService.getBoard();
		/*
		 * vai tas ir pirmais uzspiestais tile(no kura kustās)
		 */
		if (game.getFromTile() == null) {
			if (lastClicked.getPiece() != null && lastClicked.getPiece().getColor() == game.getMove()) {
				game.setFromTile(lastClicked);
				displayValidMoves(lastClicked, mainGrid);
			}
			return;
		}

		Tile fromTile = game.getFromTile();
		Piece fromPiece = fromTile.getPiece();

		// ja uzspiež uz tile kur nevar pakustēties tad vienkārši atliek visu kā bija
		if (!isMoveValid(board, fromTile, lastClicked, true)) {
			game.setFromTile(null);
			GameController.generateGraphicalGrid(mainGrid);
			return;
		}
		lastClicked.setPiece(fromPiece);
		fromTile.setPiece(null);

		Color enemyColor = lastClicked.getPiece().getColor().opposite();
		Tile enemyKing = board.getTileByTypeAndColor(PieceType.KING, enemyColor);

		GameState state = getGameState(board, enemyKing, lastClicked.getPiece().getColor());

		switch (state) {
		case CHECK_MATE:
			System.out.println("Beigas");
			break;
		case CHECK:
			System.out.println("CHECK");
			break;
		default:
		}
		GameController.generateAndSetImage(lastClicked, (byte) lastClicked.getRow(), (byte) lastClicked.getColumn(),
				mainGrid);
		GameController.generateAndSetImage(fromTile, (byte) fromTile.getRow(), (byte) fromTile.getColumn(), mainGrid);

		game.setFromTile(null);
		game.setMove(fromPiece.getColor().opposite());

		// noņem visas liekās krāsas
		GameController.generateGraphicalGrid(mainGrid);
	}

	public static GameState getGameState(Board board, Tile opponentKing, Color selfColor) {
		List<Tile> opponentKingValidMoves = getAllValidMovesForTile(board, opponentKing);
		// ja king visur apkārt ir citi kauliņi
		if (opponentKingValidMoves.isEmpty()) {
			return GameState.NORMAL;
		}
		int opponentKingMoveCount = opponentKingValidMoves.size();
		List<Tile> selfColorTiles = board.getAllTilesByColor(selfColor);
		// iet cauri visiem saviem kauliņiem un skatās vai moves pārklājas ar pretinieku king moves
		for (Tile selfColorTile : selfColorTiles) {
			for (Tile selfColorTileMove : getAllValidMovesForTile(board, selfColorTile)) {
				if (opponentKingValidMoves.contains(selfColorTileMove)) {
					opponentKingValidMoves.remove(selfColorTileMove);
				}
			}
		}
		if (opponentKingValidMoves.isEmpty()) {
			return GameState.CHECK_MATE;
		} else if (opponentKingValidMoves.size() < opponentKingMoveCount) { // ja samazinājās skaits tad kāds kauliņš ieliek check
			return GameState.CHECK;
		}
		return GameState.NORMAL;
	}

	public static GameState getSelfCheckState(Board board, Tile selfKing, Color opponentColor) {
		List<Tile> opponentColorTiles = board.getAllTilesByColor(opponentColor);
		for (Tile opponentColorTile : opponentColorTiles) {
			for (Tile opponentColorTileMove : getAllValidMovesForTile(board, opponentColorTile)) {
				// ja kāds pretinieka move uziet uz sava king lauciņa tad sanāks self check
				if (selfKing.getRow() == opponentColorTileMove.getRow()
						&& selfKing.getColumn() == opponentColorTileMove.getColumn()) {
					return GameState.CHECK;
				}
			}
		}
		return GameState.NORMAL;
	}

	public static List<Tile> getAllValidMovesForTile(Board board, Tile tile) {
		List<Tile> validMoveList = new ArrayList<>();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile toTile = board.getTile((byte) i, (byte) j);
				if (!isMoveValid(board, tile, toTile, false)) {
					continue;
				}
				validMoveList.add(toTile);
			}
		}
		return validMoveList;
	}

	public static void displayValidMoves(Tile clickedTile, GridPane mainGrid) {
		Board board = MainService.getBoard();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = board.getTile((byte) i, (byte) j);
				if (!isMoveValid(board, clickedTile, tile, true)) {
					continue;
				}
				String imageString = "";
				Piece piece = tile.getPiece();
				if (piece == null) {

					if (((i + j) % 2) == 0) {
						imageString = "Green.jpg";
					} else {
						imageString = "Dark_Green.jpg";
					}
				} else {
					if (piece.getColor() == clickedTile.getPiece().getColor()) {
						continue;
					}
					imageString += piece.getColor().name().toLowerCase() + "_";
					imageString += piece.getPieceType().name().toLowerCase() + "_onRed.jpg";
				}
				GameController.setImage(tile, imageString, i, j, mainGrid);
			}
		}
	}

}
