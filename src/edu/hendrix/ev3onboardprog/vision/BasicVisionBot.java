package edu.hendrix.ev3onboardprog.vision;

import java.io.IOException;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.video.Video;
import lejos.hardware.video.YUYVImage;

abstract public class BasicVisionBot implements Runnable {
	public final static int WIDTH = 160, HEIGHT = 120;
			
	private byte[] frame;
	private Video wc;

	public BasicVisionBot() throws IOException {
		wc = BrickFinder.getDefault().getVideo();
		wc.open(WIDTH,HEIGHT);
		frame = wc.createFrame();
	}
	
	public void run() {
		try {
			while (Button.ESCAPE.isUp()) {
				wc.grabFrame(frame);
				proc(new YUYVImage(frame, WIDTH, HEIGHT));
			}
			wc.close();
		} catch (IOException exc) {
			LCD.clear();
			LCD.drawString("Error with camera", 0, 0);
			LCD.drawString("Press ESCAPE to exit", 0, 0);
			while (Button.ESCAPE.isUp()) {}
			System.exit(1);
		}
	}
	
	abstract public void proc(YUYVImage img);
}
