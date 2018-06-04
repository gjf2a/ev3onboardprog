package edu.hendrix.ev3onboardprog;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public enum Move {
	
	FORWARD {
		@Override
		public void shortMove() {
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
	abstract public String rep();
	abstract public int moveDistance();
	abstract public NXTRegulatedMotor trackedMotor();
	
	public Move nextChoice() {
		return advChoice(1);
	}
	
	public Move prevChoice() {
		return advChoice(-1);
	}
	
	public Move advChoice(int dir) {
		return values()[Util.addMod(this.ordinal(), (dir < 0 ? -1 : 1), values().length)];
	}
	
	public void longMove() {
		trackedMotor().resetTachoCount();
		shortMove();
		while (trackedMotor().getTachoCount() < moveDistance()) {}
	}
	
	public static void allStop() {
		Motor.A.stop(true);
		Motor.D.stop();
	}
	
	public static final int FORWARD_COUNT = 215; // about 10 cm
	public static final int TURN_COUNT = 100; // about 45 degrees
}
