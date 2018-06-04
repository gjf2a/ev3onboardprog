package edu.hendrix.ev3onboardprog.demos;

import edu.hendrix.ev3onboardprog.Move;
import lejos.hardware.motor.Motor;

public class ForwardDemo {
	public static void main(String[] args) {
		Motor.A.forward();
		Motor.D.forward();
		while (Motor.A.getTachoCount() < 178) {}
		Move.allStop();
	}
}
