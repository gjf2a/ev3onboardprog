package edu.hendrix.ev3onboardprog.reactive;

import java.util.Optional;

public interface SensorRunner {
	public boolean matches();
	public Optional<Float> lastValue();
	public void close();
}
