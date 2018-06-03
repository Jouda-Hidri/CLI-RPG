package com.mycompany.app.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AsciiPicture {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	private static final String DARTH_VADER = "/darth-vader.txt";
	private static final String X_WING = "/x-wing.txt";
	private static final String WALKER_ATTACKING = "/walker-attacking.txt";
	private static final String HOTH = "/hoth-left.txt";
	private static final String R2D2_LEIA = "/R2D2-Leia.txt";
	private static final String TIE = "/tie.txt";
	private static final String FALCON = "/falcon.txt";
	private static final String LEIA = "/leia.txt";

	/** A map of path and pictures */
	Map<String, String> asciiPictures = new HashMap<String, String>();

	public AsciiPicture() {
		asciiPictures.put("character", new String());
		asciiPictures.put("attacked", new String());
		asciiPictures.put("attacking", new String());
		asciiPictures.put("target", new String());
	}

	public AsciiPicture(String character, String attacked, String attacking, String target) {
		asciiPictures.put("character", character);
		asciiPictures.put("attacked", attacked);
		asciiPictures.put("attacking", attacking);
		asciiPictures.put("target", target);
	}

	public AsciiPicture(int topic) {
		if (topic == 1) {
			asciiPictures.put("character", DARTH_VADER);
			asciiPictures.put("attacked", X_WING);
			asciiPictures.put("attacking", WALKER_ATTACKING);
			asciiPictures.put("target", HOTH);
		}
		if (topic == 2) {
			asciiPictures.put("character", R2D2_LEIA);
			asciiPictures.put("attacked", TIE);
			asciiPictures.put("attacking", FALCON);
			asciiPictures.put("target", LEIA);
		}
	}

	public void display(String name) {
		String path = asciiPictures.get(name);
		String ascii = new String();
		try {
			ascii = readFile(path);
		} catch (IOException e) {
		}
		System.out.println(ascii);
	}

	public String readFile(String fileName) throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(fileName);
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}
}
