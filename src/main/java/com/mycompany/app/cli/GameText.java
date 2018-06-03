package com.mycompany.app.cli;

public class GameText {

	String introMessage;
	String winMessage;
	String looseMessage;
	String enemy;
	String target;

	public GameText() {
		introMessage = new String("Start exploring the map");
		winMessage = new String("YOU WON!!!");
		looseMessage = new String("Game over");
		enemy = new String("Enemy");
		target = new String("Target");
	}

	public GameText(String introMessage, String winMessage, String looseMessage, String enemy, String target) {
		this.introMessage = introMessage;
		this.winMessage = winMessage;
		this.looseMessage = looseMessage;
		this.enemy = enemy;
		this.target = target;

	}

	public String getIntroMessage() {
		return introMessage;
	}

	public void setIntroMessage(String introMessage) {
		this.introMessage = introMessage;
	}

	public String getWinMessage() {
		return winMessage;
	}

	public void setWinMessage(String winMessage) {
		this.winMessage = winMessage;
	}

	public String getLooseMessage() {
		return looseMessage;
	}

	public void setLooseMessage(String looseMessage) {
		this.looseMessage = looseMessage;
	}

	public String getEnemy() {
		return enemy;
	}

	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
