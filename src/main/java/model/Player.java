package model;

import javafx.scene.control.Label;

public class Player {

	private String name;
	private byte moveCount = 0;
	private long timer = 3600000;
	private Label timerLabel;

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

	public void increaseMoveCount() {
		this.moveCount += 1;
	}

	public void decreaseTimerTime() {
		this.timer -= 1000;
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

}
