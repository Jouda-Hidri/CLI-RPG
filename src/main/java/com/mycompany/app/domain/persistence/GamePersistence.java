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
	private static String TOPIC_FILE_PATH = "topic.md";

	public static void save(int topicId, Character character, List<Enemy> enemiesList) {
		save(topicId);
		save(character);
		save(enemiesList);
	}

	public static void save(int topicId) {
		try {
			FileOutputStream out = new FileOutputStream(TOPIC_FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(topicId);
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

	public static int getTopic() {
		int topic = 0;
		try {
			FileInputStream in = new FileInputStream(TOPIC_FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(in);
			topic = (Integer) (ois.readObject());
			in.close();
		} catch (Exception e) {
		}
		return topic;
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