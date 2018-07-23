package edu.hendrix.ev3onboardprog.colortrack;

import edu.hendrix.ev3onboardprog.ui.UIFuncs;
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
		drawBounds();
	}
	
	public void drawBounds() {
		for (int i = 0; i < YUVBand.values().length; i++) {
			E b = band.getEnumConstants()[i];
			LCD.drawString(String.format("%c:(", b.toString().charAt(0)), 0, i);
			LCD.drawString(String.format("%3d", bound.getMin(b)), 3, i, current == i && isMin);
			LCD.drawString(",", 6, i);
			LCD.drawString(String.format("%3d", bound.getMax(b)), 7, i, current == i && !isMin);
			LCD.drawString(")", 10, i);
		}
	}
	
	public void nextValue() {
		if (!isMin) {
			current = (current + 1) % YUVBand.values().length;
		}
		isMin = !isMin;
	}
	
	public void prevValue() {
		if (isMin) {
			current = (current - 1 + YUVBand.values().length) % YUVBand.values().length;
		}
		isMin = !isMin;
	}
	
	public void incValue() {
		if (isMin) {
			bound.minUp(getCurrent());
		} else {
			bound.maxUp(getCurrent());
		}
	}
	
	public void decValue() {
		if (isMin) {
			bound.minDown(getCurrent());
		} else {
			bound.maxDown(getCurrent());
		}
	}
	
	public void invert() {
		bound.invertInside();
	}
	
	public void buttonChange() {
		UIFuncs.checkAndUse(Button.LEFT, () -> {
			prevValue();
			display();
		});
		
		UIFuncs.checkAndUse(Button.RIGHT, () -> {
			nextValue();
			display();
		});
		
		UIFuncs.checkAndUse(Button.DOWN, () -> {
			decValue();
			display();
		});
		
		UIFuncs.checkAndUse(Button.UP, () -> {
			incValue();
			display();
		});
	}
	
	public void loop() {
		display();
		while (Button.ENTER.isUp() && Button.ESCAPE.isUp()) {
			buttonChange();
		}
	}
}
