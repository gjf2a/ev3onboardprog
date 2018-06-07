package edu.hendrix.ev3onboardprog.demos;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightDemoActive {
	public static void main(String[] args) {
		EV3ColorSensor light = new EV3ColorSensor(SensorPort.S3);
		float[] sample = new float[1];
		while (Button.ESCAPE.isUp()) {
			light.getRedMode().fetchSample(sample, 0);
			LCD.drawString(String.format("%4.2f     ", sample[0]), 0, 0);
		}
		light.close();
	}
}
