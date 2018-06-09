package edu.hendrix.ev3onboardprog.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Util {
	public static int addMod(int x, int y, int mod) {
		return (x + y + (y < 0 ? mod : 0)) % mod;
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
	
	public static void assertion(boolean condition, Supplier<RuntimeException> exceptor) {
		if (!condition) {throw exceptor.get();}
	}
	
	public static void assertArgument(boolean condition, String msg) {
		assertion(condition, () -> new IllegalArgumentException(msg));
	}
	
	public static void assertState(boolean condition, String msg) {
		assertion(condition, () -> new IllegalStateException(msg));
	}	/**
	 * Takes an input containing matching pairs of curly braces ("{}") and returns 
	 * an array of strings where everything in the top level of braces 
	 * (and between braces) is placed in its own string.
	 */
	public static ArrayList<String> debrace(String input) {
		ArrayList<String> result = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int numOpen = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '{') {
				numOpen += 1;
				if (numOpen == 1) {
					if (current.length() > 0) result.add(current.toString());
					current = new StringBuilder();
				} else {
					current.append(input.charAt(i));
				}
			} else if (input.charAt(i) == '}') {
				numOpen -= 1;
				if (numOpen > 0) {
					current.append(input.charAt(i));
				} else {
					result.add(current.toString());
					current = new StringBuilder();
				}
				while (i + 1 < input.length() && Character.isWhitespace(input.charAt(i + 1))) {
					i += 1;
				}
			} else {
				current.append(input.charAt(i));
			}
		}
		if (current.length() > 0) result.add(current.toString());
		return result;
	}
}
