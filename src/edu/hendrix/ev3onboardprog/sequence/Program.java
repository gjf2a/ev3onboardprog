package edu.hendrix.ev3onboardprog.sequence;

import edu.hendrix.ev3onboardprog.Move;
import edu.hendrix.ev3onboardprog.ui.ScreenSpot;
import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;

public class Program {
	private ListZipper<Move> moves = new ListZipper<>();
	
	private Program() {}
	
	public static Program make() {
		Program p = new Program();
		p.addMove();
		return p;
	}
	
	public void goLeft() {
		if (!moves.atStart())
			moves.moveLeft();
	}
	
	public void goRight() {
		if (!moves.atEnd())
			moves.moveRight();
	}
	
	public void rotateCurrent() {
		moves.replaceBefore(moves.getBefore().nextChoice());
	}
	
	public void delete() {
		if (!moves.atStart() && moves.size() > 1) 
			moves.backspace();
	}
	
	public void addMove() {
		moves.insertBefore(Move.FORWARD);
	}
	
	public void execute() {
		while (!moves.atStart()) {moves.moveLeft();}
		do {
			moves.moveRight();
			display();
			moves.getBefore().longMove();
		} while (!moves.atEnd());
		Move.allStop();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		moves.forAll(m -> sb.append(m.rep()));
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public static Program fromInstrString(String src) {
		Program result = new Program();
		int i = 0;
		while (i < src.length()) {
			boolean found = false;
			for (Move candidate: Move.values()) {
				String test = src.substring(i, i + candidate.rep().length());
				if (test.equals(candidate.rep())) {
					result.moves.insertBefore(candidate);
					i += candidate.rep().length();
					found = true;
					break;
				}
			}
			if (!found) {
				throw new IllegalArgumentException("Did not recognize '" + src.charAt(i) + "'");
			}
		}
		return result;
	}
	
	public void display() {
		LCD.clear();
		ScreenSpot spot = new ScreenSpot();
		if (!moves.atStart()) {
			moves.moveLeft();
			moves.allBefore(m -> spot.plot(m.rep(), false));
			moves.moveRight();
			spot.plot(moves.getBefore().rep(), true);
		}
		moves.allAfter(m -> spot.plot(m.rep(), false));
	}
	
	public void checkAndUse(Key button, Runnable action) {
		UIFuncs.checkAndUse(button, () -> {
			action.run();
			display();
		});
	}
}
