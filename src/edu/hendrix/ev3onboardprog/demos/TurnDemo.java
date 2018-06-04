package edu.hendrix.ev3onboardprog.demos;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.motor.Motor;

public class TurnDemo {
	public static void main(String[] args) {
		Motor.A.forward();
		Motor.D.backward();
		while (Motor.A.getTachoCount() < 85) {}
		Move.allStop();
	}
}
