package model;

import javafx.scene.control.Label;

public class Player {

	private String name;
	private long timer = 3600000;
	private Label timerLabel;
	private boolean draw = false;

	public Player(String inputName) {
		setName(inputName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		} else {
			this.name = "Player";
		}
	}

	public void decreaseTimerTime() {
		if (this.timer >= 100) {
			this.timer -= 100;
		}
	}

	public Label getTimerLabel() {
		return timerLabel;
	}

	public void setTimerLabel(Label timerLabel) {
		this.timerLabel = timerLabel;
	}

	public long getTimer() {
		return timer;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

}
