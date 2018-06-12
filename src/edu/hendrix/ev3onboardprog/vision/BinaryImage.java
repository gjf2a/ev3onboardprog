package edu.hendrix.ev3onboardprog.vision;

import java.util.BitSet;

import edu.hendrix.ev3onboardprog.colortrack.PixelFilter;
import lejos.hardware.lcd.LCD;
import lejos.hardware.video.YUYVImage;

public class BinaryImage {
	private BitSet bits;
	private int width, height;
	
	public BinaryImage(YUYVImage img, PixelFilter filter) {
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
		return numOn == 0 ? new Point(width / 2, height / 2) : new Point(xTotal / numOn, yTotal / numOn);
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
				LCD.setPixel(x, y, bitFor(x, y) ? 1 : 0);
			}
		}
	}
	
	public void drawIgnoring(int minX, int maxX, int minY, int maxY) {
		for (int y = 0; y < LCD.SCREEN_HEIGHT; y++) {
			for (int x = 0; x < LCD.SCREEN_WIDTH; x++) {
				if (x < minX || x > maxX || y < minY || y > maxY) {
					LCD.setPixel(x, y, bitFor(x, y) ? 1 : 0);
				}
			}
		}
	}
}
