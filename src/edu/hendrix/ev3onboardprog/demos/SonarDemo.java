package edu.hendrix.ev3onboardprog.demos;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class SonarDemo {
	public static void main(String[] args) {
		EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S4);
		float[] sample = new float[1];
		while (Button.ESCAPE.isUp()) {
			sonar.getDistanceMode().fetchSample(sample, 0);
			LCD.drawString(String.format("%4.2f     ", sample[0]), 0, 0);
		}
		sonar.close();
	}
}
