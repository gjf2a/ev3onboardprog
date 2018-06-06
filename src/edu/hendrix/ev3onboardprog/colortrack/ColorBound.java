package edu.hendrix.ev3onboardprog.colortrack;

import lejos.hardware.video.YUYVImage;

public interface ColorBound {
	public boolean isOn(YUYVImage img, int x, int y);
}
