package com.galaxy.rpg.gamelogic.impl;

import java.util.ArrayList;
import java.util.List;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;
import com.galaxy.rpg.gamelogic.PlayerMotion;

public class EnemiesMotion implements PlayerMotion<Character> {

	List<Enemy> enemiesList = new ArrayList<Enemy>();

	public List<Enemy> getEnemiesList() {
		return enemiesList;
	}

	public void setEnemiesList(List<Enemy> enemiesList) {
		this.enemiesList = enemiesList;
	}

	@Override
	public void create() {
		enemiesList = new ArrayList<Enemy>();

		Enemy enemy1 = new Enemy(0, 2, 1, 0);
		Enemy enemy2 = new Enemy(2, 2, 0, -1);
		Enemy enemy3 = new Enemy(2, 0, 1, 0);

		enemiesList.add(enemy1);
		enemiesList.add(enemy2);
		enemiesList.add(enemy3);
	}

	/**
	 * Each enemy moves one step horizontally or vertically and then changes to the
	 * opposition direction.
	 */
	@Override
	public void move() {
		enemiesList.stream()//
				.filter(e -> e.getHealth() > 0)//
				.forEach(e -> {
					int x2 = e.getX() + e.getDirectionX();
					int y2 = e.getY() + e.getDirectionY();

					// It is also possible to make the enemies move the random way or maybe create a
					// small AI in way they move to the character
					int directionX2 = -e.getDirectionX();
					int directionY2 = -e.getDirectionY();

					e.setX(x2);
					e.setY(y2);

					e.setDirectionX(directionX2);
					e.setDirectionY(directionY2);
				});
	}

	@Override
	public void fight(Character character) {
		System.out.println("You got attacked ...");
		reduceHealth(character);
		System.out.println("You: " + character);
	}

	@Override
	public void reduceHealth(Character character) {
		int health = character.getHealth();
		health--;
		character.setHealth(health);
		System.out.println("You lost 1 point of health!");
	}
}