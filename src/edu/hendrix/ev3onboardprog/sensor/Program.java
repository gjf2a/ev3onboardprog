package edu.hendrix.ev3onboardprog.sensor;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Program {
	private float threshold;
	private Move below, above;
	
	public Program(int threshold, Move below, Move above) {
		this.threshold = (float)(threshold / 100.0);
		this.below = below;
		this.above = above;
	}
	
	public void execute() {
		EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(SensorPort.S4);
		sonar.enable();
		float[] sample = new float[1];
		while (Button.ESCAPE.isUp()) {
			sonar.getDistanceMode().fetchSample(sample, 0);
			if (sample[0] < threshold) {
				below.shortMove();
			} else {
				above.shortMove();
			}
		}
		sonar.close();
	}
}
