package edu.hendrix.ev3onboardprog.reactive;

import java.util.Optional;
import java.util.function.DoublePredicate;

import edu.hendrix.ev3onboardprog.Repped;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
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

				@Override
				public Optional<Float> lastValue() {
					return Optional.empty();
				}
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
			return new float[] {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.3f, 1.5f, 1.8f, 2.0f, 2.2f, 2.4f};
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
			return new float[] {1.0f, 0.0f};
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
	}, 
	ACTIVE_LIGHT {

		@Override
		public String rep() {
			return "LA";
		}

		@Override
		public float[] targets() {
			return new float[] {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f};
		}

		@Override
		public SensorRunner getSensorObject(Port p, DoublePredicate test) {
			EV3ColorSensor light = new EV3ColorSensor(p);
			return new WrappedSensor(light, light.getRedMode(), test);
		}

		@Override
		public Op preferredOp() {
			return Op.LT;
		}
		
	}, PASSIVE_LIGHT {

		@Override
		public String rep() {
			return "LI";
		}

		@Override
		public float[] targets() {
			return new float[] {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f};
		}

		@Override
		public SensorRunner getSensorObject(Port p, DoublePredicate test) {
			EV3ColorSensor light = new EV3ColorSensor(p);
			return new WrappedSensor(light, light.getAmbientMode(), test);
		}

		@Override
		public Op preferredOp() {
			return Op.LT;
		}
		
	};
	
	abstract public float[] targets();
	abstract public SensorRunner getSensorObject(Port p, DoublePredicate test);
	abstract public Op preferredOp();
}
