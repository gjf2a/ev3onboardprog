package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.vision.YUVBand;

public class YUVColorTracker implements Runnable {
	private YUVColorBound filter = new YUVColorBound(true);
	
	public void run() {
		try {
			do {
				ColorSelector<YUVBand> selector = new ColorSelector<>(filter, YUVBand.class);
				selector.loop();
				if (UIFuncs.isYes("Check color")) {
					ShowFilter tracker = new ShowFilter(filter);
					tracker.run();
				}
				if (UIFuncs.isYes("Run robot")) {
					FilterTracker tracker = new FilterTracker(filter);
					tracker.run();
				}
			} while (UIFuncs.isYes("Try again"));
		} catch (IOException exc) {
			UIFuncs.reportAndQuit(exc);
		}
	}
	
	public static void main(String[] args) {
		new YUVColorTracker().run();
	}
}
