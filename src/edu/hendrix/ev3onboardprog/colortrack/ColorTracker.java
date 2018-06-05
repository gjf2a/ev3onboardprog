package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.Util;

public class ColorTracker implements Runnable {
	private HackColorBound filter = new HackColorBound();
	
	public void run() {
		try {
			do {
				ColorSelector selector = new ColorSelector(filter);
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
		new ColorTracker().run();
	}
}
