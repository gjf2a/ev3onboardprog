package edu.hendrix.ev3onboardprog.sequence;

import java.io.FileNotFoundException;

import edu.hendrix.ev3onboardprog.ui.StringListView;
import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class PathMaker {
	public static void main(String[] args) throws FileNotFoundException {
		LCD.drawString("Loading...", 0, 0);
		StoredPaths stored = new StoredPaths();
		Program prog = !stored.isEmpty() && UIFuncs.isYes("Use stored") ? StringListView.selectLoop(stored) : stored.addNew();

		do {
			prog.display();
			while (Button.ESCAPE.isUp()) {
				prog.checkAndUse(Button.LEFT,  () -> prog.goLeft());
				prog.checkAndUse(Button.RIGHT, () -> prog.goRight());
				prog.checkAndUse(Button.UP,    () -> prog.rotateCurrent());
				prog.checkAndUse(Button.DOWN,  () -> prog.delete());
				prog.checkAndUse(Button.ENTER, () -> prog.addMove());
			}
			if (UIFuncs.isYes("Run program")) {
				prog.execute();
			}
		} while (UIFuncs.isYes("Try again"));
		
		if (UIFuncs.isYes("Save program")) {
			stored.save();
		}
	}
}
