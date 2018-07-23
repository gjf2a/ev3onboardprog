package edu.hendrix.ev3onboardprog.pushbutton;

import edu.hendrix.ev3onboardprog.Move;
import edu.hendrix.ev3onboardprog.TrackMove;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class PushButton {
	public static void main(String[] args) {
		LCD.drawString("Ready to drive!", 0, 0);
		while (Button.ESCAPE.isUp()) {
			if (Button.UP.isDown()) {
				TrackMove.FORWARD.move();
			} else if (Button.LEFT.isDown()) {
				TrackMove.LEFT.move();
			} else if (Button.RIGHT.isDown()) {
				TrackMove.RIGHT.move();
			} else if (Button.DOWN.isDown()) {
				TrackMove.BACK.move();
			} else {
				Move.allStop();
			}
		}
	}
}
