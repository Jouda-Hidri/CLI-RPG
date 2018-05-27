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
			enemy.moveX();
			enemy.changeDirectionX();
			enemy.moveY();
			enemy.changeDirectionY();
			System.out.println("Enemy position: (" + enemy.getX() + "," + enemy.getY() + ")");
		}
	}

	public void create() {
		if (enemiesList != null) {
			enemiesList = new ArrayList<Enemy>();
		}
		// TODO load the enemies position from a datasource
		enemiesList.add(new Enemy(0, 2, 1, 0));
		enemiesList.add(new Enemy(2, 2, 0, -1));
		enemiesList.add(new Enemy(2, 0, 1, 0));
	}
}