package edu.hendrix.ev3onboardprog.util;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class CyclesPerSecond {
	private long start = 0, duration = 0;
	private int cycles = 0;
	
	public void bump() {
		cycles += 1;
	}
	
	public void stop() {
		duration += System.currentTimeMillis() - start;
	}
	
	public void start() {
		start = System.currentTimeMillis();
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
		LCD.drawString(String.format("%5.2f hz", hz()), 0, 2);
		while (Button.ESCAPE.isUp()) {}
		while (Button.ESCAPE.isDown()) {}
	}
}
