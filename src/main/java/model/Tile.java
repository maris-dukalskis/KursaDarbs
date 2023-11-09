package model;

public class Tile {
	
	

	
	    
	    //mainigie
	    private int row;
	    private int column;
	    private Piece piece;
	    
	    
	    
	    //get
	    public int getRow() {
	        return row;
	    }
	    public int getColumn() {
	        return column;
	    }
	    public Piece getPiece() {
	        return piece;
	    }
	    
	    //set
	    public void setRow (int inputrow) {
	        if(inputrow<=8 && inputrow>0) {
	            row=inputrow;
	        }
	        else {
	            row=8;
	        }
	    }
	    
	    public void setColumn (int inputColumn) {
	        if(inputColumn<=8 && inputColumn>0) {
	            column=inputColumn;
	        }
	        else {
	            column=8;
	        }
	    }
	    
	    public void setPiece (Piece inputPiece) {
	        if(inputPiece!=null) {
	            piece=inputPiece;
	        }
	        else {
	            piece=new Piece();
	        }
	    }
	    
	    //konstruktori
	    public Tile() {
	        setRow(5);
	        setColumn(7);
	        setPiece(new Piece());    
	    }
	    
	    public Tile(int inputrow, int inputColumn, Piece inputPiece ) {
	        setRow(inputrow);
	        setColumn(inputColumn);
	        setPiece(inputPiece);
	    }
	    
	    //toString
	    //gan neesmu pārliecināta vai šī funkcija ir nepieciešama
	    public String toString() {
	        String result = piece+" novietots "+ row+" rindā un "+column+" kolonnā ";
	        return result;
	    }
	    
	    

	}

