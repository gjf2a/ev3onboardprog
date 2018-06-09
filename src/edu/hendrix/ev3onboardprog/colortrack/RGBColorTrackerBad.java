package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.vision.RGB;

public class RGBColorTrackerBad implements Runnable {
	private RGBColorBound1 filter = new RGBColorBound1(true);
	
	public void run() {
		try {
			do {
				ColorSelector<RGB> selector = new ColorSelector<>(filter, RGB.class);
				selector.loop();
				if (UIFuncs.isYes("Check color")) {
					ShowFilter tracker = new ShowFilter(filter.makeYUV());
					tracker.run();
				}
				if (UIFuncs.isYes("Run robot")) {
					FilterTracker tracker = new FilterTracker(filter.makeYUV());
					tracker.run();
				}
			} while (UIFuncs.isYes("Try again"));
		} catch (IOException exc) {
			UIFuncs.reportAndQuit(exc);
		}
	}
	
	public static void main(String[] args) {
		new RGBColorTrackerBad().run();
	}
}
