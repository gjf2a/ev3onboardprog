package edu.hendrix.ev3onboardprog;

import lejos.hardware.lcd.LCD;

public class ScreenSpot {
	private int x, y;
	
	final public static int COLS = LCD.SCREEN_WIDTH / LCD.FONT_WIDTH;
	final public static int ROWS = LCD.SCREEN_HEIGHT / LCD.FONT_HEIGHT;
	
	public ScreenSpot() {
		x = y = 0;
	}
	
	public void plot(String s, boolean invert) {
		LCD.drawString(s, x, y, invert);
		advance();
	}
	
	public void advance() {
		x += 1;
		if (x == COLS) {
			x = 0;
			y += 1;
			if (y == ROWS) {
				y -= 1;
				LCD.scroll();
			}
		}
	}
}
