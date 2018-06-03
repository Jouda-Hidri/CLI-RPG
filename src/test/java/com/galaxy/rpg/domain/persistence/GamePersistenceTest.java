package com.galaxy.rpg.domain.persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;

public class GamePersistenceTest {

	GamePersistence gamePersistence = new GamePersistence();
	// load saved data, to restore them later
	final private Character character = GamePersistence.loadCharacter();
	final private List<Enemy> enemiesList = GamePersistence.loadEnemiesList();
	final private int storylineId = GamePersistence.getStorylineId();

	@Test
	public void testCharacterPersistence() {
		Character dummy = new Character();
		dummy.setX(5);
		dummy.setX(-2);
		dummy.setExperience(1);
		dummy.setHealth(3);

		GamePersistence.save(dummy);
		Character result = GamePersistence.loadCharacter();

		assertEquals(dummy.getX(), result.getX());
		assertEquals(dummy.getY(), result.getY());
		assertEquals(dummy.getExperience(), result.getExperience());
		assertEquals(dummy.getHealth(), result.getHealth());
	}

	@Test
	public void testEnemiesPersistence() {
		Enemy dummy1 = new Enemy(0, 0, 1, 0);
		Enemy dummy2 = new Enemy(1, 0, 1, 0);
		Enemy dummy3 = new Enemy(0, 1, 1, 0);
		List<Enemy> dummies = new ArrayList<Enemy>();
		dummies.add(dummy1);
		dummies.add(dummy2);
		dummies.add(dummy3);

		GamePersistence.save(dummies);
		List<Enemy> result = GamePersistence.loadEnemiesList();

		assertEquals(dummy1.getX(), result.get(0).getX());
		assertEquals(dummy1.getY(), result.get(0).getY());
		assertEquals(dummy1.getHealth(), result.get(0).getHealth());
		assertEquals(dummy2.getX(), result.get(1).getX());
		assertEquals(dummy2.getY(), result.get(1).getY());
		assertEquals(dummy2.getHealth(), result.get(1).getHealth());
		assertEquals(dummy3.getX(), result.get(2).getX());
		assertEquals(dummy3.getY(), result.get(2).getY());
		assertEquals(dummy3.getHealth(), result.get(2).getHealth());
	}

	@Test
	public void testStorylineIdPersistence() {
		int dummy = 5;

		GamePersistence.save(dummy);
		int result = GamePersistence.getStorylineId();

		assertEquals(dummy, result);
	}

	@After
	public void retoreData() {
		GamePersistence.save(storylineId, character, enemiesList);
	}
}
