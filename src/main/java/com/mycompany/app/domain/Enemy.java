package com.mycompany.app.domain;

public class Enemy {
	// it is possible here also to create class Position
	int x;
	int y;
	// it is also possible to externalize everything related to movement (keep it in
	// another class)
	int directionX = 1;
	int directionY = 0;

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
}
