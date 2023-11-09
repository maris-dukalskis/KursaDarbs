package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
		//bitboards
	//private List<Piece> pieceList = new ArrayList<>();
		private Piece[][] chessBoard;

	public Board(int xDimension, int yDimension){
		chessBoard = new Piece[xDimension][yDimension];
	}
	public boolean isEmptyPosition(int xPosition, int yPosition){
		if (isInBounds(xPosition, yPosition)){
			if (chessBoard[xPosition][yPosition] == null)
				return true;
		}
		return false;
	}
	public boolean isInBounds(int xPosition, int yPosition){
		if (xPosition < getXDimension() && xPosition >= 0 &&
				yPosition < getYDimension() && yPosition >= 0)
			return true;
		return false;
	}
	public Piece pieceAt(int xPosition, int yPosition){
		if (isInBounds(xPosition, yPosition)){
			return chessBoard[xPosition][yPosition];
		}
		return null;
	}

	/*public void displayBoard(){

	Vajag pieces pabeigt

	}
	*/


	// Getters

	public int getXDimension(){
		return chessBoard[0].length;
	}

	public int getYDimension(){
		return chessBoard.length;
	}

	public Piece[][] getChessBoard(){
		return chessBoard;
	}

	//funkcija kas nonem kaulinu

	// funkcija kas noliek kaulinu

}
