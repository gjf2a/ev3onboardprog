package edu.hendrix.ev3onboardprog.vision;

import lejos.hardware.video.YUYVImage;

public enum Band {
	Y {
		@Override
		public int get(YUYVImage img, int x, int y) {
			return mask(img.getY(x, y));
		}
	},
	U {
		@Override
		public int get(YUYVImage img, int x, int y) {
			return mask(img.getU(x, y));
		}
	},
	V {
		@Override
		public int get(YUYVImage img, int x, int y) {
			return mask(img.getV(x, y));
		}
	};
	
	abstract public int get(YUYVImage img, int x, int y);
	public int mask(int value) {
		return value & 0xFF;
	}
}
