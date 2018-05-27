package com.mycompany.app.service;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.domain.Enemy;

public class EnemyService {

	List<Enemy> enemiesList = new ArrayList<Enemy>();

	// it is also possible to make the enemies move the random way or maybe create a
	// small AI in way they move to the enemy
	public void move() {
		for (Enemy enemy : enemiesList) {
			int x = enemy.getX();
			int y = enemy.getY();

			if (x < enemy.getMaxX() && x > -enemy.getMaxX()) {
				enemy.moveX();
			} else {
				enemy.changeDirectionX();
			}

			if (y < enemy.getMaxY() && y > -enemy.getMaxY()) {
				enemy.moveY();
			} else {
				enemy.changeDirectionY();
			}
			System.out.println("Enemy position: ("+x+","+y+")");
		}
	}

	public void create() {
		if (enemiesList != null) {
			enemiesList = new ArrayList<Enemy>();
		}
		// TODO load the enemies position from a datasource
		enemiesList.add(new Enemy(-1, -1, 1, 0));
		enemiesList.add(new Enemy(1, 1, -1, 0));
		enemiesList.add(new Enemy(-1, 1, 1, 0));
		enemiesList.add(new Enemy(1, -1, -1, 0));
	}
}