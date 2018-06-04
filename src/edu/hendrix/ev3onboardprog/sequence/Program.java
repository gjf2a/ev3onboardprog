package edu.hendrix.ev3onboardprog.sequence;

import edu.hendrix.ev3onboardprog.Move;
import edu.hendrix.ev3onboardprog.ScreenSpot;
import edu.hendrix.ev3onboardprog.Util;
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
		moves.forAll(m -> m.longMove());
		Move.allStop();
	}
	
	public String toInstrString() {
		StringBuilder sb = new StringBuilder();
		moves.forAll(m -> sb.append(m.rep()));
		return sb.toString();
	}
	
	public static Program fromInstrString(String src) {
		Program result = new Program();
		int i = 0;
		while (i < src.length()) {
			for (Move candidate: Move.values()) {
				String test = src.substring(i, i + candidate.rep().length());
				if (test.equals(candidate.rep())) {
					result.moves.insertBefore(candidate);
					i += candidate.rep().length();
					break;
				}
			}
			throw new IllegalArgumentException("Did not recognize '" + src.charAt(i) + "'");
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
		Util.checkAndUse(button, () -> {
			action.run();
			display();
		});
	}
}
