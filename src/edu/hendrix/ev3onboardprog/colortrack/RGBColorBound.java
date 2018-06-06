package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.vision.RGB;

public class RGBColorBound extends MinMaxBound<RGB> {
	public static final int MAX = 10;
	public static final int CONVERT = 255 / MAX;
	
	private int rMin = 0, rMax = 10, gMin = 0, gMax = 10, bMin = 0, bMax = 10;
	private boolean inside;
	
	public HackColorBound makeYUV() {
		return new HackColorBound(inside, 
				getY(rMin, gMin, bMin), 
				getY(rMax, gMax, bMax),
				getU(rMin, gMin, bMin),
				getU(rMax, gMax, bMax),
				getV(rMin, gMin, bMin),
				getV(rMax, gMax, bMax));
	}
	
	// From https://en.wikipedia.org/wiki/YUV
	
	private static int formula(int r, int g, int b, int rc, int gc, int bc, int add) {
		return ((CONVERT * (rc * r + gc * gc + bc * b) + 128) >> 8) + add;
	}
	
	public static int getY(int r, int g, int b) {
		return formula(r, g, b, 66, 129, 25, 16);
	}
	
	public static int getU(int r, int g, int b) {
		return formula(r, g, b, -38, -74, 112, 128);
	}
	
	public static int getV(int r, int g, int b) {
		return formula(r, g, b, 112, -94, -18, 128);
	}
	
	public RGBColorBound(boolean inside) {
		super(inside, 1, MAX);
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
