package edu.hendrix.ev3onboardprog;

public interface Updateable {
	public void setSelect(boolean isSelected);
	public void display(int row);
	public void update(boolean forward);
}
