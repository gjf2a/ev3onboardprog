package edu.hendrix.ev3onboardprog.ui;

public enum Reply {
	YES {
		@Override
		public boolean isYes() {
			return true;
		}

		@Override
		public Reply other() {
			return NO;
		}
	}, NO {
		@Override
		public boolean isYes() {
			return false;
		}

		@Override
		public Reply other() {
			return YES;
		}
	};
	
	abstract public boolean isYes();
	abstract public Reply other();
}
