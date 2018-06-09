package edu.hendrix.ev3onboardprog.reactive;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class ControllerSpecs {
	private Spec[] specs = new Spec[] {new SensorSpec(), new SensorSpec(), new SensorSpec(), new SensorSpec(), new DefaultSpec()};
	private Port[] ports = new Port[] {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4, SensorPort.S4};
	private int topicOuter = 0, topicInner = 0;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < specs.length; i++) {
			sb.append(specs[i].toString() + "\n");
		}
		return sb.toString();
	}
	
	public static ControllerSpecs fromString(String src) {
		ControllerSpecs specs = new ControllerSpecs();
		String[] parts = src.split("\n");
		for (int i = 0; i < specs.specs.length - 1; i++) {
			specs.specs[i] = SensorSpec.fromString(parts[i]);
		}
		specs.specs[specs.specs.length - 1] = DefaultSpec.fromString(parts[specs.specs.length - 1]);
		return specs;
	}
	
	public void checkAndUse(Key button, Runnable action) {
		UIFuncs.checkAndUse(button, () -> {
			action.run();
			display();
		});
	}
	
	public void display() {
		LCD.clear();
		for (int i = 0; i < specs.length; i++) {
			specs[i].lcdShow(i, i == topicOuter ? topicInner : specs[i].numChoices());
		}
	}
	
	public void topicNext() {
		specs[topicOuter].next(topicInner);
	}
	
	public void topicPrev() {
		specs[topicOuter].prev(topicInner);
	}
	
	public void nextTopic() {
		topicInner += 1;
		if (topicInner == specs[topicOuter].numChoices()) {
			topicInner = 0;
			topicOuter += 1;
			if (topicOuter == specs.length) {
				topicOuter = 0;
			}
		}
	}
	
	public void prevTopic() {
		topicInner -= 1;
		if (topicInner < 0) {
			topicOuter -= 1;
			if (topicOuter < 0) {
				topicOuter = specs.length - 1;
			}
			topicInner = specs[topicOuter].numChoices() - 1;
		}
	}
	
	public Controller makeController() {
		Controller c = new Controller();
		for (int i = 0; i < specs.length; i++) {
			try {
				c.add(specs[i].makeRunner(ports[i]), specs[i].action());
			} catch (IllegalArgumentException exc) {
				throw new IllegalArgumentException(String.format("Sensor S%d wrong", i + 1));
			}
		}
		return c;
	}
}
