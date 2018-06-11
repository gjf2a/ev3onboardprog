package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;

public class RGBColorTracker implements Runnable {
	private RGBColorBound2 filter = new RGBColorBound2(true);
	
	public void run() {
		//UIFuncs.report(LCD.FONT_WIDTH + "," + LCD.SCREEN_WIDTH);
		try {
			do {
				SelectShowFilter ssfilter = new SelectShowFilter(filter);
				ssfilter.run();
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
		new RGBColorTracker().run();
	}
}
