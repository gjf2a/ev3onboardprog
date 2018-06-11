package edu.hendrix.ev3onboardprog.colortrack;

import java.io.IOException;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import edu.hendrix.ev3onboardprog.vision.BasicVisionBot;
import edu.hendrix.ev3onboardprog.vision.BinaryImage;
import edu.hendrix.ev3onboardprog.vision.RGB;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.video.YUYVImage;

public class SelectShowFilter extends BasicVisionBot {
	private RGBColorBound2 filter;
	private ColorSelector<RGB> selector;

	public SelectShowFilter(RGBColorBound2 filter) throws IOException {
		super();
		this.filter = filter;
		selector = new ColorSelector<RGB>(filter, RGB.class);
	}

	@Override
	public void proc(YUYVImage img) {
		handleButtons();
		selector.drawBounds();
		BinaryImage bin = new BinaryImage(img, filter);
		bin.drawIgnoring(0, LCD.FONT_WIDTH * 11, 0, LCD.FONT_HEIGHT * 3);
		
	}
	
	public void handleButtons() {
		UIFuncs.checkAndUse(Button.LEFT, () -> {
			selector.prevValue();
		});
		
		UIFuncs.checkAndUse(Button.RIGHT, () -> {
			selector.nextValue();
		});
		
		UIFuncs.checkAndUse(Button.UP, () -> {
			selector.decValue();
		});
		
		UIFuncs.checkAndUse(Button.DOWN, () -> {
			selector.incValue();
		});
	}
}
