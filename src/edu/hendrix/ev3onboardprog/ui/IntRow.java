package edu.hendrix.ev3onboardprog.ui;

public class IntRow extends Row<Integer> {
	public IntRow(String prompt, int start, int update) {
		super(prompt, start, (i, f) -> i + (f ? 1 : -1)  * update);
	}
}
