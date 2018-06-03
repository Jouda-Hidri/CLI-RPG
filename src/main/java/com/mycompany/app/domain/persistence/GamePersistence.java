package com.mycompany.app.domain.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mycompany.app.domain.Character;

public class GamePersistence {

	private static String CHARACTER_FILE_PATH = "character.md";

	// private String CHARACTER_FILE_PATH =
	// this.getClass().getClassLoader().getResource("/character.md").getPath();

	public static void save(Character character) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(CHARACTER_FILE_PATH);
			out = new ObjectOutputStream(fos);
			out.writeObject(character);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Character resume() {
		try {
			FileInputStream fis = new FileInputStream(CHARACTER_FILE_PATH);
			ObjectInputStream in = new ObjectInputStream(fis);
			Character character = (Character) in.readObject();
			in.close();
			return character;
		} catch (Exception e) {

		}
		return null;
	}

}
