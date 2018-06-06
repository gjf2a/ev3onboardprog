package edu.hendrix.ev3onboardprog.reactive;

import java.util.function.DoublePredicate;

import edu.hendrix.ev3onboardprog.Repped;

public enum Op implements Repped {
	LT {
		@Override
		public DoublePredicate makePred(double value) {
			return d -> d < value;
		}

		@Override
		public String rep() {
			return "<";
		}
	},
	GT {
		@Override
		public DoublePredicate makePred(double value) {
			return d -> d > value;
		}

		@Override
		public String rep() {
			return ">";
		}
	},
	EQ {
		@Override
		public DoublePredicate makePred(double value) {
			return d -> d == value;
		}

		@Override
		public String rep() {
			return "=";
		}
	};
	
	abstract public DoublePredicate makePred(double value);
}
