package model;

public class Player {
	
	private String name;
	private Color color;

	public Player(String inputName, Color inputColor) {
		setName(inputName);
		setColor(inputColor);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && name.matches("[A-Z]{1}[a-z]+")) {
			this.name = name;
		} else {
			this.name = "Invalid name";
		}
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
