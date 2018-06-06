package edu.hendrix.ev3onboardprog.colortrack;

import java.util.EnumMap;

import edu.hendrix.ev3onboardprog.vision.YUVBand;
import lejos.hardware.video.YUYVImage;

public class SlowColorBound {
	private EnumMap<YUVBand,Bound> bands = new EnumMap<>(YUVBand.class);
	
	public SlowColorBound() {
		for (YUVBand b: YUVBand.values()) {
			bands.put(b, new Bound());
		}
	}
	
	public boolean isOn(YUYVImage img, int x, int y) {
		return bands.entrySet().stream().allMatch(e -> e.getValue().in(e.getKey().get(img, x, y)));
	}
	
	public void minUp(YUVBand b) {
		bands.get(b).minUp();
	}
	
	public void minDown(YUVBand b) {
		bands.get(b).minDown();
	}
	
	public void maxUp(YUVBand b) {
		bands.get(b).maxUp();
	}
	
	public void maxDown(YUVBand b) {
		bands.get(b).maxDown();
	}
	
	public int getMin(YUVBand b) {
		return bands.get(b).getMin();
	}
	
	public int getMax(YUVBand b) {
		return bands.get(b).getMax();
	}
}
