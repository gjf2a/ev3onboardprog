package edu.hendrix.ev3onboardprog.colortrack;

import java.io.File;
import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.util.Util;

public class RGBColorTracker implements Runnable {
	private RGBColorBound2 filter = new RGBColorBound2(true);
	
	public static String FILENAME = "rgb.txt";
	
	public void run() {
		File saved = new File(FILENAME);
		if (saved.exists()) {
			try {
				String contents = Util.fileToString(saved);
				filter = new RGBColorBound2(contents);
			} catch (IOException e) {
				filter = new RGBColorBound2(true);
			}
		}
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
		
		if (UIFuncs.isYes("Save color")) {
			try {
				Util.stringToFile(new File(FILENAME), filter.toString());
			} catch (IOException e) {
				UIFuncs.report("Save failed");
			}
		}
	}
	
	public static void main(String[] args) {
		new RGBColorTracker().run();
	}
}
