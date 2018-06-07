package edu.hendrix.ev3onboardprog.reactive;

import java.util.ArrayList;
import java.util.Optional;

import edu.hendrix.ev3onboardprog.Counter;
import edu.hendrix.ev3onboardprog.CyclesPerSecond;
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
		Counter row = new Counter();
		Optional<Move> move = Optional.empty();
		for (int i = 0; i < size(); i++) {
			if (sensors.get(i).matches() && !move.isPresent()) {
				move = Optional.of(actions.get(i));
			}
			final int sensorInt = i + 1;
			sensors.get(i).lastValue().ifPresent(v -> {
				LCD.drawString(String.format("S%d: %5.2f        ", sensorInt, v), 0, row.get());
				row.bump();
			});
		}
		move.ifPresent(m -> {
			m.shortMove();
			LCD.drawString(m.toString() + "           ", 0, row.get());
		});
	}
	
	public void loop() {
		LCD.clear();
		LCD.drawString("Starting...", 0, 0);
		CyclesPerSecond cps = new CyclesPerSecond();
		while (Button.ESCAPE.isUp()) {
			run();
			cps.bump();
		}
		cps.stop();
		Move.allStop();
		for (SensorRunner sensor: sensors) {
			sensor.close();
		}
		//cps.display();
	}
}
