package com.mycompany.app.gamelogic.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;
import com.mycompany.app.gamelogic.PlayerMotion;

public class EnemiesMotion implements PlayerMotion<Character> {

	List<Enemy> enemiesList = new ArrayList<Enemy>();

	public List<Enemy> getEnemiesList() {
		return enemiesList;
	}

	@Override
	public void create() {
		if (enemiesList != null) {
			enemiesList = new ArrayList<Enemy>();
		}
		// TODO load the enemy positions from a data source
		enemiesList.add(new Enemy(0, 2, 1, 0));
		System.out.println("Enemy1 position: (0,2)");
		enemiesList.add(new Enemy(2, 2, 0, -1));
		System.out.println("Enemy2 position: (2,2)");
		enemiesList.add(new Enemy(2, 0, 1, 0));
		System.out.println("Enemy3 position: (2,0)");
	}

	/**
	 * Each enemy moves one step horizontally or vertically and then changes to the
	 * opposition direction.
	 */
	@Override
	public void move() {
		for (Enemy enemy : enemiesList) {
			if (enemy.getHealth() <= 0) {
				continue;
			}
			int x2 = enemy.getX() + enemy.getDirectionX();
			int y2 = enemy.getY() + enemy.getDirectionY();

			// It is also possible to make the enemies move the random way or maybe create a
			// small AI in way they move to the character
			int directionX2 = -enemy.getDirectionX();
			int directionY2 = -enemy.getDirectionY();

			enemy.setX(x2);
			enemy.setY(y2);

			enemy.setDirectionX(directionX2);
			enemy.setDirectionY(directionY2);
			System.out.println("Enemy: " + enemy);
		}
	}

	@Override
	public void fight(Character character) {
		reduceHealth(character);
	}

	@Override
	public void reduceHealth(Character character) {
		int health = character.getHealth();
		health--;
		character.setHealth(health);
	}
}