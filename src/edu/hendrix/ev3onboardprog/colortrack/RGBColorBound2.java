package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.vision.RGB;
import lejos.hardware.video.YUYVImage;

public class RGBColorBound2 extends MinMaxBound<RGB> {
	public static final int MAX = 0xFF;
	
	public static int clamp(int value) {
		return Math.min(MAX, Math.max(0, value));
	}
	
	private int rMin = 0, rMax = MAX, gMin = 0, gMax = MAX, bMin = 0, bMax = MAX;
	
	public RGBColorBound2(boolean inside) {
		super(inside, MAX / 10, MAX);
	}
	
	static int getR(int c, int e) {
		return clamp((298*c + 409*e + 128) >> 8);
	}
	
	static int getG(int c, int d, int e) {
		return clamp((298*c - 100*d - 208*e + 128) >> 8);
	}
	
	static int getB(int c, int d) {
		return clamp((298*c + 516*d + 128) >> 8);
	}
	
	public boolean isOn(YUYVImage img, int x, int y) {
		int c = (img.getY(x, y) & MAX) - 16;
		int d = (img.getU(x, y) & MAX) - 128;
		int e = (img.getV(x, y) & MAX) - 128;
		
		int r = getR(c, e);
		int g = getG(c, d, e);
		int b = getB(c, d);
		
		return isInside() == (rMin <= r && r <= rMax && gMin <= g && g <= gMax && bMin <= b && b <= bMax);
	}
	
	@Override
	public void minUp(RGB rgb) {
		switch (rgb) {
		case RED:   rMin = minPlus(rMin, rMax); break;
		case GREEN: gMin = minPlus(gMin, gMax); break;
		default:    bMin = minPlus(bMin, bMax);
		}
	}
	
	@Override
	public void minDown(RGB rgb) {
		switch (rgb) {
		case RED:   rMin = minMinus(rMin); break;
		case GREEN: gMin = minMinus(gMin); break;
		default:    bMin = minMinus(bMin);
		}
	}
	
	@Override
	public void maxUp(RGB rgb) {
		switch (rgb) {
		case RED:   rMax = maxPlus(rMax); break;
		case GREEN: gMax = maxPlus(gMax); break;
		default:    bMax = maxPlus(bMax); 
		}
	}
	
	@Override
	public void maxDown(RGB rgb) {
		switch (rgb) {
		case RED:   rMax = maxMinus(rMax, rMin); break;
		case GREEN: gMax = maxMinus(gMax, gMin); break;
		default:    bMax = maxMinus(bMax, bMin); 
		}
	}

	@Override
	public int getMin(RGB rgb) {
		switch (rgb) {
		case RED:   return rMin;
		case GREEN: return gMin;
		default:    return bMin;
		}
	}

	@Override
	public int getMax(RGB rgb) {
		switch (rgb) {
		case RED:   return rMax;
		case GREEN: return gMax;
		default:    return bMax;
		}
	}
}
