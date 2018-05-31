package com.mycompany.app;

import java.util.Scanner;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;
import com.mycompany.app.domain.persistence.Game;
import com.mycompany.app.service.EnemyService;
import com.mycompany.app.service.PlayerService;

/**
 * role playing game
 *
 */
public class App {
	public static void main(String[] args) {
		PlayerService service = new PlayerService();
		EnemyService enemyService = new EnemyService();
		Game game = new Game();
		boolean gameOver = false;
		int exitX = 4;
		int exitY = 2;

		Scanner scanner = new Scanner(System.in);

		// check if there is a saved game. Then ask the player if they want to resume or
		// start a new game. Otherwise the player can only start a new game
		Character character = game.resume();
		service.setCharacter(character);
		System.out.println("Previous game:");
		System.out.println("Character: " + character);
		String input = new String();
		if (character == null) {
			System.out.println("Start a new game [s]? ");
			input = scanner.next();
		} else {
			System.out.println("Start a new game [s] or resume [r]? ");
			input = scanner.next();
		}
		// TODO check if the player typed in s or r, otherwise ask to type in the
		// correct letter
		if (input.equals("s")) {
			character = service.create();
		}

		if (character != null) {
			System.out.println("Character status: " + character);
			// create enemies
			enemyService.create();
			// start the game
			while (character.getX() != exitX || character.getY() != exitY) {
				System.out.println("Exit: (" + exitX + "," + exitY + ")");
				System.out.println("Explore (w: up - s: down - a: left - d: right)");
				System.out.println("Save and quit [q]");
				input = scanner.next();
				if (input.equals("q")) {
					game.save(character);
					break;
				}
				int x = 0;
				int y = 0;
				if (input.equals("a")) {
					x -= 1;
				}
				if (input.equals("d")) {
					x += 1;
				}
				if (input.equals("w")) {
					y += 1;
				}
				if (input.equals("s")) {
					y -= 1;
				}
				service.move(x, y);
				// in case the character has the same position as an enemy, this enemy would
				// start attacking
				Enemy attackingEnemy = enemyService.move(character);
				// as long as the enemy does not die, it will keep on attacking
				while (attackingEnemy != null && attackingEnemy.getHealth() > 0) {
					System.out.println("You are being attacked ...");
					// in case the player dies, game over
					gameOver = enemyService.attack(attackingEnemy, character);
					if (gameOver) {
						break;
					}
					System.out.println("You: " + character);
					System.out.println("You can fight [f]");
					input = scanner.next();
					if (input.equals("f")) {
						// attackingEnemy would be null in case health becomes <= 0
						service.fight(attackingEnemy);
					}
					System.out.println("Enemy: " + attackingEnemy);
				}
				if (gameOver) {
					System.out.println("You died! \n GAME OVER");
					break; // TODO life --; restart the game
				}
				if (attackingEnemy != null && attackingEnemy.getHealth() <= 0) {
					System.out.println("Enemy died \n");
					System.out.println("You: " + character);
				}
			}

		} else {
			// no saved game and no new game created
			// TODO show the menu : leave? start a new game etc.
		}

	}

}
