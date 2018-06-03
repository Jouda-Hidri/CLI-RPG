package com.galaxy.rpg.domain.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.galaxy.rpg.domain.Character;
import com.galaxy.rpg.domain.Enemy;

public class GamePersistence {

	private static String CHARACTER_FILE_PATH = "character.sav";
	private static String ENEMIES_FILE_PATH = "enemies.sav";
	private static String STORYLINE_ID_FILE_PATH = "storyline_id.sav";

	public static void save(int storyLineId, Character character, List<Enemy> enemiesList) {
		save(storyLineId);
		save(character);
		save(enemiesList);
	}

	public static void save(int storyLineId) {
		try {
			FileOutputStream out = new FileOutputStream(STORYLINE_ID_FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(storyLineId);
			oos.close();
		} catch (Exception e) {
		}
	}

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

	public static void save(List<Enemy> enemiesList) {
		try {
			FileOutputStream out = new FileOutputStream(ENEMIES_FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(enemiesList);
			oos.close();
		} catch (Exception e) {
		}
	}

	public static int getStorylineId() {
		int storylineId = 0;
		try {
			FileInputStream in = new FileInputStream(STORYLINE_ID_FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(in);
			storylineId = (Integer) (ois.readObject());
			in.close();
		} catch (Exception e) {
		}
		return storylineId;
	}

	public static Character loadCharacter() {
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

	@SuppressWarnings("unchecked")
	public static List<Enemy> loadEnemiesList() {
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