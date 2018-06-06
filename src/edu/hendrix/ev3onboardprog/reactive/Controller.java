package edu.hendrix.ev3onboardprog.reactive;

import java.util.ArrayList;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Controller implements Runnable {
	private ArrayList<SensorRunner> sensors = new ArrayList<>();
	private ArrayList<Move> actions = new ArrayList<>();
	
	public void add(SensorRunner sensor, Move action) {
		sensors.add(sensor);
		actions.add(action);
	}
	
	public int size() {
		return sensors.size();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < size(); i++) {
			if (sensors.get(i).matches()) {
				Move action = actions.get(i);
				action.shortMove();
				LCD.drawString(action.toString() + "           ", 0, 0);
				return;
			}
		}
	}
	
	public void loop() {
		LCD.clear();
		LCD.drawString("Starting...", 0, 0);
		while (Button.ESCAPE.isUp()) {
			run();
		}
		Move.allStop();
		for (SensorRunner sensor: sensors) {
			sensor.close();
		}
	}
}
