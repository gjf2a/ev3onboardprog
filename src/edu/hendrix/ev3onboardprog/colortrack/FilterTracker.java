package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.Move;
import edu.hendrix.ev3onboardprog.vision.BasicVisionBot;
import edu.hendrix.ev3onboardprog.vision.BinaryImage;
import edu.hendrix.ev3onboardprog.vision.Point;
import lejos.hardware.video.YUYVImage;

public class FilterTracker extends BasicVisionBot {
	private HackColorBound filter;
	
	public final static double STRAIGHT_FRACTION = 0.20;
	public final static double TURN_FRACTION = (1.0 - STRAIGHT_FRACTION) / 2;
	public final static int TURN_PIXELS = (int)(BasicVisionBot.WIDTH * TURN_FRACTION);
	public final static int GO_LEFT = TURN_PIXELS;
	public final static int GO_RIGHT = BasicVisionBot.WIDTH - TURN_PIXELS;

	public FilterTracker(HackColorBound filter) throws IOException {
		super();
		this.filter = filter;
	}

	@Override
	public void proc(YUYVImage img) {
		BinaryImage bin = new BinaryImage(img, filter);
		bin.draw();
		Point centroid = bin.centroid();
		if (centroid.getX() < GO_LEFT) {
			Move.LEFT.shortMove();
		} else if (centroid.getX() > GO_RIGHT) {
			Move.RIGHT.shortMove();
		} else {
			Move.FORWARD.shortMove();
		}
	}
}
