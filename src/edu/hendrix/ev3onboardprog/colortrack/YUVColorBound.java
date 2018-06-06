package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.vision.YUVBand;
import lejos.hardware.video.YUYVImage;

public class YUVColorBound extends MinMaxBound<YUVBand> implements ColorBound {
	private int yMin = 0, yMax = MAX, uMin = 0, uMax = MAX, vMin = 0, vMax = MAX;
	
	public YUVColorBound(boolean inside) {
		super(inside, INCR, MAX);
	}
	
	public YUVColorBound(boolean inside, int yMin, int yMax, int uMin, int uMax, int vMin, int vMax) {
		super(inside, INCR, MAX);
		this.yMin = yMin;
		this.yMax = yMax;
		this.uMin = uMin;
		this.uMax = uMax;
		this.vMin = vMin;
		this.vMax = vMax;
		//Logger.EV3Log.format("Y: (%d,%d) U: (%d,%d) V: (%d,%d)", yMin, yMax, uMin, uMax, vMin, vMax);
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
	public void minUp(YUVBand b) {
		switch (b) {
		case Y: yMin = minPlus(yMin, yMax); break;
		case U: uMin = minPlus(uMin, uMax); break;
		default: vMin = minPlus(vMin, vMax);
		}
	}
	
	@Override
	public void minDown(YUVBand b) {
		switch (b) {
		case Y: yMin = minMinus(yMin); break;
		case U: uMin = minMinus(uMin); break;
		default: vMin = minMinus(vMin);
		}		
	}
	
	@Override
	public void maxUp(YUVBand b) {
		switch (b) {
		case Y: yMax = maxPlus(yMax); break;
		case U: uMax = maxPlus(uMax); break;
		default: vMax = maxPlus(vMax);
		}
	}
	
	@Override
	public void maxDown(YUVBand b) {
		switch (b) {
		case Y: yMax = maxMinus(yMax, yMin); break;
		case U: uMax = maxMinus(uMax, uMin); break;
		default: vMax = maxMinus(vMax, vMin);
		}
	}
	
	@Override
	public int getMin(YUVBand b) {
		switch (b) {
		case Y: return yMin;
		case U: return uMin;
		default: return vMin;
		}
	}
	
	@Override
	public int getMax(YUVBand b) {
		switch (b) {
		case Y: return yMax;
		case U: return uMax;
		default: return vMax;
		}
	}
}
