package edu.hendrix.ev3onboardprog.reactive;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;

public class DefaultSpec extends Spec {

	@Override
	public SensorRunner makeRunner(Port p) {
		return new SensorRunner() {

			@Override
			public boolean matches() {
				return true;
			}

			@Override
			public void close() {
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
}
