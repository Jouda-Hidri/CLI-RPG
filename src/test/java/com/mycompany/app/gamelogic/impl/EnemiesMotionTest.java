package com.mycompany.app.gamelogic.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.app.domain.Character;

import org.junit.Before;
import org.junit.Test;

import com.mycompany.app.domain.Enemy;

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
	public void testMove() {

		motion.move();

		assertEquals(1, enemy1.getX());
	}

	@Test
	public void testReduceHealth() {
		Character character = new Character();
		character.setHealth(2);

		motion.reduceHealth(character);

		assertEquals(1, character.getHealth());
	}
}
