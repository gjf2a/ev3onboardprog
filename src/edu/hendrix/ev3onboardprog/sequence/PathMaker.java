package edu.hendrix.ev3onboardprog.sequence;

import java.io.FileNotFoundException;

import edu.hendrix.ev3onboardprog.StringListView;
import edu.hendrix.ev3onboardprog.Util;
import lejos.hardware.Button;

public class PathMaker {
	public static void main(String[] args) throws FileNotFoundException {
		StoredPaths stored = new StoredPaths();
		Program prog = !stored.isEmpty() && Util.isYes("Use stored program") ? StringListView.selectLoop(stored) : stored.addNew();

		do {
			prog.display();
			while (Button.ESCAPE.isUp()) {
				prog.checkAndUse(Button.LEFT,  () -> prog.goLeft());
				prog.checkAndUse(Button.RIGHT, () -> prog.goRight());
				prog.checkAndUse(Button.UP,    () -> prog.rotateCurrent());
				prog.checkAndUse(Button.DOWN,  () -> prog.delete());
				prog.checkAndUse(Button.ENTER, () -> prog.addMove());
			}
			prog.execute();
		} while (Util.isYes("Try again"));
		
		if (Util.isYes("Save program")) {
			stored.save();
		}
	}
}
