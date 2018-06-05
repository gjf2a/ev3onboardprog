package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.vision.Band;
import lejos.hardware.video.YUYVImage;

public class HackColorBound {
	private int yMin = 0, yMax = MAX, uMin = 0, uMax = MAX, vMin = 0, vMax = MAX;
	
	public HackColorBound() {}
	
	public final static int INCR = 5;
	public final static int MAX = 0xFF;
	
	public boolean isOn(YUYVImage img, int x, int y) {
		int Y = img.getY(x, y) & MAX;
		int U = img.getU(x, y) & MAX;
		int V = img.getV(x, y) & MAX;
		
		return yMin <= Y && Y <= yMax && vMin <= V && V <= vMax && uMin <= U && U <= uMax;
	}
	
	private int minPlus(int min, int max) {
		return Math.min(min + INCR, max);
	}
	
	private int minMinus(int min) {
		return Math.max(min - INCR, 0);
	}
	
	private int maxPlus(int max) {
		return Math.min(max + INCR, MAX);
	}
	
	private int maxMinus(int max, int min) {
		return Math.max(max - INCR, min);
	}
	
	public void minUp(Band b) {
		switch (b) {
		case Y: yMin = minPlus(yMin, yMax); break;
		case U: uMin = minPlus(uMin, uMax); break;
		default: vMin = minPlus(vMin, vMax);
		}
	}
	
	public void minDown(Band b) {
		switch (b) {
		case Y: yMin = minMinus(yMin); break;
		case U: uMin = minMinus(uMin); break;
		default: vMin = minMinus(vMin);
		}		
	}
	
	public void maxUp(Band b) {
		switch (b) {
		case Y: yMax = maxPlus(yMax); break;
		case U: uMax = maxPlus(uMax); break;
		default: vMax = maxPlus(vMax);
		}
	}
	
	public void maxDown(Band b) {
		switch (b) {
		case Y: yMax = maxMinus(yMax, yMin); break;
		case U: uMax = maxMinus(uMax, uMin); break;
		default: vMax = maxMinus(vMax, vMin);
		}
	}
	
	public int getMin(Band b) {
		switch (b) {
		case Y: return yMin;
		case U: return uMin;
		default: return vMin;
		}
	}
	
	public int getMax(Band b) {
		switch (b) {
		case Y: return yMax;
		case U: return uMax;
		default: return vMax;
		}
	}
}
