package edu.hendrix.ev3onboardprog.reactive;

import java.util.function.DoublePredicate;

import edu.hendrix.ev3onboardprog.RotateFuncs;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;

public class SensorSpec extends Spec {
	private Sensor sensor;
	private int testValueIndex;
	
	public SensorSpec() {
		this.sensor = Sensor.NONE;
		this.testValueIndex = 0;
	}
	
	public void nextSensor() {
		sensor = RotateFuncs.nextChoice(sensor, Sensor.values());
		fixTestValue();
	}
	
	public void prevSensor() {
		sensor = RotateFuncs.prevChoice(sensor, Sensor.values());
		fixTestValue();
	}
	
	private void fixTestValue() {
		testValueIndex = Math.min(testValueIndex, sensor.targets().length - 1);
	}
	
	public void nextValue() {
		testValueIndex = RotateFuncs.nextIndex(sensor.targets().length, testValueIndex);
	}
	
	public void prevValue() {
		testValueIndex = RotateFuncs.prevIndex(sensor.targets().length, testValueIndex);
	}

	@Override
	public SensorRunner makeRunner(Port p) {
		DoublePredicate pred = sensor.preferredOp().makePred(sensor.targets()[testValueIndex]);
		return sensor.getSensorObject(p, pred);
	}

	@Override
	public int numChoices() {
		return 3;
	}

	@Override
	public void next(int choice) {
		switch (choice % numChoices()) {
		case 0:  nextAction(); break;
		case 1:  nextSensor(); break;
		default: nextValue();
		}
	}

	@Override
	public void prev(int choice) {
		switch (choice % numChoices()) {
		case 0:  prevAction(); break;
		case 1:  prevSensor(); break;
		default: prevValue();
		}
	}

	@Override
	public void lcdShow(int row, int highlight) {
		LCD.drawString("S" + (row + 1), 0, row);
		LCD.drawString(action().rep(), 3, row, highlight == 0);
		LCD.drawString(sensor.rep(), 5, row, highlight == 1);
		LCD.drawString(sensor.preferredOp().rep(), 8, row);
		LCD.drawString(String.format("%4.1f", sensor.targets()[testValueIndex]), 10, row, highlight == 2);
	}
}
