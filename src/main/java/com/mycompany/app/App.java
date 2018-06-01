package com.mycompany.app;

import java.util.Scanner;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;
import com.mycompany.app.domain.GameMap;
import com.mycompany.app.domain.persistence.GamePersistence;
import com.mycompany.app.gamelogic.impl.CharacterMotion;
import com.mycompany.app.gamelogic.impl.EnemiesMotion;

/**
 * role playing game
 *
 */
public class App {
	public static void main(String[] args) {
		CharacterMotion characterMotion = new CharacterMotion();
		EnemiesMotion enemiesMotion = new EnemiesMotion();
		GameMap gameMap = new GameMap();

		Scanner scanner = new Scanner(System.in);

		createOrResume(scanner, characterMotion, enemiesMotion);
		moveAndFight(scanner, characterMotion, enemiesMotion, gameMap);
	}

	/**
	 * check if there is a saved game. Then ask the player if they want to resume or
	 * start a new game. Otherwise the player can only start a new game
	 */
	private static void createOrResume(Scanner scanner, CharacterMotion characterMotion, EnemiesMotion enemyMotion) {
		// check if there is a saved game
		Character character = GamePersistence.resume();
		characterMotion.setCharacter(character);
		String input = new String();
		if (character != null) {
			System.out.println("Previous game:");
			System.out.println("Character: " + character);
			while (!input.equals("s") && !input.equals("r")) {
				System.out.println("Start a new game [s] or resume [r]? ");
				input = scanner.next();
			}
		} else {
			// in case there is no saved game, the player can only start a new game
			while (!input.equals("s")) {
				System.out.println("Start a new game [s]? ");
				input = scanner.next();
			}
		}
		if (input.equals("s")) {
			characterMotion.create();
			character = characterMotion.getCharacter();
			System.out.println("Character status: " + character);
		}
		// create enemies
		enemyMotion.create();
	}

	/**
	 * The player can be exploring the map until they reach the exit point or they
	 * get the same position as an enemy. In this case the enemy would start
	 * attacking. The player can also fight. The fight will end when the enemy or
	 * the player dies. In case the enemy dies, the player can keep on exploring. In
	 * case the player dies: Game Over!
	 */
	private static void moveAndFight(Scanner scanner, CharacterMotion characterMotion, EnemiesMotion enemieMotion,
			GameMap gameMap) {
		Character character = characterMotion.getCharacter();
		if (character != null) {
			while (character.getX() != gameMap.getExitX() || character.getY() != gameMap.getExitY()) {
				// display menu
				System.out.println("Exit: (" + gameMap.getExitX() + "," + gameMap.getExitY() + ")");
				System.out.print("Explore (w: up - s: down - a: left - d: right)");
				System.out.print("   ");
				System.out.println("Save and quit [q]");
				String input = scanner.next();
				if (input.equals("q")) {
					GamePersistence.save(character);
					break;
				}
				// move
				int directionX = 0;
				int directionY = 0;
				if (input.equals("a")) {
					directionX -= 1;
				}
				if (input.equals("d")) {
					directionX += 1;
				}
				if (input.equals("w")) {
					directionY += 1;
				}
				if (input.equals("s")) {
					directionY -= 1;
				}
				character.setDirectionX(directionX);
				character.setDirectionY(directionY);
				characterMotion.move();
				enemieMotion.move();
				// fight in case there is an attacking enemy
				Enemy attackingEnemy = checkCollision(characterMotion, enemieMotion);
				if (attackingEnemy != null) {
					fight(scanner, characterMotion, enemieMotion, attackingEnemy);
					if (character.getHealth() <= 0) {
						// it is possible here to extend the game: life -- and restart
						break;
					}
				}

			}
			System.out.println("You reached the exit point. You wone!!!");
		} else {
			System.out.println("An error occured!");
		}
	}

	private static Enemy checkCollision(CharacterMotion characterMotion, EnemiesMotion enemiesMotion) {
		Character character = characterMotion.getCharacter();
		for (Enemy enemy : enemiesMotion.getEnemiesList()) {
			if (enemy.getX() == character.getX() && enemy.getY() == character.getY()) {
				return enemy;
			}
		}
		return null;
	}

	/**
	 * Check if there is an attacking enemy. Then start the fight until the enemy or
	 * the player dies
	 */
	private static void fight(Scanner scanner, CharacterMotion characterMotion, EnemiesMotion enemiesMotion,
			Enemy attackingEnemy) {
		Character character = characterMotion.getCharacter();
		if (attackingEnemy != null) {
			while (attackingEnemy.getHealth() > 0) {
				// enemy attack; then check check player health
				System.out.println("You are being attacked ...");
				enemiesMotion.fight(character);
				if (character.getHealth() <= 0) {
					System.out.println("You died! \n GAME OVER");
					break;
				}
				System.out.println("You: " + character);
				// player attack; then check enemy health
				System.out.println("You can fight [f]");
				String input = scanner.next();
				if (input.equals("f")) {
					characterMotion.fight(attackingEnemy);
				}
				System.out.println("Enemy: " + attackingEnemy);
			}
			System.out.println("Enemy died \n");
			System.out.println("You: " + character);
		}
	}
}