package com.mycompany.app.service;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Game;

public class PlayerService {

	Character character;

	public Character create() {
		character = new Character();
		System.out.println("new character created: "+character);
		return character;
	}

	public void explore(int x, int y) {
		if(character == null) {
			System.out.println("You do not have any character");
		}else {
			int x2 = character.getX() + x;
			int y2 = character.getY() + y;
			
			character.setX(x2);
			character.setY(y2);
			System.out.println("Character status: "+character);
		}
	}

	public void fight() {
		// gain experience

	}

	public Game save() {
		return null;
	}

	public Game resume(int playerId) {
		return null;
	}

}
