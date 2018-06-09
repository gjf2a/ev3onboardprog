package edu.hendrix.ev3onboardprog.ui;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;

public class UIFuncs {

	public static void checkAndUse(Key button, Runnable action) {
		if (button.isDown()) {
			action.run();
			while (button.isDown()) {}
		}
	}

	public static boolean isYes(String question) {
		LCD.clear();
		Row<Reply> prompt = new Row<>(question + "?", Reply.YES, (r, f) -> r.other());
		prompt.display(1);
		while (Button.ESCAPE.isUp() && Button.ENTER.isUp()) {
			for (Key button: new Key[]{Button.LEFT, Button.RIGHT, Button.UP, Button.DOWN}) {
				checkAndUse(button, () -> {
					prompt.update(true);
					prompt.display(1);
				});
			}
		}
		
		while (Button.ESCAPE.isDown() || Button.ENTER.isDown()) {}
		return prompt.get().isYes();
	}

	public static void waitForUser() {
		while (Button.ESCAPE.isUp()) {}
		while (Button.ESCAPE.isDown()) {}
	}

	public static void reportAndQuit(Exception exc) {
		LCD.clear();
		LCD.drawString(exc.getMessage(), 0, 0);
		while (Button.ESCAPE.isDown());
		while (Button.ESCAPE.isUp());
		System.exit(1);
	}

}
