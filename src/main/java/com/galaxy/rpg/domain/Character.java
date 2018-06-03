package com.galaxy.rpg.domain;

import java.io.Serializable;

public class Character implements Serializable {

	private static final long serialVersionUID = 1L;
	int x = 0;
	int y = 0;
	int directionX = 0;
	int directionY = 0;
	int health = 9;
	int experience = 0;

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

	public int getDirectionX() {
		return directionX;
	}

	public void setDirectionX(int directionX) {
		this.directionX = directionX;
	}

	public int getDirectionY() {
		return directionY;
	}

	public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "position (" + x + "," + y + ") - experience : " + experience + " - health : " + health;
	}
}
