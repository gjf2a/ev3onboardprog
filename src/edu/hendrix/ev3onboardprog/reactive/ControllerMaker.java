package edu.hendrix.ev3onboardprog.reactive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.util.Util;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class ControllerMaker {
	public static void main(String[] args) {
		ControllerSpecs specs = initialize();
		do {
			specify(specs);
			execute(specs);
		} while (UIFuncs.isYes("Try again"));
		save(specs);	
	}
	
	public static void save(ControllerSpecs specs) {
		if (UIFuncs.isYes("Save")) {
			try {
				Util.stringToFile(new File(FILENAME), specs.toString());
				LCD.drawString("Save complete", 0, 0);
			} catch (IOException e) {
				LCD.clear();
				LCD.drawString("Save failed", 0, 0);
			}
			UIFuncs.waitForUser();
		}
	}
	
	public static final String FILENAME = "controller.txt";
	
	public static ControllerSpecs initialize() {
		File fin = new File(FILENAME);
		if (fin.exists()) {
			try {
				String src = Util.fileToString(fin);
				return ControllerSpecs.fromString(src);
			} catch (FileNotFoundException | IllegalArgumentException exc) {
				// Intentionally left blank
			}
		}
		return new ControllerSpecs();
	}
	
	public static void specify(ControllerSpecs specs) {
		specs.display();
		do {
			specs.checkAndUse(Button.LEFT,  () -> specs.prevTopic());
			specs.checkAndUse(Button.RIGHT, () -> specs.nextTopic());
			specs.checkAndUse(Button.UP,    () -> specs.topicPrev());
			specs.checkAndUse(Button.DOWN,  () -> specs.topicNext());
		} while (Button.ESCAPE.isUp());
		while (Button.ESCAPE.isDown()) {}
	}
	
	public static void execute(ControllerSpecs specs) {
		try {
			LCD.clear();
			LCD.drawString("Starting up...", 0, 0);
			Controller c = specs.makeController();
			c.loop();
		} catch (IllegalArgumentException exc) {
			LCD.clear();
			LCD.drawString(exc.getMessage(), 0, 3);
			while (Button.ESCAPE.isUp()) {}
			while (Button.ESCAPE.isDown()) {}
		}
	}
}
