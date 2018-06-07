package edu.hendrix.ev3onboardprog.reactive;

import java.util.function.DoublePredicate;

import edu.hendrix.ev3onboardprog.Repped;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public enum Sensor implements Repped {
	NONE {
		@Override
		public float[] targets() {
			return new float[] {0.0f};
		}

		@Override
		public SensorRunner getSensorObject(Port p, DoublePredicate test) {
			return new SensorRunner() {
				@Override
				public boolean matches() {
					return false;
				}

				@Override
				public void close() {}
			};
		}

		@Override
		public String rep() {
			return "NO";
		}

		@Override
		public Op preferredOp() {
			return Op.EQ;
		}
	},
	SONAR {
		@Override
		public float[] targets() {
			return new float[] {10.0f, 20.0f, 30.0f, 40.0f, 50.0f, 60.0f, 70.0f, 80.0f, 90.0f, 100.0f};
		}

		@Override
		public WrappedSensor getSensorObject(Port p, DoublePredicate test) {
			EV3UltrasonicSensor sonar = new EV3UltrasonicSensor(p);
			return new WrappedSensor(sonar, sonar.getDistanceMode(), test);
		}

		@Override
		public String rep() {
			return "SO";
		}

		@Override
		public Op preferredOp() {
			return Op.LT;
		}
	},
	BUMP {
		@Override
		public float[] targets() {
			return new float[] {0.0f, 1.0f};
		}

		@Override
		public WrappedSensor getSensorObject(Port p, DoublePredicate test) {
			EV3TouchSensor bumper = new EV3TouchSensor(p);
			return new WrappedSensor(bumper, bumper, test);
		}

		@Override
		public String rep() {
			return "BP";
		}

		@Override
		public Op preferredOp() {
			return Op.EQ;
		}
	};
	
	abstract public float[] targets();
	abstract public SensorRunner getSensorObject(Port p, DoublePredicate test);
	abstract public Op preferredOp();
}
