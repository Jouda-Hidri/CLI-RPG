package com.mycompany.app.domain;

import java.io.Serializable;

public class Character implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int experience = 0;
	int level = 1;
	int health = 9;
	int life = 3;
	int x = 0;
	int y = 0;

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void gainExperience() {
		experience++;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void reduceHealth() {
		if (health > 0) {
			health--;
		}
		// TODO else die
	}

	@Override
	public String toString() {
		return "position (" + x + "," + y + ") - experience : " + experience + " - health : " + health;
	}
}
