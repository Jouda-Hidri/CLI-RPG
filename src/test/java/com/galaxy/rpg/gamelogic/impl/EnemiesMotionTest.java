package com.galaxy.rpg.gamelogic.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;
import com.galaxy.rpg.gamelogic.impl.EnemiesMotion;

public class EnemiesMotionTest {
	EnemiesMotion motion = new EnemiesMotion();
	Enemy enemy1 = new Enemy(0, 0, 1, 0);
	List<Enemy> enemiesList = new ArrayList<Enemy>();

	@Before
	public void init() {
		enemiesList.add(enemy1);
		motion.setEnemiesList(enemiesList);
	}
	
	@Test
	public void testCreate() {
		motion.create();
		assertTrue (motion.getEnemiesList().size()>0);
	}

	@Test
	public void testMove() {
		motion.move();
		assertEquals(1, enemy1.getX());
	}

	@Test
	public void testFight() {
		Character character = new Character();
		character.setHealth(2);

		motion.fight(character);

		assertEquals(1, character.getHealth());
	}

	@Test
	public void testReduceHealth() {
		Character character = new Character();
		character.setHealth(2);

		motion.reduceHealth(character);

		assertEquals(1, character.getHealth());
	}
}
