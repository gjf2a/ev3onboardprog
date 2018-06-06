package edu.hendrix.ev3onboardprog.reactive;

import edu.hendrix.ev3onboardprog.Util;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class ControllerMaker {
	public static void main(String[] args) {
		ControllerSpecs specs = new ControllerSpecs();
		do {
			specify(specs);
			execute(specs);
		} while (Util.isYes("Try again"));
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
