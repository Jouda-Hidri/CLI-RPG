package com.mycompany.app.gamelogic.impl;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;
import com.mycompany.app.gamelogic.PlayerMotion;

public class CharacterMotion implements PlayerMotion<Enemy> {

	Character character;

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Character getCharacter() {
		return character;
	}

	@Override
	public void create() {
		character = new Character();
		System.out.println("Character created");
	}

	@Override
	public void move() {
		if (character == null) {
			System.out.println("You do not have any character");
		} else {
			int x2 = character.getX() + character.getDirectionX();
			int y2 = character.getY() + character.getDirectionY();
			character.setX(x2);
			character.setY(y2);
			System.out.println("You: " + character);
		}
	}

	@Override
	public void fight(Enemy attackingEnemy) {
		if (attackingEnemy != null) {
			reduceHealth(attackingEnemy);
			if (attackingEnemy.getHealth() == 0) {
				gainExperience();
			}
		}
	}

	@Override
	public void reduceHealth(Enemy enemy) {
		int health = enemy.getHealth();
		health--;
		enemy.setHealth(health);
		System.out.println("They lost health: " + enemy.getHealth());
	}

	public void gainExperience() {
		int experience = character.getExperience();
		experience++;
		character.setExperience(experience);
		System.out.println("You gained experience: " + character.getExperience());
	}
}
