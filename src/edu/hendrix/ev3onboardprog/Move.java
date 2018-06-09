package edu.hendrix.ev3onboardprog;

import edu.hendrix.ev3onboardprog.ui.RotateFuncs;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public enum Move implements Repped {
	
	FORWARD {
		@Override
		public void shortMove() {
			Motor.A.setSpeed(500);
			Motor.D.setSpeed(500);
			Motor.A.forward();
			Motor.D.forward();
		}

		@Override
		public String rep() {
			return "F";
		}

		@Override
		public int moveDistance() {
			return FORWARD_COUNT;
		}

		@Override
		public NXTRegulatedMotor trackedMotor() {
			return Motor.A;
		}
	},
	LEFT {
		@Override
		public void shortMove() {
			Motor.A.setSpeed(100);
			Motor.D.setSpeed(100);
			Motor.D.forward();
			Motor.A.backward();			
		}

		@Override
		public String rep() {
			return "L";
		}

		@Override
		public int moveDistance() {
			return TURN_COUNT;
		}

		@Override
		public NXTRegulatedMotor trackedMotor() {
			return Motor.D;
		}
	},
	RIGHT {
		@Override
		public void shortMove() {
			Motor.A.setSpeed(100);
			Motor.D.setSpeed(100);
			Motor.A.forward();
			Motor.D.backward();
		}

		@Override
		public String rep() {
			return "R";
		}

		@Override
		public int moveDistance() {
			return TURN_COUNT;
		}

		@Override
		public NXTRegulatedMotor trackedMotor() {
			return Motor.A;
		}
	};
	
	abstract public void shortMove();
	abstract public int moveDistance();
	abstract public NXTRegulatedMotor trackedMotor();
	
	public Move nextChoice() {
		return RotateFuncs.nextChoice(this, Move.values());
	}
	
	public Move prevChoice() {
		return RotateFuncs.prevChoice(this, Move.values());
	}
	
	public void longMove() {
		trackedMotor().resetTachoCount();
		shortMove();
		while (trackedMotor().getTachoCount() < moveDistance() && Button.ESCAPE.isUp()) {}
	}
	
	public static void allStop() {
		Motor.A.stop(true);
		Motor.D.stop();
	}
	
	public static final int FORWARD_COUNT = 655; // about 1 foot
	public static final int TURN_COUNT = 98; // about 45 degrees
}
