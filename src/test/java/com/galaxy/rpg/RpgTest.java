package com.galaxy.rpg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;

/**
 * Unit test for simple App.
 */
public class RpgTest {

	@Test
	public void testCollision() {
		Character character1 = new Character();
		character1.setX(0);
		character1.setY(0);
		Character character2 = new Character();
		character2.setX(3);
		character2.setY(3);
		Enemy enemy1 = new Enemy(0, 0, 1, 0);
		Enemy enemy2 = new Enemy(1, 0, 1, 0);
		Enemy enemy3 = new Enemy(0, 1, 1, 0);
		List<Enemy> enemiesList = new ArrayList<Enemy>();
		enemiesList.add(enemy1);
		enemiesList.add(enemy2);
		enemiesList.add(enemy3);

		Optional<Enemy> result1 = Rpg.checkCollision(character1, enemiesList);
		Optional<Enemy> result2 = Rpg.checkCollision(character2, enemiesList);

		assertEquals(enemy1, result1.get());
		assertFalse(result2.isPresent());
	}
}
