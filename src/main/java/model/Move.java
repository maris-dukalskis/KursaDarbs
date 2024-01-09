package model;

import controller.GameController;

public class Move {

	private Tile fromTile;
	private Tile toTile;
	private boolean isCastle;

	public Move(Tile fromTile, Tile toTile, boolean isCastle) {
		super();
		this.fromTile = fromTile;
		this.toTile = toTile;
		this.isCastle = isCastle;
	}

	public Tile getFromTile() {
		return fromTile;
	}

	public void setFromTile(Tile fromTile) {
		this.fromTile = fromTile;
	}

	public Tile getToTile() {
		return toTile;
	}

	public void setToTile(Tile toTile) {
		this.toTile = toTile;
	}

	public boolean isCastle() {
		return isCastle;
	}

	public void setCastle(boolean isCastle) {
		this.isCastle = isCastle;
	}

	public void perform() {
		if (!isCastle) {
			toTile.setPiece(fromTile.getPiece());
			fromTile.setPiece(null);
			return;
		}
		// (if pārbaude) ? (iedod vērtību ja ir true) : (iedod vērtību ja ir false)
		int toRookColumn = (toTile.getColumn() == 7) ? 5 : 3;
		int toKingColumn = (toRookColumn == 5) ? 6 : 2;

		Tile toRookTile = GameController.getGame().getBoard().getTile(fromTile.getRow(), (byte) toRookColumn);
		Tile toKingTile = GameController.getGame().getBoard().getTile(fromTile.getRow(), (byte) toKingColumn);

		toRookTile.setPiece(toTile.getPiece());
		toKingTile.setPiece(fromTile.getPiece());

		fromTile.setPiece(null);
		toTile.setPiece(null);
	}

	public void undo(Move clone) {
		if (isCastle) {
			int currentRookColumn = (clone.getToTile().getColumn() == 7) ? 5 : 3;
			int currentKingColumn = (currentRookColumn == 5) ? 6 : 2;

			Tile currentRookTile = GameController.getGame().getBoard().getTile(clone.getFromTile().getRow(),
					(byte) currentRookColumn);
			Tile currentKingTile = GameController.getGame().getBoard().getTile(clone.getFromTile().getRow(),
					(byte) currentKingColumn);
			currentRookTile.setPiece(null);
			currentKingTile.setPiece(null);
		}
		this.getFromTile().setPiece(clone.getFromTile().getPiece());
		this.getToTile().setPiece(clone.getToTile().getPiece());
	}

	public Move clone() {
		return new Move(this.fromTile.clone(), this.toTile.clone(), this.isCastle);
	}

}
