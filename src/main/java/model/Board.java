package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Tile[][] board = null;

	public Board(int row, int column) {
		board = new Tile[row][column];
		initializeTiles(row, column);
	}

	private void initializeTiles(int row, int column) {
		for (int i = 0; i <= row - 1; i++) {
			for (int j = 0; j <= column - 1; j++) {
				board[i][j] = new Tile((byte) i, (byte) j, null);
			}
		}
	}

	public void placeInitialPieces() {
		for (int i = 0; i <= 7; i++) {
			Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN);
			board[6][i].setPiece(whitePawn);
			Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN);
			board[1][i].setPiece(blackPawn);
		}
		Piece whiteRook1 = new Piece(Color.WHITE, PieceType.ROOK);
		board[7][0].setPiece(whiteRook1);
		Piece whiteRook2 = new Piece(Color.WHITE, PieceType.ROOK);
		board[7][7].setPiece(whiteRook2);
		Piece blackRook1 = new Piece(Color.BLACK, PieceType.ROOK);
		board[0][0].setPiece(blackRook1);
		Piece blackRook2 = new Piece(Color.BLACK, PieceType.ROOK);
		board[0][7].setPiece(blackRook2);

		Piece whiteKnight1 = new Piece(Color.WHITE, PieceType.KNIGHT);
		board[7][1].setPiece(whiteKnight1);
		Piece whiteKnight2 = new Piece(Color.WHITE, PieceType.KNIGHT);
		board[7][6].setPiece(whiteKnight2);
		Piece blackKnight1 = new Piece(Color.BLACK, PieceType.KNIGHT);
		board[0][1].setPiece(blackKnight1);
		Piece blackKnight2 = new Piece(Color.BLACK, PieceType.KNIGHT);
		board[0][6].setPiece(blackKnight2);

		Piece whiteBishop1 = new Piece(Color.WHITE, PieceType.BISHOP);
		board[7][2].setPiece(whiteBishop1);
		Piece whiteBishop2 = new Piece(Color.WHITE, PieceType.BISHOP);
		board[7][5].setPiece(whiteBishop2);
		Piece blackBishop1 = new Piece(Color.BLACK, PieceType.BISHOP);
		board[0][2].setPiece(blackBishop1);
		Piece blackBishop2 = new Piece(Color.BLACK, PieceType.BISHOP);
		board[0][5].setPiece(blackBishop2);

		Piece whiteQueen = new Piece(Color.WHITE, PieceType.QUEEN);
		board[7][3].setPiece(whiteQueen);
		Piece blackQueen = new Piece(Color.BLACK, PieceType.QUEEN);
		board[0][3].setPiece(blackQueen);

		Piece whiteKing = new Piece(Color.WHITE, PieceType.KING);
		board[7][4].setPiece(whiteKing);
		Piece blackKing = new Piece(Color.BLACK, PieceType.KING);
		board[0][4].setPiece(blackKing);
	}

	public static void printBoard(Board board, int row, int column) { // j- verticali(column), i-horizontali(row)
		for (int i = 0; i <= row - 1; i++) {
			for (int j = 0; j <= column - 1; j++) {
				Tile tile = board.getBoard()[i][j];
				Piece piece = tile.getPiece();
				if (piece == null) {
					System.out.print("      ");
				} else {
					System.out.print(piece.getColor().name().charAt(0) + "" + piece.getPieceType().name().charAt(0)
							+ "(" + i + j + ")");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public Tile getTile(byte row, byte column) {
		try {
			return board[row][column];
		} catch (Exception e) {
			return null;
		}
	}

	public Tile getTileByTypeAndColor(PieceType type, Color color) {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = board[i][j];
				Piece piece = tile.getPiece();
				if (piece != null && piece.getPieceType() == type && piece.getColor() == color) {
					return tile;
				}
			}
		}
		return null;
	}

	public List<Tile> getAllTilesByColor(Color color) {
		List<Tile> oneColorTiles = new ArrayList<>();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = board[i][j];
				Piece piece = tile.getPiece();
				if (piece != null && piece.getColor() == color) {
					oneColorTiles.add(tile);
				}
			}
		}
		return oneColorTiles;
	}

	public Tile[][] getBoard() {
		return this.board;
	}

	public Board clone(int row, int column) {
		Board board = new Board(row, column);
		for (int i = 0; i <= row - 1; i++) {
			for (int j = 0; j <= column - 1; j++) {
				board.getBoard()[i][j] = this.board[i][j].clone();
			}
		}
		return board;
	}

}