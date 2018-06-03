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

	private static final String DARTH_VADER = "/darth-vader.txt";
	private static final String X_WING = "/x-wing.txt";
	private static final String WALKER_ATTACKING = "/walker-attacking.txt";
	private static final String HOTH = "/hoth-left.txt";

	private static AsciiPicture asciiPicture = new AsciiPicture();
	private static GameText gameText = new GameText();

	private static Scanner scanner = new Scanner(System.in);

	private static CharacterMotion characterMotion = new CharacterMotion();
	private static EnemiesMotion enemiesMotion = new EnemiesMotion();

	public static void main(String[] args) {
		GameMap gameMap = new GameMap();
		createOrResume();
		moveAndFight(gameMap);
	}

	/**
	 * check if there is a saved game. Then ask the player if they want to resume or
	 * start a new game. Otherwise the player can only start a new game
	 */
	private static void createOrResume() {
		// Per default the previous game will be loaded,
		// unless no saved game were found
		// and/or the player wants to start a new one
		System.out.println("Loading the previous game ...");
		Character character = GamePersistence.resumeCharacter();
		List<Enemy> enemiesList = GamePersistence.resumeEnemiesList();
		characterMotion.setCharacter(character);
		enemiesMotion.setEnemiesList(enemiesList);

		// Check if loading the game were successful
		String input = new String();
		if (character != null && enemiesList != null) {
			// In case loading the game were successful,
			// ask the player if they want to resume the game or start a new one
			System.out.println("Previous game:");
			System.out.println("You: " + character);
			System.out.println(gameText.getEnemy() + "s: " + enemiesList);
			while (!input.equals(START_NEW) && !input.equals(RESUME)) {
				System.out.print("Start a new game [n] or resume [r]? ");
				input = scanner.next();
			}
		} else {
			// In case loading the game were not successful,
			// the player can only start a new one
			System.out.println("No game found!");
			while (!input.equals(START_NEW)) {
				System.out.print("Start a new game [n]? ");
				input = scanner.next();
			}
		}

		// In case the player would like to start a new game
		if (input.equals(START_NEW)) {
			// TODO ask the player to choose a topic
			gameText.setEnemy("Rebel");
			gameText.setTarget("Hoth");
			gameText.setIntroMessage(
					" Hey Dath Vader! \n We found out where the rebels are hiding! \n You should go to the Hoth ... ");
			gameText.setLooseMessage("Darth Vader died! \n GAME OVER!");
			gameText.setWinMessage("You reached the Hoth! \n YOU WON!!!");

			// create character
			characterMotion.create();
			character = characterMotion.getCharacter();
			// create enemies
			enemiesMotion.create();
			enemiesList = enemiesMotion.getEnemiesList();

			System.out.println("-------------------------------------------------------------------------");
			asciiPicture.display(DARTH_VADER);
			System.out.println("-------------------------------------------------------------------------");
			System.out.println(gameText.getIntroMessage());
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("You: " + character);
			System.out.println(gameText.getEnemy() + "s: " + enemiesList);
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * The player can be exploring the map until they reach the exit point or they
	 * get the same position as an enemy. In this case the enemy would start
	 * attacking. The player can also fight. The fight will end when the enemy or
	 * the player dies. In case the enemy dies, the player can keep on exploring. In
	 * case the player dies: Game Over!
	 */
	private static void moveAndFight(GameMap gameMap) {
		Character character = characterMotion.getCharacter();
		List<Enemy> enemiesList = enemiesMotion.getEnemiesList();
		if (character != null) {
			while (character.getX() != gameMap.getTargetX() || character.getY() != gameMap.getTargetY()) {

				// display menu
				System.out.println("-------------------------------------------------------------------------");
				System.out.print("Explore (up [w]  -down [s]  -left [s]  -right [d])");
				System.out.print("  -  ");
				System.out.print("Save and quit [q] ");
				String input = scanner.next();
				if (input.equals(QUIT)) {
					GamePersistence.save(character);
					GamePersistence.save(enemiesList);
					break;
				}
				System.out.println("-------------------------------------------------------------------------");
				// move
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
				System.out.println(gameText.getEnemy() + "s");
				enemiesMotion.move();
				// fight in case there is an attacking enemy
				Enemy attackingEnemy = checkCollision();
				if (attackingEnemy != null) {
					fight(attackingEnemy);
					if (character.getHealth() <= 0) {
						// it is possible here to extend the game: life -- and restart
						break;
					}
				}
				System.out.println(gameText.getTarget() + ": position (" + gameMap.getTargetX() + ","
						+ gameMap.getTargetY() + ")");
			}
			if (character.getX() == gameMap.getTargetX() //
					&& character.getY() == gameMap.getTargetY()) {

				asciiPicture.display(HOTH);

				System.out.println(gameText.getWinMessage());
			}
		} else {
			System.out.println("An error occured!");
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
			while (attackingEnemy.getHealth() > 0) {
				// enemy attacks; then check character health
				asciiPicture.display(X_WING);

				System.out.println("You are being attacked ...");
				enemiesMotion.fight(character);
				if (character.getHealth() <= 0) {
					System.out.println(gameText.getLooseMessage());
					break;
				}
				System.out.println("You: " + character);
				// character attacks; then check enemy health
				System.out.print("You can fight [f] ");
				String input = scanner.next();
				if (input.equals(FIGHT)) {

					asciiPicture.display(WALKER_ATTACKING);

					characterMotion.fight(attackingEnemy);
				}
				System.out.println(gameText.getEnemy() + ": " + attackingEnemy);
			}

			if ((attackingEnemy.getHealth() <= 0)) {
				System.out.println(gameText.getEnemy() + " died \n");
				System.out.println("You: " + character);
			}
		}
	}
}