package model;

public enum Color {

	BLACK, WHITE;

	public Color opposite() {
		switch (this) {
		case BLACK:
			return WHITE;
		case WHITE:
			return BLACK;
		}
		return null;
	}
}
