package model;

public class Board {
	private Tile[][] board = new Tile[8][8];

	public Board() {
		initializeTiles();
		placeInitialPieces();

	}

	private void initializeTiles() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Color color = Color.BLACK;
				if (i + j % 2 == 0) {
					color = Color.WHITE;
				} else {
					color = Color.BLACK;
				}
				board[i][j] = new Tile(i, j, null, color);
			}
		}
	}

	public void placeInitialPieces() {
		// Pawns
		for (int i = 0; i <= 7; i++) {
			Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN, true);
			board[i][6].setPiece(whitePawn);
			Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN, true);
			board[i][1].setPiece(blackPawn);
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
		board[1][0].setPiece(whiteKnight1);
		Piece whiteKnight2 = new Piece(Color.WHITE, PieceType.KNIGHT, true);
		board[6][0].setPiece(whiteKnight2);
		Piece blackKnight1 = new Piece(Color.BLACK, PieceType.KNIGHT, true);
		board[1][7].setPiece(blackKnight1);
		Piece blackKnight2 = new Piece(Color.BLACK, PieceType.KNIGHT, true);
		board[6][7].setPiece(blackKnight2);
		// Bishops
		Piece whiteBishop1 = new Piece(Color.WHITE, PieceType.BISHOP, true);
		board[2][7].setPiece(whiteBishop1);
		Piece whiteBishop2 = new Piece(Color.WHITE, PieceType.BISHOP, true);
		board[5][7].setPiece(whiteBishop2);
		Piece blackBishop1 = new Piece(Color.BLACK, PieceType.BISHOP, true);
		board[2][0].setPiece(blackBishop1);
		Piece blackBishop2 = new Piece(Color.BLACK, PieceType.BISHOP, true);
		board[5][0].setPiece(blackBishop2);
		// Queens
		Piece whiteQueen = new Piece(Color.WHITE, PieceType.QUEEN, true);
		board[3][7].setPiece(whiteQueen);
		Piece blackQueen = new Piece(Color.BLACK, PieceType.QUEEN, true);
		board[3][0].setPiece(blackQueen);
		// Kings
		Piece whiteKing = new Piece(Color.WHITE, PieceType.KING, true);
		board[4][7].setPiece(whiteKing);
		Piece blackKing = new Piece(Color.BLACK, PieceType.KING, true);
		board[4][0].setPiece(blackKing);
	}

	public void printBoard() {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				Tile tile = board[j][i];
				Piece piece = tile.getPiece();
				if (piece == null) {
					System.out.print(" ");
				} else {
					System.out.print(piece.getPieceType().name().charAt(0));
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	// removePiece

	// addPiece

	// emptyBoard

	// pajautat koordinatas un izvadit tas

	// pajautat koordinatas lai novietotu kaulinu

}
