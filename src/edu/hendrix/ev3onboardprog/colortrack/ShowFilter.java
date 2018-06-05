package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.vision.BasicVisionBot;
import edu.hendrix.ev3onboardprog.vision.BinaryImage;
import lejos.hardware.video.YUYVImage;

public class ShowFilter extends BasicVisionBot {
	private HackColorBound filter;

	public ShowFilter(HackColorBound filter) throws IOException {
		super();
		this.filter = filter;
	}

	@Override
	public void proc(YUYVImage img) {
		BinaryImage bin = new BinaryImage(img, filter);
		bin.draw();
	}

}
