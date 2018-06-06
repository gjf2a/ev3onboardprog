package edu.hendrix.ev3onboardprog.vision;

import java.util.BitSet;

import edu.hendrix.ev3onboardprog.colortrack.YUVColorBound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.video.YUYVImage;

public class BinaryImage {
	private BitSet bits;
	private int width, height;
	
	public BinaryImage(YUYVImage img, YUVColorBound filter) {
		height = img.getHeight();
		width = img.getWidth();
		bits = new BitSet(img.getHeight() * img.getWidth());
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				bits.set(ind(x, y), filter.isOn(img, x, y));
			}
		}
	}
	
	public boolean on(int x, int y) {
		return bits.get(ind(x, y));
	}
	
	public Point centroid() {
		int xTotal = 0, yTotal = 0, numOn = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (on(x, y)) {
					xTotal += x;
					yTotal += y;
					numOn += 1;
				}
			}
		}
		return new Point(xTotal / numOn, yTotal / numOn);
	}
	
	int ind(int x, int y) {
		return y * width + x;
	}
	
	boolean bitFor(int lcdX, int lcdY) {
		int px = lcdX * width / LCD.SCREEN_WIDTH;
		int py = lcdY * height / LCD.SCREEN_HEIGHT;
		return bits.get(ind(px, py));
	}
	
	public void draw() {
		for (int y = 0; y < LCD.SCREEN_HEIGHT; y++) {
			for (int x = 0; x < LCD.SCREEN_WIDTH; x++) {
				LCD.setPixel(x, y, bits.get(ind(x, y)) ? 1 : 0);
			}
		}
	}
}
