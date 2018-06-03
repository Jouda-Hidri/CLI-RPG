package com.galaxy.rpg;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.galaxy.rpg.cli.AsciiArt;
import com.galaxy.rpg.cli.GameText;
import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;
import com.galaxy.rpg.domain.GameMap;
import com.galaxy.rpg.domain.persistence.GamePersistence;
import com.galaxy.rpg.gamelogic.impl.CharacterMotion;
import com.galaxy.rpg.gamelogic.impl.EnemiesMotion;

/**
 * role playing game
 *
 */
public class Rpg {

	private static final String START_NEW = "n";
	private static final String RESUME = "r";
	private static final String QUIT = "q";
	private static final String UP = "w";
	private static final String RIGHT = "d";
	private static final String LEFT = "a";
	private static final String DOWN = "s";
	private static final String FIGHT = "f";

	private static int storylineId = 0;

	private static CharacterMotion characterMotion = new CharacterMotion();
	private static EnemiesMotion enemiesMotion = new EnemiesMotion();
	private static GameMap gameMap = new GameMap();
	private static GameText gameText = new GameText();
	private static AsciiArt asciiArt = new AsciiArt();

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		initGame();
		runGame();
	}

	/**
	 * Check if there is a saved game. In case there is a saved game ask the player
	 * if they want to resume or start a new game. In case there is a saved game and
	 * the player wants to resume, this game will be resumed. In case there is no
	 * saved game or the player does not want to resume, a new game will be created.
	 */
	private static void initGame() {
		// init input variable
		String startOrResumeInput = new String();

		// load the game
		System.out.println("Loading the previous game ...");
		Character character = GamePersistence.loadCharacter();
		List<Enemy> enemiesList = GamePersistence.loadEnemiesList();
		storylineId = GamePersistence.getStorylineId();
		gameText = new GameText(storylineId);
		asciiArt = new AsciiArt(storylineId);

		// check if loading were successful
		boolean successfulLoading = character != null && enemiesList != null && storylineId != 0;

		if (successfulLoading) {
			// Display the previous game state
			System.out.println("Previous game:");
			displayGameState(character, enemiesList);

			// ask the player if they want to resume the game or start a new one
			while (!startOrResumeInput.equals(START_NEW) && !startOrResumeInput.equals(RESUME)) {
				System.out.printf("Start a new game [%s] or resume [%s]? ", START_NEW, RESUME);
				startOrResumeInput = scanner.next();
			}
		} else {
			System.out.println("There is no saved game! Starting a new game ... ");
		}

		// In case loading were successful and the player wants to resume
		if (successfulLoading && startOrResumeInput.equals(RESUME)) {
			// Resume the game
			characterMotion.setCharacter(character);
			enemiesMotion.setEnemiesList(enemiesList);
		} else {
			// Create a new game: the player can choose a character
			String input = new String();

			while (!input.equals("1") && !input.equals("2")) {
				System.out.print("Choose a character: Darth Vader [1] or Luke Skywalker [2] ");
				input = scanner.next();
			}

			storylineId = Integer.parseInt(input);
			gameText = new GameText(storylineId);
			asciiArt = new AsciiArt(storylineId);

			// create character
			characterMotion.create();
			character = characterMotion.getCharacter();

			// create enemies
			enemiesMotion.create();
			enemiesList = enemiesMotion.getEnemiesList();

			// display
			asciiArt.display("character");
			displayHorizontalRow();
			System.out.println(gameText.getIntroMessage());
			displayHorizontalRow();
			displayGameState(character, enemiesList);
		}
	}

	/**
	 * The player can be exploring the map until they reach the target point or they
	 * get the same position as an enemy (collision). In this case the enemy would
	 * start attacking. The player can also fight. The fight will end when the enemy
	 * or the player dies. In case the enemy dies, the player can keep on exploring.
	 * In case the player dies: Game Over!
	 */
	static void runGame() {
		Character character = characterMotion.getCharacter();
		List<Enemy> enemiesList = enemiesMotion.getEnemiesList();
		int targetX = gameMap.getTargetX();
		int targetY = gameMap.getTargetY();

		if (character != null) {
			while (character.getX() != targetX || character.getY() != targetY) {
				displayHorizontalRow();
				// display possible actions
				System.out.printf("Explore (up [%s]  - down [%s]  - left [%s]  - right [%s])", UP, DOWN, LEFT, RIGHT);
				System.out.print("  -  ");
				System.out.printf("Save and quit [%s] ", QUIT);
				String input = scanner.next();

				// quit
				if (input.equals(QUIT)) {
					GamePersistence.save(storylineId, character, enemiesList);
					break;
				}

				displayHorizontalRow();
				displayGameState(character, enemiesList);

				// character moves
				int directionX = 0;
				int directionY = 0;
				switch (input) {
				case LEFT:
					directionX -= 1;
					break;
				case RIGHT:
					directionX += 1;
					break;
				case UP:
					directionY += 1;
					break;
				case DOWN:
					directionY -= 1;
					break;
				}
				character.setDirectionX(directionX);
				character.setDirectionY(directionY);
				characterMotion.move();

				// enemies move
				System.out.println(gameText.getEnemy() + "s");
				enemiesMotion.move();

				displayHorizontalRow();
				displayGameState(character, enemiesList);

				// check if there is a collision between the character and an enemy
				Optional<Enemy> attackingEnemy = checkCollision(characterMotion.getCharacter(),
						enemiesMotion.getEnemiesList());
				if (attackingEnemy.isPresent()) {
					// fight in case there is a collision
					fight(attackingEnemy.get());
					// if character dies after fight: leave the game loop
					if (character.getHealth() <= 0) {
						break;
					}
				}
			}

			// win in case character reaches the target
			if (character.getX() == targetX && character.getY() == targetY) {
				asciiArt.display("target");
				System.out.println(gameText.getWinMessage());
			}
		}
	}

	static Optional<Enemy> checkCollision(Character character, List<Enemy> enemiesList) {
		return enemiesList.stream()//
				.filter(e -> e.getX() == character.getX() //
						&& e.getY() == character.getY() //
						&& e.getHealth() > 0)
				.findFirst();
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
				asciiArt.display("attacked");
				enemiesMotion.fight(character);

				displayHorizontalRow();
				displayHorizontalRow();
				displayHorizontalRow();

				// character can attack
				System.out.printf("You can fight [%s] ", FIGHT);
				String input = scanner.next();

				if (input.equals(FIGHT)) {
					// if the player presses any other letter, they will miss their turn to attack
					// and get hit
					asciiArt.display("attacking");
					characterMotion.fight(attackingEnemy);
				}

				displayHorizontalRow();
				displayHorizontalRow();
				displayHorizontalRow();
			}

			if ((attackingEnemy.getHealth() <= 0)) {
				System.out.println(gameText.getEnemy() + " died");
			}

			displayGameState(character, enemiesMotion.getEnemiesList());

			if (character.getHealth() <= 0) {
				System.out.println(gameText.getLoseMessage());
			}
		}
	}

	private static void displayGameState(Character character, List<Enemy> enemiesList) {
		System.out.printf(gameText.getTarget() + ": position (%s,%s)\n", gameMap.getTargetX(), gameMap.getTargetY());
		System.out.println("You: " + character);
		enemiesList.stream()//
				.filter(e -> e.getHealth() > 0)//
				.forEach(e -> {
					System.out.println(gameText.getEnemy() + ": " + e);
				});
	}

	private static void displayHorizontalRow() {
		System.out.println("-----------------------------------------------------------------------------------------");
	}
}