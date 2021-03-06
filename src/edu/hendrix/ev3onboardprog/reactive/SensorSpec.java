package edu.hendrix.ev3onboardprog.reactive;

import java.util.function.DoublePredicate;

import edu.hendrix.ev3onboardprog.ui.RotateFuncs;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;

public class SensorSpec extends Spec {
	private Sensor sensor;
	private Op op;
	private int testValueIndex;
	
	public SensorSpec() {
		this.sensor = Sensor.NONE;
		this.op = Op.EQ;
		this.testValueIndex = 0;
	}
	
	private SensorSpec(String moveStr) {
		super(moveStr);
	}
	
	public void nextOp() {
		op = RotateFuncs.nextChoice(op, Op.values());
	}
	
	public void prevOp() {
		op = RotateFuncs.prevChoice(op, Op.values());
	}
	
	public void nextSensor() {
		sensor = RotateFuncs.nextChoice(sensor, Sensor.values());
		fixOtherValues();
	}
	
	public void prevSensor() {
		sensor = RotateFuncs.prevChoice(sensor, Sensor.values());
		fixOtherValues();
	}
	
	private void fixOtherValues() {
		testValueIndex = 0;
		op = sensor.preferredOp();
	}
	
	public void nextValue() {
		testValueIndex = RotateFuncs.nextIndex(sensor.targets().length, testValueIndex);
	}
	
	public void prevValue() {
		testValueIndex = RotateFuncs.prevIndex(sensor.targets().length, testValueIndex);
	}

	@Override
	public SensorRunner makeRunner(Port p) {
		DoublePredicate pred = op.makePred(sensor.targets()[testValueIndex]);
		return sensor.getSensorObject(p, pred);
	}

	@Override
	public int numChoices() {
		return 4;
	}

	@Override
	public void next(int choice) {
		switch (choice % numChoices()) {
		case 0:  nextAction(); break;
		case 1:  nextSensor(); break;
		case 2:  nextOp();     break;
		default: nextValue();
		}
	}

	@Override
	public void prev(int choice) {
		switch (choice % numChoices()) {
		case 0:  prevAction(); break;
		case 1:  prevSensor(); break;
		case 2:  prevOp();     break;
		default: prevValue();
		}
	}

	@Override
	public void lcdShow(int row, int highlight) {
		LCD.drawString("S" + (row + 1), 0, row);
		LCD.drawString(action().rep(), 3, row, highlight == 0);
		LCD.drawString(sensor.rep(), 5, row, highlight == 1);
		LCD.drawString(op.rep(), 8, row, highlight == 2);
		LCD.drawString(String.format("%4.1f", sensor.targets()[testValueIndex]), 10, row, highlight == 3);
	}
	
	@Override
	public String toString() {
		return action().toString() + ";" + sensor.toString() + ";" + op.toString() + ";" + testValueIndex;
	}
	
	public static SensorSpec fromString(String s) {
		String[] parts = s.split(";"	);
		SensorSpec spec = new SensorSpec(parts[0]);
		spec.sensor = Sensor.valueOf(parts[1]);
		spec.op = Op.valueOf(parts[2]);
		spec.testValueIndex = Integer.parseInt(parts[3]);
		return spec;
	}
}
