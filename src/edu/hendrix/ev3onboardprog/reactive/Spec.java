package edu.hendrix.ev3onboardprog.reactive;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.port.Port;

abstract public class Spec {
	private Move move;
	
	public Spec() {
		move = Move.FORWARD;
	}
	
	public Spec(String moveStr) {
		move = Move.valueOf(moveStr);
	}
	
	public Move action() {
		return move;
	}
	
	public void nextAction() {
		move = move.nextChoice();
	}
	
	public void prevAction() {
		move = move.prevChoice();
	}
	
	abstract public SensorRunner makeRunner(Port p);
	abstract public int numChoices();
	abstract public void next(int choice);
	abstract public void prev(int choice);
	abstract public void lcdShow(int row, int highlight);
}
