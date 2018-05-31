package com.mycompany.app.service;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;

public class PlayerService {

	Character character;

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Character create() {
		character = new Character();
		System.out.println("new character created");
		return character;
	}

	public void move(int x, int y) {
		if (character == null) {
			System.out.println("You do not have any character");
		} else {
			int x2 = character.getX() + x;
			int y2 = character.getY() + y;
			character.setX(x2);
			character.setY(y2);
			System.out.println("Character status: " + character);
		}
	}

	public void fight(Enemy attackingEnemy) {
		if (attackingEnemy != null) {
			int enemyHealth = attackingEnemy.reduceHeath();
			if (enemyHealth == 0) {
				character.gainExperience();
				attackingEnemy = null;
			}
		}
	}

}
