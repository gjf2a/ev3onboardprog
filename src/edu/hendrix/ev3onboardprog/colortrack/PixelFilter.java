package edu.hendrix.ev3onboardprog.colortrack;

import lejos.hardware.video.YUYVImage;

public interface PixelFilter {
	public boolean isOn(YUYVImage img, int x, int y);
}
