package edu.hendrix.ev3onboardprog.colorcluster;

import edu.hendrix.ev3onboardprog.util.Util;

public enum Mode {
	TRAIN, 
	APPLY;
	
	public Mode next() {
		return Mode.values()[Util.addMod(1, this.ordinal(), Mode.values().length)];
	}
}
