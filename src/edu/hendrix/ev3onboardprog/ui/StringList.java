package edu.hendrix.ev3onboardprog.ui;

public interface StringList<T> {
	public int size();
	public T get(int i);
	
	default public int maxWidthFrom(int start, int end) {
		int max = 0;
		for (int i = start; i <= end; i++) {
			max = Math.max(max, get(i).toString().length());
		}
		return max;
	}
}
