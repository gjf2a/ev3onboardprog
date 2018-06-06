package edu.hendrix.ev3onboardprog.reactive;

import edu.hendrix.ev3onboardprog.Util;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class ControllerSpecs {
	private Spec[] specs = new Spec[] {new SensorSpec(), new SensorSpec(), new SensorSpec(), new SensorSpec(), new DefaultSpec()};
	private Port[] ports = new Port[] {SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4, SensorPort.S4};
	private int topicOuter = 0, topicInner = 0;
	
	public void checkAndUse(Key button, Runnable action) {
		Util.checkAndUse(button, () -> {
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
