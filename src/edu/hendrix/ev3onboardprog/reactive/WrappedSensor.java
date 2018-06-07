package edu.hendrix.ev3onboardprog.reactive;

import java.util.Optional;
import java.util.function.DoublePredicate;
import lejos.hardware.sensor.BaseSensor;
import lejos.robotics.SampleProvider;

public class WrappedSensor implements SensorRunner {
	private BaseSensor sensor;
	private SampleProvider provider;
	private DoublePredicate test;
	private float[] sample = new float[1];
	
	public WrappedSensor(BaseSensor sensor, SampleProvider provider, DoublePredicate test) {
		this.sensor = sensor;
		this.provider = provider;
		this.test = test;
	}
	
	public void close() {
		sensor.close();
	}
	
	public float sense() {
		provider.fetchSample(sample, 0);
		return sample[0];
	}
	
	public Optional<Float> lastValue() {
		return Optional.of(sample[0]);
	}
	
	public boolean matches() {
		return test.test(sense());
	}
}
