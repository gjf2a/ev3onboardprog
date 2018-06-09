package edu.hendrix.ev3onboardprog.ui;

import java.util.function.BiFunction;

import lejos.hardware.lcd.LCD;

public class Row<T> implements Updateable {
	private String prompt;
	private T value;
	private BiFunction<T,Boolean,T> updater;
	private boolean selected = false;
	
	public Row(String prompt, T startValue, BiFunction<T,Boolean,T> updater) {
		this.prompt = prompt;
		this.value = startValue;
		this.updater = updater;
	}
	
	public void setSelect(boolean isSelected) {
		selected = isSelected;
	}
	
	public void display(int row) {
		LCD.drawString(prompt + ":" + value.toString() + "    ", 0, row, selected);
	}
	
	public void update(boolean forward) {
		value = updater.apply(value, forward);
	}
	
	public T get() {return value;}
}
