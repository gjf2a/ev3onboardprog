package edu.hendrix.ev3onboardprog;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;

public class StringListView<T> {
	private StringList<T> src;
	private int xOffset = 0, yOffset = 0, selectedRow = 0;
	
	public StringListView(StringList<T> src) {
		this.src = src;
	}
	
	public void checkAndUse(Key button, Runnable action) {
		Util.checkAndUse(button, () -> {
			action.run();
			display();
		});
	}
	
	public void move() {
		checkAndUse(Button.DOWN, () -> {
			if (yOffset + ScreenSpot.ROWS < src.size()) {
				yOffset += 1;
			}
		});
		
		checkAndUse(Button.UP, () -> {
			if (yOffset > 0) {
				yOffset -= 1;
			}
		});
		
		checkAndUse(Button.LEFT, () -> {
			if (xOffset > 0) {
				xOffset -= 1;
			}
		});
		
		checkAndUse(Button.RIGHT, () -> {
			if (xOffset + 1 < src.maxWidthFrom(firstVisibleRow(), lastVisibleRow())) {
				xOffset += 1;
			}
		});
	}
	
	public int getSelected() {return selectedRow;}
	
	public int firstVisibleRow() {return yOffset;}
	
	public int lastVisibleRow() {return Math.min(yOffset + ScreenSpot.ROWS, src.size()) - 1;}
	
	public void display() {
		LCD.clear();
		for (int i = firstVisibleRow(); i <= lastVisibleRow(); i++) {
			LCD.drawString(src.get(i).toString().substring(xOffset), 0, i, i == getSelected());
		}
	}
	
	public static <T> T selectLoop(StringList<T> src) {
		StringListView<T> view = new StringListView<>(src);
		view.display();
		while (true) {
			view.move();
			if (Button.ENTER.isDown() || Button.ESCAPE.isDown()) {
				return src.get(view.getSelected());
			}
		}
	}
}
