package edu.hendrix.ev3onboardprog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;

public class Util {
	public static void checkAndUse(Key button, Runnable action) {
		if (button.isDown()) {
			action.run();
			while (button.isDown()) {}
		}
	}
	
	public static int addMod(int x, int y, int mod) {
		return (x + y + (y < 0 ? mod : 0)) % mod;
	}
	
	public static boolean isYes(String question) {
		LCD.clear();
		Row<Reply> prompt = new Row<>(question + "?", Reply.YES, (r, f) -> r.other());
		prompt.display(1);
		while (Button.ESCAPE.isUp() && Button.ENTER.isUp()) {
			for (Key button: new Key[]{Button.LEFT, Button.RIGHT, Button.UP, Button.DOWN}) {
				checkAndUse(button, () -> {
					prompt.update(true);
					prompt.display(1);
				});
			}
		}
		
		while (Button.ESCAPE.isDown() || Button.ENTER.isDown()) {}
		return prompt.get().isYes();
	}
	
	public static void waitForUser() {
		while (Button.ESCAPE.isUp()) {}
		while (Button.ESCAPE.isDown()) {}
	}
	
	public static void reportAndQuit(Exception exc) {
		LCD.clear();
		LCD.drawString(exc.getMessage(), 0, 0);
		while (Button.ESCAPE.isDown());
		while (Button.ESCAPE.isUp());
		System.exit(1);
	}
	
	/**
	 * Reads in a File and dumps it into a String.
	 * 
	 * @param f
	 * @return a String containing all text from f.
	 * @throws FileNotFoundException
	 */
	public static String fileToString(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		while (s.hasNextLine()) {
			sb.append(s.nextLine() + '\n');
		}
		s.close();
		return sb.toString();
	}
	
	/**
	 * Takes a String and dumps it into a File.
	 * @throws IOException 
	 * 
	 * 
	 */
	public static void stringToFile(File f, String s) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(f));
		p.println(s);
		p.close();
	}
}
