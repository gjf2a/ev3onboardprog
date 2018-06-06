package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.Util;
import edu.hendrix.ev3onboardprog.vision.YUVBand;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class ColorSelector<E extends Enum<E>> {
	private MinMaxBound<E> bound;
	private Class<E> band;
	private int current;
	private boolean isMin;
	
	public ColorSelector(MinMaxBound<E> bound, Class<E> band) {
		this.bound = bound;
		this.band = band;
		this.isMin = true;
		this.current = 0;
	}
	
	public E getCurrent() {
		return band.getEnumConstants()[current];
	}
	
	public void display() {
		LCD.clear();
		for (int i = 0; i < YUVBand.values().length; i++) {
			E b = band.getEnumConstants()[i];
			LCD.drawString(b + ":" + bound.getMin(b), 0, i * 2, current == i && isMin);
			LCD.drawString(b + ":" + bound.getMax(b), 0, i * 2 + 1, current == i && !isMin);
		}
	}
	
	public void loop() {
		display();
		while (Button.ENTER.isUp() && Button.ESCAPE.isUp()) {
			
			Util.checkAndUse(Button.UP, () -> {
				if (isMin) {
					current = (current - 1 + YUVBand.values().length) % YUVBand.values().length;
				}
				isMin = !isMin;
				display();
			});
			
			Util.checkAndUse(Button.DOWN, () -> {
				if (!isMin) {
					current = (current + 1) % YUVBand.values().length;
				}
				isMin = !isMin;
				display();
			});
			
			Util.checkAndUse(Button.LEFT, () -> {
				if (isMin) {
					bound.minDown(getCurrent());
				} else {
					bound.maxDown(getCurrent());
				}
				display();
			});
			
			Util.checkAndUse(Button.RIGHT, () -> {
				if (isMin) {
					bound.minUp(getCurrent());
				} else {
					bound.maxUp(getCurrent());
				}
				display();
			});
		}
	}
}
