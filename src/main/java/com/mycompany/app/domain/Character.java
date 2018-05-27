package com.mycompany.app.domain;

public class Character {

	int experience;
	int level = 1;
	int energy = 10;
	int life = 5;
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

	@Override
	public String toString() {
		return "(" + x + "," + y + ") - " + experience;
	}
}
