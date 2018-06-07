package edu.hendrix.ev3onboardprog;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class CyclesPerSecond {
	private long start, duration;
	private int cycles = 0;
	
	public CyclesPerSecond() {
		start = System.currentTimeMillis();
	}
	
	public void bump() {
		cycles += 1;
	}
	
	public void stop() {
		duration = System.currentTimeMillis() - start;
	}
	
	public long duration() {
		return duration;
	}
	
	public double seconds() {
		return duration / 1000.0;
	}
	
	public int frames() {
		return cycles;
	}
	
	public double hz() {
		return cycles / seconds();
	}
	
	public void display() {
		LCD.clear();
		LCD.drawString(cycles + " frames", 0, 0);
		LCD.drawString(String.format("%5.1f s", seconds()), 0, 1);
		LCD.drawString(String.format("%5.2f fps", hz()), 0, 2);
		while (Button.ESCAPE.isUp()) {}
		while (Button.ESCAPE.isDown()) {}
	}
}
