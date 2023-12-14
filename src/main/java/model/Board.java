package model;

public class Board {
	private static Tile[][] board = new Tile[8][8];

	public Board() {
		initializeTiles();
		placeInitialPieces();

	}

	private void initializeTiles() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Color color = Color.BLACK;
				if (((i + j) % 2) == 0) {
					color = Color.WHITE;
				} else {
					color = Color.BLACK;
				}
				board[i][j] = new Tile((byte) i, (byte) j, null, color);
			}
		}
	}

	public void placeInitialPieces() {
		// Pawns
		for (int i = 0; i <= 7; i++) {
			Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN, true);
			board[6][i].setPiece(whitePawn);
			Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN, true);
			board[1][i].setPiece(blackPawn);
		}
		// Rooks
		Piece whiteRook1 = new Piece(Color.WHITE, PieceType.ROOK, true);
		board[7][0].setPiece(whiteRook1);
		Piece whiteRook2 = new Piece(Color.WHITE, PieceType.ROOK, true);
		board[7][7].setPiece(whiteRook2);
		Piece blackRook1 = new Piece(Color.BLACK, PieceType.ROOK, true);
		board[0][0].setPiece(blackRook1);
		Piece blackRook2 = new Piece(Color.BLACK, PieceType.ROOK, true);
		board[0][7].setPiece(blackRook2);
		// Knights
		Piece whiteKnight1 = new Piece(Color.WHITE, PieceType.KNIGHT, true);
		board[7][1].setPiece(whiteKnight1);
		Piece whiteKnight2 = new Piece(Color.WHITE, PieceType.KNIGHT, true);
		board[7][6].setPiece(whiteKnight2);
		Piece blackKnight1 = new Piece(Color.BLACK, PieceType.KNIGHT, true);
		board[0][1].setPiece(blackKnight1);
		Piece blackKnight2 = new Piece(Color.BLACK, PieceType.KNIGHT, true);
		board[0][6].setPiece(blackKnight2);
		// Bishops
		Piece whiteBishop1 = new Piece(Color.WHITE, PieceType.BISHOP, true);
		board[7][2].setPiece(whiteBishop1);
		Piece whiteBishop2 = new Piece(Color.WHITE, PieceType.BISHOP, true);
		board[7][5].setPiece(whiteBishop2);
		Piece blackBishop1 = new Piece(Color.BLACK, PieceType.BISHOP, true);
		board[0][2].setPiece(blackBishop1);
		Piece blackBishop2 = new Piece(Color.BLACK, PieceType.BISHOP, true);
		board[0][5].setPiece(blackBishop2);
		// Queens
		Piece whiteQueen = new Piece(Color.WHITE, PieceType.QUEEN, true);
		board[7][3].setPiece(whiteQueen);
		Piece blackQueen = new Piece(Color.BLACK, PieceType.QUEEN, true);
		board[0][3].setPiece(blackQueen);
		// Kings
		Piece whiteKing = new Piece(Color.WHITE, PieceType.KING, true);
		board[7][4].setPiece(whiteKing);
		Piece blackKing = new Piece(Color.BLACK, PieceType.KING, true);
		board[0][4].setPiece(blackKing);
	}

	public void printBoard() { // j- verticali(column), i-horizontali(row)
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = board[i][j];
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

	public static Tile getTile(byte row, byte column) {
		return board[row][column];
	}

}