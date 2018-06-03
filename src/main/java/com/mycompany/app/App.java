package com.mycompany.app;

import java.util.List;
import java.util.Scanner;

import com.mycompany.app.cli.AsciiPicture;
import com.mycompany.app.cli.GameText;
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

	private static final String START_NEW = "n";
	private static final String RESUME = "r";
	private static final String QUIT = "q";
	private static final String UP = "w";
	private static final String RIGHT = "d";
	private static final String LEFT = "a";
	private static final String DOWN = "s";
	private static final String FIGHT = "f";

	private static int topic = 0;

	private static CharacterMotion characterMotion = new CharacterMotion();
	private static EnemiesMotion enemiesMotion = new EnemiesMotion();
	private static GameMap gameMap = new GameMap();
	private static GameText gameText = new GameText();
	private static AsciiPicture asciiPicture = new AsciiPicture();

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		initGame();
		runGame();
	}

	/**
	 * check if there is a saved game. Then ask the player if they want to resume or
	 * start a new game. Otherwise the player can only start a new game
	 */
	private static void initGame() {
		// Per default the previous game will be loaded,
		// unless no saved game were found
		// and/or the player wants to start a new one
		System.out.println("Loading the previous game ...");
		Character character = GamePersistence.loadCharacter();
		List<Enemy> enemiesList = GamePersistence.loadEnemiesList();
		topic = GamePersistence.getTopic();
		gameText = new GameText(topic);
		asciiPicture = new AsciiPicture(topic);
		characterMotion.setCharacter(character);
		enemiesMotion.setEnemiesList(enemiesList);

		// Check if loading the game were successful
		String input = new String();
		if (character != null //
				&& enemiesList != null //
				&& gameText != null //
				&& asciiPicture != null) {
			// In case loading the game were successful,
			// ask the player if they want to resume the game or start a new one
			System.out.println("Previous game:");
			System.out.println("You: " + character);
			System.out.println(gameText.getEnemy() + "s: " + enemiesList);
			while (!input.equals(START_NEW) && !input.equals(RESUME)) {
				System.out.printf("Start a new game [%s] or resume [%s]? ", START_NEW, RESUME);
				input = scanner.next();
			}
		} else {
			// In case loading the game were not successful,
			// the player can only start a new one
			System.out.println("No game found!");
			while (!input.equals(START_NEW)) {
				System.out.printf("Start a new game [%s]? ", START_NEW);
				input = scanner.next();
			}
		}

		// In case the player would like to start a new game
		if (input.equals(START_NEW)) {
			input = new String();
			while (!input.equals("1") && !input.equals("2")) {
				System.out.print("Choose a character: Darth Vader [1] or Luke Skywalker [2] ");
				input = scanner.next();
			}
			if (input.equals("1")) {
				gameText = new GameText(1);
				asciiPicture = new AsciiPicture(1);
			}
			if (input.equals("2")) {
				gameText = new GameText(2);
				asciiPicture = new AsciiPicture(2);
			}
			// create character
			characterMotion.create();
			character = characterMotion.getCharacter();
			// create enemies
			enemiesMotion.create();
			enemiesList = enemiesMotion.getEnemiesList();

			asciiPicture.display("character");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println(gameText.getIntroMessage());
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("You: " + character);
			System.out.println(gameText.getEnemy() + "s: " + enemiesList);
		}
	}

	/**
	 * The player can be exploring the map until they reach the target point or they
	 * get the same position as an enemy (collision). In this case the enemy would
	 * start attacking. The player can also fight. The fight will end when the enemy
	 * or the player dies. In case the enemy dies, the player can keep on exploring.
	 * In case the player dies: Game Over!
	 */
	private static void runGame() {
		Character character = characterMotion.getCharacter();
		List<Enemy> enemiesList = enemiesMotion.getEnemiesList();
		int targetX = gameMap.getTargetX();
		int targetY = gameMap.getTargetY();
		if (character == null) {
			System.out.println("An error occured!");
		} else {
			while (character.getX() != targetX || character.getY() != targetY) {
				// display possible actions
				System.out.println("-------------------------------------------------------------------------");
				System.out.printf("Explore (up [%s]  -down [%s]  -left [%s]  -right [%s])", UP, DOWN, LEFT, RIGHT);
				System.out.print("  -  ");
				System.out.printf("Save and quit [%s] ", QUIT);
				String input = scanner.next();

				// quit
				if (input.equals(QUIT)) {
					GamePersistence.save(topic, character, enemiesList);
					break;
				}
				System.out.println("-------------------------------------------------------------------------");

				// character moves
				int directionX = 0;
				int directionY = 0;
				if (input.equals(LEFT)) {
					directionX -= 1;
				}
				if (input.equals(RIGHT)) {
					directionX += 1;
				}
				if (input.equals(UP)) {
					directionY += 1;
				}
				if (input.equals(DOWN)) {
					directionY -= 1;
				}
				character.setDirectionX(directionX);
				character.setDirectionY(directionY);
				characterMotion.move();

				// display target position
				System.out.println(gameText.getTarget() + ": position (" + gameMap.getTargetX() + ","
						+ gameMap.getTargetY() + ")");

				// enemies move
				System.out.println(gameText.getEnemy() + "s");
				enemiesMotion.move();

				// check if there is a collision
				Enemy attackingEnemy = checkCollision();
				if (attackingEnemy != null) {

					// fight in case there is a collision
					fight(attackingEnemy);
					if (character.getHealth() <= 0) {
						// it is possible here to extend the game: life -- and restart
						break;
					}
				}
			}
			// win in case character reaches the target
			if (character.getX() == targetX && character.getY() == targetY) {
				asciiPicture.display("target");
				System.out.println(gameText.getWinMessage());
			}
		}
	}

	private static Enemy checkCollision() {
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
	 * the character dies
	 */
	private static void fight(Enemy attackingEnemy) {
		Character character = characterMotion.getCharacter();
		if (attackingEnemy != null) {
			while (attackingEnemy.getHealth() > 0 && //
					character.getHealth() > 0) {

				// enemy attacks
				asciiPicture.display("attacked");
				System.out.println("You got attacked ...");
				enemiesMotion.fight(character);

				System.out.println("You: " + character);

				// character attacks
				System.out.printf("You can fight [%s] ", FIGHT);
				String input = scanner.next();
				if (input.equals(FIGHT)) {
					asciiPicture.display("attacking");
					characterMotion.fight(attackingEnemy);
				}

				System.out.println(gameText.getEnemy() + ": " + attackingEnemy);

			}

			if ((attackingEnemy.getHealth() <= 0)) {
				System.out.println(gameText.getEnemy() + " died \n");
			}

			System.out.println("You: " + character);

			if (character.getHealth() <= 0) {
				System.out.println(gameText.getLooseMessage());
			}
		}
	}
}