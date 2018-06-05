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
			long frameCount = 0;
			long start = System.currentTimeMillis();
			while (Button.ESCAPE.isUp()) {
				wc.grabFrame(frame);
				frameCount += 1;
				proc(new YUYVImage(frame, WIDTH, HEIGHT));
			}
			long duration = System.currentTimeMillis() - start;
			double secs = duration / 1000.0;
			double fps = frameCount / secs;
			wc.close();
			
			while (Button.ESCAPE.isDown()) {}
			LCD.drawString(frameCount + " frames", 0, 0);
			LCD.drawString(String.format("%5.1f s", secs), 0, 1);
			LCD.drawString(String.format("%4.2f fps", fps), 0, 2);
			
			while (Button.ESCAPE.isUp()) {}
			while (Button.ESCAPE.isDown()) {}
			
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
