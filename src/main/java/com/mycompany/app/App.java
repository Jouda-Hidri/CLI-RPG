package com.mycompany.app;

import java.util.Scanner;

import com.mycompany.app.service.PlayerService;
import com.mycompany.app.domain.Character;

/**
 * role payling game
 *
 */
public class App {
	public static void main(String[] args) {
		PlayerService service = new PlayerService();

		Scanner scanner = new Scanner(System.in);

		System.out.print("You do not have any Character. Create character [Y/n]?");

		String create = scanner.next();
		if (create.equals("Y")) {
			Character character = service.create();
			System.out.print("You can know start exploring. (w or z:up - s:down - a or q:left - d:right)");
			int i = 5;
			while (character.getLife() > 0) {
				String direction = scanner.next();
				int x = 0;
				int y = 0;
				if (direction.equals("a") || direction.equals("q") ) {
					x -= 1;
				}
				if (direction.equals("d")) {
					x += 1;
				}
				if (direction.equals("w") || direction.equals("z")) {
					y += 1;
				}
				if (direction.equals("s")) {
					y -= 1;
				}
				service.explore(x, y);
				character.setLife(--i);
			}

		} else {
			// TODO leave the game?
		}
	}

}
