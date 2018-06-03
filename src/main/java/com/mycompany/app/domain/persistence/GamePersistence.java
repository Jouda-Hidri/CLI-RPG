package com.mycompany.app.domain.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.mycompany.app.domain.Character;
import com.mycompany.app.domain.Enemy;

public class GamePersistence {

	private static String CHARACTER_FILE_PATH = "character.md";
	private static String ENEMIES_FILE_PATH = "enemies.md";

	public static void save(Character character) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(CHARACTER_FILE_PATH);
			out = new ObjectOutputStream(fos);
			out.writeObject(character);
			out.close();
		} catch (Exception e) {
		}
	}

	public static Character resumeCharacter() {
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

	public static void save(List<Enemy> enemiesList) {
		try {
			FileOutputStream out = new FileOutputStream(ENEMIES_FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(enemiesList);
			oos.close();
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Enemy> resumeEnemiesList() {
		List<Enemy> enemiesList = null;
		try {
			FileInputStream in = new FileInputStream(ENEMIES_FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(in);
			enemiesList = (List<Enemy>) (ois.readObject());
			in.close();
		} catch (Exception e) {
		}
		return enemiesList;
	}
}