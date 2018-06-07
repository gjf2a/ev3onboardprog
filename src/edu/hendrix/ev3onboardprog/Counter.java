package edu.hendrix.ev3onboardprog;

public class Counter {
	private int n;
	
	public Counter() {}
	
	public void reset() {
		n = 0;
	}
	
	public void bump() {
		n += 1;
	}
	
	public int get() {
		return n;
	}
}
