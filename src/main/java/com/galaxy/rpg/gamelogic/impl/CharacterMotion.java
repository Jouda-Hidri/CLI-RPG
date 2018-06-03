package com.galaxy.rpg.gamelogic.impl;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;
import com.galaxy.rpg.gamelogic.PlayerMotion;

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
	}

	@Override
	public void move() {
		if (character != null) {
			int x2 = character.getX() + character.getDirectionX();
			int y2 = character.getY() + character.getDirectionY();
			character.setX(x2);
			character.setY(y2);
		}
	}

	@Override
	public void fight(Enemy attackingEnemy) {
		if (attackingEnemy != null) {
			reduceHealth(attackingEnemy);
			if (attackingEnemy.getHealth() == 0) {
				System.out.println("They died");
				gainExperience();
			}
			System.out.println("They: " + attackingEnemy);
		}
	}

	@Override
	public void reduceHealth(Enemy enemy) {
		int health = enemy.getHealth();
		health--;
		enemy.setHealth(health);
		System.out.println("They lost 1 point of health!");
	}

	public void gainExperience() {
		int experience = character.getExperience();
		experience++;
		character.setExperience(experience);
		System.out.println("You gained experience: " + character.getExperience());
	}
}
