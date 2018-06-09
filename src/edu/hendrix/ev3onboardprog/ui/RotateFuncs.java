package edu.hendrix.ev3onboardprog.ui;

import edu.hendrix.ev3onboardprog.util.Util;

public class RotateFuncs {
	public static <E extends Enum<E>> E nextChoice(E en, E[] vals) {
		return advChoice(1, en, vals);
	}
	
	public static <E extends Enum<E>> E prevChoice(E en, E[] vals) {
		return advChoice(-1, en, vals);
	}
	
	public static <E extends Enum<E>> E advChoice(int dir, E en, E[] vals) {
		//@SuppressWarnings("unchecked")
		//E[] vals = (E[])en.getClass().getEnumConstants();
		return advChoice(dir, vals, en.ordinal());
	}
	
	public static <T> int advIndex(int dir, int length, int index) {
		return Util.addMod(index, (dir < 0 ? -1 : 1), length);
	}
	
	public static <T> int nextIndex(int length, int index) {
		return advIndex(1, length, index);
	}
	
	public static <T> int prevIndex(int length, int index) {
		return advIndex(-1, length, index);
	}
	
	public static <T> T advChoice(int dir, T[] vals, int index) {
		return vals[advIndex(dir, vals.length, index)];
	}
}
