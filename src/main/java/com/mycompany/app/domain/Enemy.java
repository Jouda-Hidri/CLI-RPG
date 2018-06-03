package com.mycompany.app.domain;

import java.io.Serializable;

public class Enemy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int x;
	int y;
	int directionX = 1;
	int directionY = 0;
	int health = 3;

	public Enemy() {

	}

	public Enemy(int x, int y, int directionX, int directionY) {
		this.x = x;
		this.y = y;
		this.directionX = directionX;
		this.directionY = directionY;
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

	@Override
	public String toString() {
		return "position (" + x + "," + y + ") - health : " + health;
	}
}