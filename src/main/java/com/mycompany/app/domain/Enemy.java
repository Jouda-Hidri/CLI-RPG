package com.mycompany.app.domain;

public class Enemy {
	// it is possible here also to create class Position
	int x;
	int y;
	// it is also possible to externalize everything related to motion
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

	// it is also possible to do this on the service layer
	public void moveX() {
		x += 1 * directionX;
	}

	public void moveY() {
		x += 1 * directionY;
	}

	public void changeDirectionX() {
		directionX *= -1;
	}

	public void changeDirectionY() {
		directionY *= -1;
	}

	public int reduceHeath() {
		health -= 1;
		return health;
	}

	@Override
	public String toString() {
		return "position (" + x + "," + y + ") - health : " + health;
	}
}