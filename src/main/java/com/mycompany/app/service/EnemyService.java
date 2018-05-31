package com.mycompany.app.service;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;

public class EnemyService {

	List<Enemy> enemiesList = new ArrayList<Enemy>();

	// it is also possible to make the enemies move the random way or maybe create a
	// small AI in way they move to the enemy
	public Enemy move(Character character) {
		Enemy attackingEnemy = null;
		for (Enemy enemy : enemiesList) {
			// TODO remove the enemy from the list once it dies
			if (enemy.getHealth() <= 0) {
				continue;
			}
			enemy.moveX();
			enemy.changeDirectionX();
			enemy.moveY();
			enemy.changeDirectionY();
			if (enemy.getX() == character.getX() && enemy.getY() == enemy.getY()) {
				attackingEnemy = enemy;
			}
			System.out.println("Enemy: " + enemy);
		}
		return attackingEnemy;
	}

	public boolean attack(Enemy enemy, Character character) {
		character.reduceHealth();
		if (character.getHealth() <= 0) {
			return true; // character died
		}
		return false;
	}

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
}