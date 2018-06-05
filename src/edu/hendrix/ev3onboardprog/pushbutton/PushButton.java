package edu.hendrix.ev3onboardprog.pushbutton;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class PushButton {
	public static void main(String[] args) {
		LCD.drawString("Ready to drive!", 0, 0);
		while (Button.ESCAPE.isUp()) {
			if (Button.UP.isDown()) {
				Move.FORWARD.shortMove();
			} else if (Button.LEFT.isDown()) {
				Move.LEFT.shortMove();
			} else if (Button.RIGHT.isDown()) {
				Move.RIGHT.shortMove();
			} else {
				Move.allStop();
			}
		}
	}
}
