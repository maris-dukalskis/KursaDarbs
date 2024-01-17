package model;

import java.util.ArrayList;
import java.util.List;

import controller.GameController;
import controller.PlayerController;
import service.MainService;

public class GameLogic {

	public static Move getValidMove(Tile fromTile, Tile toTile) {
		if (fromTile.equals(toTile)) {
			return null;
		}
		boolean move = false;
		// pie from pārbaude vai ir null būs kad nospiež uz Tile grafiskajā vidē, lai
		// nav liekas pārbaudes
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
			if (PlayerController.getGame().getGameState() != GameState.CHECK
					&& fromTile.getPiece().getType() == PieceType.KING && toTile.getPiece().getType() == PieceType.ROOK
					&& !fromTile.getPiece().hasMoved() && !toTile.getPiece().hasMoved()) {
				move = isCastlingMoveValid(toTile, fromTile, differenceColumn);
				if (!move) {
					return null;
				}
				return new Move(fromTile, toTile, true);
			}
			return null;
		}

		switch (fromTile.getPiece().getType()) {
		case PAWN:
			move = isPawnMoveValid(fromColumn, fromRow, toColumn, toRow, differenceColumn, fromPieceColor);
			break;
		case BISHOP:
			move = isBishopMoveValid(differenceRow, differenceColumn, fromRow, fromColumn, toRow, toColumn, fromTile,
					toTile);
			break;
		case KNIGHT:
			move = isKnightMoveValid(differenceRow, differenceColumn);
			break;
		case ROOK:
			move = isRookMoveValid(fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow, fromTile,
					toTile);
			break;
		case QUEEN:
			move = isQueenMoveValid(fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow, fromTile,
					toTile);
			break;
		case KING:
			move = isKingMoveValid(differenceRow, differenceColumn);
			break;
		default:
		}
		if (!move) {
			return null;
		}
		return new Move(fromTile, toTile, false);
	}

	public static boolean isPawnMoveValid(byte fromColumn, byte fromRow, byte toColumn, byte toRow,
			byte differenceColumn, Color pieceColor) {
		Board board = PlayerController.getGame().getBoard();
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

	public static boolean isBishopMoveValid(byte differenceRow, byte differenceColumn, byte fromRow, byte fromColumn,
			byte toRow, byte toColumn, Tile fromTile, Tile toTile) {
		Board board = PlayerController.getGame().getBoard();
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

	public static boolean isRookMoveValid(byte fromRow, byte fromColumn, byte toRow, byte toColumn,
			byte differenceColumn, byte differenceRow, Tile fromTile, Tile toTile) {
		Board board = PlayerController.getGame().getBoard();
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

	public static boolean isQueenMoveValid(byte fromRow, byte fromColumn, byte toRow, byte toColumn,
			byte differenceColumn, byte differenceRow, Tile fromTile, Tile toTile) {
		if (isRookMoveValid(fromRow, fromColumn, toRow, toColumn, differenceColumn, differenceRow, fromTile, toTile)
				|| isBishopMoveValid(differenceRow, differenceColumn, fromRow, fromColumn, toRow, toColumn, fromTile,
						toTile)) {
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

	public static boolean isCastlingMoveValid(Tile kingTile, Tile rookTile, byte differenceColumn) {
		Board board = PlayerController.getGame().getBoard();
		int direction = getDirection(kingTile.getColumn(), rookTile.getColumn());
		for (int i = 1; i < differenceColumn; i++) {
			byte checkColumn = (byte) (kingTile.getColumn() + i * direction);
			if (board.getTile(kingTile.getRow(), checkColumn).getPiece() != null) {
				return false;
			}
		}
		return true;
	}

	public static void processClickedTile(Tile lastClicked) {
		Game game = PlayerController.getGame();

		if (game.getWhitePlayer().getTimer() <= 0 || game.getBlackPlayer().getTimer() <= 0) {
			System.out.println("Beigas");
			GameController.generateGraphicalGrid(false);
			return;
		}
		/*
		 * vai tas ir pirmais uzspiestais tile(no kura kustās)
		 */
		if (game.getFromTile() == null) {
			if (lastClicked.getPiece() != null && lastClicked.getPiece().getColor() == game.getMove()) {
				game.setFromTile(lastClicked);
				GameController.displayValidMoves(lastClicked);
				String imageString = lastClicked.getPiece().getColor().name().toLowerCase() + "_"
						+ lastClicked.getPiece().getType().name().toLowerCase() + "_onYellow.jpg";
				GameController.setImage(lastClicked, imageString, lastClicked.getRow(), lastClicked.getColumn(),
						PlayerController.getGame().getBoard().getGrid(), true, MainService.mainImageSize,
						MainService.mainImageSize);
			}
			return;
		}
		Tile fromTile = game.getFromTile();
		List<Move> moveList = generateAllMovesForColor(fromTile.getPiece().getColor(), true);
		Piece fromPiece = fromTile.getPiece();
		Piece toPiece = lastClicked.getPiece();
		boolean canMove = false;
		boolean isCastle = false;
		for (Move move : moveList) {
			if (!move.getFromTile().equals(fromTile)) {
				continue;
			}
			if (move.getToTile().equals(lastClicked)) {
				canMove = true;
				isCastle = move.isCastle();
				move.perform();
				break;
			}
		}

		if (!canMove) {
			game.setFromTile(null);
			GameController.generateGraphicalGrid(true);
			return;
		}
		if (toPiece != null && !isCastle) {
			addToKnockedOutGrid(toPiece);
		}
		game.getWhitePlayer().setDraw(false);
		game.getBlackPlayer().setDraw(false);
		fromPiece.setHasMoved(true);
		game.setMove(fromPiece.getColor().opposite());
		game.getCurrentMoveColorLabel().setText("Current move: " + game.getMove().name());
		game.setFromTile(null);
		GameController.generateGraphicalGrid(false);

		if ((lastClicked.getRow() == 0 || lastClicked.getRow() == 7)
				&& lastClicked.getPiece().getType() == PieceType.PAWN) {
			GameController.displayPawnPromotion(fromPiece, lastClicked, game);
			return;
		}
		processPostMove(game);
	}

	public static void processPostMove(Game game) {
		GameController.generateGraphicalGrid(true);
		GameState state = getGameState(game.getMove());
		switch (state) {
		case CHECK_MATE:
			System.out.println("CHECK MATE");
			GameController.generateGraphicalGrid(false);
			break;
		case CHECK:
			System.out.println("CHECK");
			break;
		case DRAW:
			System.out.println("DRAW");
			break;
		case NORMAL:
			System.out.println("Normal");
		default:
		}
		game.setGameState(state);
		GameController.getInstance().popUps(state);
		GameController.timerForPlayerWhite(game);
		GameController.timerForPlayerBlack(game);
	}

	public static GameState getGameState(Color color) {
		boolean isKingInCheck = isKingInCheck(color);
		List<Move> moveList = generateAllMovesForColor(color, true);
		if (isKingInCheck && moveList.isEmpty()) {
			return GameState.CHECK_MATE;
		}
		if (isKingInCheck) {
			return GameState.CHECK;
		}
		if (moveList.isEmpty()) {
			return GameState.DRAW;
		}
		return GameState.NORMAL;
	}

	public static boolean isKingInCheck(Color color) {
		Tile kingTile = PlayerController.getGame().getBoard().getTileByTypeAndColor(PieceType.KING, color);
		List<Move> moveList = generateAllMovesForColor(kingTile.getPiece().getColor().opposite(), false);
		for (Move move : moveList) {
			if (move.getToTile().getRow() == kingTile.getRow()
					&& move.getToTile().getColumn() == kingTile.getColumn()) {
				return true;
			}
		}
		return false;
	}

	public static List<Move> generateAllMovesForColor(Color color, boolean selfCheck) {
		List<Move> moveList = new ArrayList<>();
		List<Tile> pieceList = new ArrayList<>();
		pieceList.addAll(PlayerController.getGame().getBoard().getAllPieceTilesByColor(color));
		for (Tile pieceTile : pieceList) {
			for (byte i = 0; i <= 7; i++) {
				for (byte j = 0; j <= 7; j++) {
					Tile toTile = PlayerController.getGame().getBoard().getTile(i, j);
					Move move = getValidMove(pieceTile, toTile);
					if (move == null) {
						continue;
					}
					moveList.add(move);
				}
			}
		}
		if (!selfCheck) {
			return moveList;
		}
		List<Move> inCheckMoves = new ArrayList<>();
		for (Move move : moveList) {
			Move clone = move.clone();
			move.perform();
			if (isKingInCheck(color)) {
				inCheckMoves.add(move);
			}
			move.undo(clone);
		}
		for (Move move : inCheckMoves) {
			moveList.remove(move);
		}
		return moveList;
	}

	public static void addToKnockedOutGrid(Piece piece) {
		switch (piece.getColor()) {
		case WHITE:
			GameController.addKnockedOutPiece(piece, PlayerController.getGame().getWhitePiecesOutBoard().getGrid(),
					PlayerController.getGame().getWhitePiecesOutBoard());
			break;
		case BLACK:
			GameController.addKnockedOutPiece(piece, PlayerController.getGame().getBlackPiecesOutBoard().getGrid(),
					PlayerController.getGame().getBlackPiecesOutBoard());
			break;
		default:
		}

	}
}