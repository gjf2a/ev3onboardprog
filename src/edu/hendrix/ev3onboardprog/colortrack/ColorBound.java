package edu.hendrix.ev3onboardprog.colortrack;

import java.util.EnumMap;

import edu.hendrix.ev3onboardprog.vision.Band;
import lejos.hardware.video.YUYVImage;

public class ColorBound {
	private EnumMap<Band,Bound> bands = new EnumMap<>(Band.class);
	
	public ColorBound() {
		for (Band b: Band.values()) {
			bands.put(b, new Bound());
		}
	}
	
	public boolean isOn(YUYVImage img, int x, int y) {
		return bands.entrySet().stream().allMatch(e -> e.getValue().in(e.getKey().get(img, x, y)));
	}
	
	public void minUp(Band b) {
		bands.get(b).minUp();
	}
	
	public void minDown(Band b) {
		bands.get(b).minDown();
	}
	
	public void maxUp(Band b) {
		bands.get(b).maxUp();
	}
	
	public void maxDown(Band b) {
		bands.get(b).maxDown();
	}
	
	public int getMin(Band b) {
		return bands.get(b).getMin();
	}
	
	public int getMax(Band b) {
		return bands.get(b).getMax();
	}
}
