package edu.hendrix.ev3onboardprog.reactive;

import java.util.Optional;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;

public class DefaultSpec extends Spec {
	
	public DefaultSpec() {}
	
	private DefaultSpec(String moveStr) {
		super(moveStr);
	}

	@Override
	public SensorRunner makeRunner(Port p) {
		return new SensorRunner() {

			@Override
			public boolean matches() {
				return true;
			}

			@Override
			public void close() {
			}

			@Override
			public Optional<Float> lastValue() {
				return Optional.empty();
			}};
	}

	@Override
	public int numChoices() {
		return 1;
	}

	@Override
	public void next(int choice) {
		nextAction();
	}

	@Override
	public void prev(int choice) {
		prevAction();
	}

	@Override
	public void lcdShow(int row, int highlight) {
		LCD.drawString(action().rep(), 0, row, highlight == 0);
	}
	
	@Override
	public String toString() {
		return action().toString();
	}
	
	public static DefaultSpec fromString(String src) {
		return new DefaultSpec(src);
	}
}
