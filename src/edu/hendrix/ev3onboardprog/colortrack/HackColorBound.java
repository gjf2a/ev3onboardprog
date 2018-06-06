package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.vision.Band;
import lejos.hardware.video.YUYVImage;

public class HackColorBound extends MinMaxBound<Band> {
	private int yMin = 0, yMax = MAX, uMin = 0, uMax = MAX, vMin = 0, vMax = MAX;
	
	public HackColorBound(boolean inside) {
		super(inside, INCR, MAX);
	}
	
	public HackColorBound(boolean inside, int yMin, int yMax, int uMin, int uMax, int vMin, int vMax) {
		super(inside, INCR, MAX);
		this.yMin = yMin;
		this.yMax = yMax;
		this.uMin = uMin;
		this.uMax = uMax;
		this.vMin = vMin;
		this.vMax = vMax;
	}
	
	public final static int INCR = 5;
	public final static int MAX = 0xFF;
	
	public boolean isOn(YUYVImage img, int x, int y) {
		int Y = img.getY(x, y) & MAX;
		int U = img.getU(x, y) & MAX;
		int V = img.getV(x, y) & MAX;
		
		return isInside() == (yMin <= Y && Y <= yMax && vMin <= V && V <= vMax && uMin <= U && U <= uMax);
	}
	
	@Override
	public void minUp(Band b) {
		switch (b) {
		case Y: yMin = minPlus(yMin, yMax); break;
		case U: uMin = minPlus(uMin, uMax); break;
		default: vMin = minPlus(vMin, vMax);
		}
	}
	
	@Override
	public void minDown(Band b) {
		switch (b) {
		case Y: yMin = minMinus(yMin); break;
		case U: uMin = minMinus(uMin); break;
		default: vMin = minMinus(vMin);
		}		
	}
	
	@Override
	public void maxUp(Band b) {
		switch (b) {
		case Y: yMax = maxPlus(yMax); break;
		case U: uMax = maxPlus(uMax); break;
		default: vMax = maxPlus(vMax);
		}
	}
	
	@Override
	public void maxDown(Band b) {
		switch (b) {
		case Y: yMax = maxMinus(yMax, yMin); break;
		case U: uMax = maxMinus(uMax, uMin); break;
		default: vMax = maxMinus(vMax, vMin);
		}
	}
	
	@Override
	public int getMin(Band b) {
		switch (b) {
		case Y: return yMin;
		case U: return uMin;
		default: return vMin;
		}
	}
	
	@Override
	public int getMax(Band b) {
		switch (b) {
		case Y: return yMax;
		case U: return uMax;
		default: return vMax;
		}
	}
}
