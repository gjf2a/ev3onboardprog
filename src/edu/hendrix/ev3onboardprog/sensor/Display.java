package edu.hendrix.ev3onboardprog.sensor;

import edu.hendrix.ev3onboardprog.IntRow;
import edu.hendrix.ev3onboardprog.MoveRow;
import edu.hendrix.ev3onboardprog.Updateable;
import edu.hendrix.ev3onboardprog.Util;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;

public class Display {
	private int row = 0;
	public MoveRow below = new MoveRow("Below");
	public MoveRow above = new MoveRow("Above");
	public IntRow thresh = new IntRow("Threshold", 50, 10);
	private Updateable[] rows = new Updateable[]{thresh, below, above};
	
	public Program create() {
		rows[row].setSelect(true);
		display();
		while (Button.ESCAPE.isUp()) {
			checkAndUse(Button.DOWN, () -> rowChange(1));
			checkAndUse(Button.UP, () -> rowChange(-1));
			checkAndUse(Button.LEFT, () -> rows[row].update(false));
			checkAndUse(Button.RIGHT, () -> rows[row].update(true));
		}
		
		while (Button.ESCAPE.isDown()) {}
		return new Program(thresh.get(), below.get(), above.get());
	}
	
	public void rowChange(int change) {
		rows[row].setSelect(false);
		row = Util.addMod(row, change, rows.length);
		rows[row].setSelect(true);
	}
	
	public void display() {
		LCD.clear();
		for (int row = 0; row < rows.length; row++) {
			rows[row].display(row);
		}
	}
	
	public void checkAndUse(Key button, Runnable act) {
		Util.checkAndUse(button, () -> {
			act.run();
			display();
		});
	}
}
