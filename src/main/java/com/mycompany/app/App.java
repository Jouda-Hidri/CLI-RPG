package com.mycompany.app;

import java.util.Scanner;

import com.mycompany.app.domain.Character;
import com.mycompany.app.service.EnemyService;
import com.mycompany.app.service.PlayerService;

/**
 * role payling game
 *
 */
public class App {
	public static void main(String[] args) {
		PlayerService service = new PlayerService();
		EnemyService enemyService = new EnemyService();
		int exitX = 4;
		int exitY = 2;

		Scanner scanner = new Scanner(System.in);

		System.out.print("You do not have any Character. Create character [Y/n]?");

		String create = scanner.next();
		if (create.equals("Y")) {
			Character character = service.create();
			System.out.print("You can now start exploring. (w: up - s: down - a: left - d: right)");
			// we create also the enemies and then we start the game
			enemyService.create();
			while (character.getX() != exitX || character.getY() != exitY) {
				String direction = scanner.next();
				int x = 0;
				int y = 0;
				if (direction.equals("a")) {
					x -= 1;
				}
				if (direction.equals("d")) {
					x += 1;
				}
				if (direction.equals("w")) {
					y += 1;
				}
				if (direction.equals("s")) {
					y -= 1;
				}
				service.move(x, y);
				enemyService.move();
			}

		} else {
			// TODO ask the user of they want to leave the game?
		}
	}

}
