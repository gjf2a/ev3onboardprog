package edu.hendrix.ev3onboardprog;

import edu.hendrix.ev3onboardprog.ui.RotateFuncs;
import lejos.hardware.motor.Motor;

public enum TrackMove implements Repped {
	
	FORWARD {
		@Override
		public void move() {
			Motor.A.setSpeed(FORWARD_SPEED);
			Motor.D.setSpeed(FORWARD_SPEED);
			Motor.A.forward();
			Motor.D.forward();
		}

		@Override
		public String rep() {
			return "F";
		}
	},
	LEFT {
		@Override
		public void move() {
			Motor.A.setSpeed(TURN_SPEED);
			Motor.D.setSpeed(TURN_SPEED);
			Motor.D.forward();
			Motor.A.backward();			
		}

		@Override
		public String rep() {
			return "L";
		}
	},
	RIGHT {
		@Override
		public void move() {
			Motor.A.setSpeed(TURN_SPEED);
			Motor.D.setSpeed(TURN_SPEED);
			Motor.A.forward();
			Motor.D.backward();
		}

		@Override
		public String rep() {
			return "R";
		}
	},
	BACK {
		@Override
		public void move() {
			Motor.A.setSpeed(FORWARD_SPEED);
			Motor.D.setSpeed(FORWARD_SPEED);
			Motor.A.backward();
			Motor.D.backward();
		}
		
		@Override
		public String rep() {
			return "B";
		}
	};
	
	abstract public void move();
	
	public TrackMove nextChoice() {
		return RotateFuncs.nextChoice(this, TrackMove.values());
	}
	
	public TrackMove prevChoice() {
		return RotateFuncs.prevChoice(this, TrackMove.values());
	}
	
	public static void allStop() {
		Motor.A.stop(true);
		Motor.D.stop();
	}
	
	public static final int TURN_SPEED = 50;
	public static final int FORWARD_SPEED = 300;
}
