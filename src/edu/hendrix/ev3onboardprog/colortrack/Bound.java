package edu.hendrix.ev3onboardprog.colortrack;

import java.util.function.IntPredicate;

public class Bound {
	private int min, max;
	
	public final static int INCR = 5;
	
	public Bound() {
		min = 0;
		max = 255;
	}
	
	public boolean in(int value) {
		return min <= value && value <= max;
	}
	
	public int getMin() {return min;}
	
	public int getMax() {return max;}
	
	private int update(int original, IntPredicate condition, int change) {
		int changed = original + change;
		return condition.test(changed) ? changed : original;
	}
	
	public void minUp() {
		min = update(min, x -> x <= max, INCR);
	}
	
	public void minDown() {
		min = update(min, x -> x >= 0, -INCR);
	}
	
	public void maxUp() {
		max = update(max, x -> x <= 255, INCR);
	}
	
	public void maxDown() {
		max = update(max, x -> x >= min, -INCR);
	}
}
