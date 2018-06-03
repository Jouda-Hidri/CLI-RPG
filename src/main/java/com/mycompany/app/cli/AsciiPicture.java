package com.mycompany.app.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AsciiPicture {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public void display(String path) {
		String xWingAscii = new String();
		try {
			xWingAscii = readFile(path);
		} catch (IOException e) {
		}
		System.out.println(xWingAscii);

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
