package com.galaxy.rpg.gamelogic.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;
import com.galaxy.rpg.gamelogic.impl.CharacterMotion;

public class CharacterMotionTest {

	CharacterMotion motion = new CharacterMotion();

	@Test
	public void testMove() {
		Character character = new Character();
		character.setX(0);
		character.setY(0);
		character.setDirectionX(1);
		character.setDirectionY(0);
		motion.setCharacter(character);

		motion.move();
		Character result = motion.getCharacter();

		assertEquals(1, result.getX());
		assertEquals(0, result.getY());
	}

	@Test
	public void testFightWhenEnemyDoesntDie() {
		Enemy enemy = new Enemy();
		enemy.setHealth(2);
		Character character = new Character();
		character.setExperience(0);
		motion.setCharacter(character);

		motion.fight(enemy);

		assertEquals(1, enemy.getHealth());
		assertEquals(0, character.getExperience());
	}

	@Test
	public void testFightWhenEnemyDies() {
		Enemy enemy = new Enemy();
		enemy.setHealth(1);
		Character character = new Character();
		character.setExperience(0);
		motion.setCharacter(character);

		motion.fight(enemy);

		assertEquals(0, enemy.getHealth());
		assertEquals(1, character.getExperience());
	}

	@Test
	public void testReduceHealth() {
		Enemy enemy = new Enemy();
		enemy.setHealth(2);
		Character character = new Character();
		motion.setCharacter(character);

		motion.reduceHealth(enemy);

		assertEquals(1, enemy.getHealth());
	}

	@Test
	public void testGainExperience() {
		Character character = new Character();
		character.setExperience(0);
		motion.setCharacter(character);

		motion.gainExperience();

		assertEquals(1, character.getExperience());
	}
}
