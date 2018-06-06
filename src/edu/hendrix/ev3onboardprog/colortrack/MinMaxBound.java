package edu.hendrix.ev3onboardprog.colortrack;

abstract public class MinMaxBound<E extends Enum<E>> {
	private int incr, max_limit;
	private boolean inside;
	
	public MinMaxBound(boolean inside, int incr, int max_limit) {
		this.inside = inside;
		this.incr = incr;
		this.max_limit = max_limit;
	}
	
	public boolean isInside() {return inside;}
	
	protected int minPlus(int min, int max) {
		return Math.min(min + incr, max);
	}
	
	protected int minMinus(int min) {
		return Math.max(min - incr, 0);
	}
	
	protected int maxPlus(int max) {
		return Math.min(max + incr, max_limit);
	}
	
	protected int maxMinus(int max, int min) {
		return Math.max(max - incr, min);
	}

	abstract public void minUp(E value);
	abstract public void minDown(E value);
	abstract public void maxUp(E value);
	abstract public void maxDown(E value);
	
	abstract public int getMin(E value);
	abstract public int getMax(E value);
}
