package edu.hendrix.ev3onboardprog;

import edu.hendrix.ev3onboardprog.ui.RotateFuncs;
import lejos.hardware.motor.Motor;

public enum TrackMove implements Repped {
	
	FORWARD {
		@Override
		public void move() {
			Motor.A.setSpeed(300);
			Motor.D.setSpeed(300);
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
			Motor.A.setSpeed(100);
			Motor.D.setSpeed(100);
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
			Motor.A.setSpeed(100);
			Motor.D.setSpeed(100);
			Motor.A.forward();
			Motor.D.backward();
		}

		@Override
		public String rep() {
			return "R";
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
}
