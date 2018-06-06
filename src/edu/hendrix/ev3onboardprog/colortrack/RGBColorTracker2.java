package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.Util;
import edu.hendrix.ev3onboardprog.vision.RGB;
import edu.hendrix.ev3onboardprog.vision.YUVBand;

public class RGBColorTracker2 implements Runnable {
	private RGBColorBound2 filter = new RGBColorBound2(true);
	
	public void run() {
		try {
			do {
				ColorSelector<RGB> selector = new ColorSelector<>(filter, RGB.class);
				selector.loop();
				if (Util.isYes("Check color")) {
					ShowFilter tracker = new ShowFilter(filter);
					tracker.run();
				}
				if (Util.isYes("Run robot")) {
					FilterTracker tracker = new FilterTracker(filter);
					tracker.run();
				}
			} while (Util.isYes("Try again"));
		} catch (IOException exc) {
			Util.reportAndQuit(exc);
		}
	}
	
	public static void main(String[] args) {
		new RGBColorTracker2().run();
	}
}
