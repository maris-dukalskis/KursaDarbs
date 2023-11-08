package model;

public class Piece {
	//variables
	private Color color;
	private PieceType type;
	private Tile tile;
	private boolean isInPlay;
	
	//Konstruktors
	public Piece(Color inputColor, PieceType inputType, Tile inputTile, boolean inputIsInPlay) {
	    setColor(inputColor);
	    setType(inputType);
	    setTile(tile);
	    setIsInPlay(isInPlay);
	}	
	//getteri	
	public Color getColor() {
		return color;
	}
	public PieceType getPieceType() {
		return type;
	}	
	public Tile getTile() {
		return tile;
	}	
	public boolean getIsInPlay() {
		return isInPlay;
	}	
	//setteri	
	public void setColor(Color inputColor) {
		if(inputColor!=null && inputColor instanceof Color) {
			color=inputColor;
		}
		else {
			color=Color.BLACK;
		}
	}
	public void setType(PieceType inputType) {
		if(inputType!=null && inputType instanceof PieceType) {
			type=inputType;
		}
		else {
			type=PieceType.PAWN;
		}
	}
	public void setTile(Tile inputTile) {
		if (inputTile != null) {
			tile = inputTile;
		} else {
			tile = null;
		}
	}
	public void setIsInPlay(boolean inputIsInPlay) {
		if(inputIsInPlay==true || inputIsInPlay==false) {
		isInPlay = inputIsInPlay;
	}	else {
		isInPlay=true;
		}
	}
}