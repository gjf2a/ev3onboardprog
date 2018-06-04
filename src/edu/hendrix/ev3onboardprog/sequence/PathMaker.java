package edu.hendrix.ev3onboardprog.sequence;

import edu.hendrix.ev3onboardprog.Util;
import lejos.hardware.Button;

public class PathMaker {
	public static void main(String[] args) {
		Program prog = Program.make();
		prog.display();
		while (true) {
			while (Button.ESCAPE.isUp()) {
				prog.checkAndUse(Button.LEFT,  () -> prog.goLeft());
				prog.checkAndUse(Button.RIGHT, () -> prog.goRight());
				prog.checkAndUse(Button.UP,    () -> prog.rotateCurrent());
				prog.checkAndUse(Button.DOWN,  () -> prog.delete());
				prog.checkAndUse(Button.ENTER, () -> prog.addMove());
			}

			prog.execute();
			
			if (Util.isYes("Try again")) {
				prog.display();
			} else {
				break;
			}
		}
	}
}
