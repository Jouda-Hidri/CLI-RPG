package com.galaxy.rpg.cli;

import java.io.Serializable;

public class GameText implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String introMessage;
	String winMessage;
	String loseMessage;
	String enemy;
	String target;

	public GameText() {
		introMessage = new String("Start exploring the map");
		winMessage = new String("YOU WON!!!");
		loseMessage = new String("Game over");
		enemy = new String("Enemy");
		target = new String("Target");
	}

	public GameText(int storylineId) {
		if (storylineId == 1) {
			introMessage = new String(
					" Hey Dath Vader! \n We found out where the rebels are hiding! \n You should go to Hoth ... ");
			winMessage = new String("You devastated the Rebel base! \n YOU WON!!!");
			loseMessage = new String("Darth Vader died! \n GAME OVER!");
			enemy = new String("Rebel");
			target = new String("Hoth");
		}
		if (storylineId == 2) {
			introMessage = new String(
					" Hey Luke! \n Your sister needs your help! \n You should go to her! \n Be careful on the way, there is a lot of TIE fighters ... ");
			winMessage = new String("You saved Leia! \n YOU WON!!!");
			loseMessage = new String("Luke died! \n GAME OVER!");
			enemy = new String("TIE fighter");
			target = new String("Leia");
		}
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

	public String getLoseMessage() {
		return loseMessage;
	}

	public void setLoseMessage(String loseMessage) {
		this.loseMessage = loseMessage;
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
