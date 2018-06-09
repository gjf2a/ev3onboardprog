package edu.hendrix.ev3onboardprog.colorcluster;

import edu.hendrix.ev3onboardprog.cluster.Clusterable;
import edu.hendrix.ev3onboardprog.util.DeepCopyable;
import edu.hendrix.ev3onboardprog.util.Util;
import lejos.hardware.video.YUYVImage;

public class ColorCluster implements Clusterable<ColorCluster>, DeepCopyable<ColorCluster> {

	private int y, u, v;
	
	public ColorCluster(int y, int u, int v) {
		this.y = y;
		this.u = u;
		this.v = v;
	}
	
	public ColorCluster(double y, double u, double v) {
		this.y = (int)y;
		this.u = (int)y;
		this.v = (int)v;
	}
	
	public ColorCluster(YUYVImage img, int x, int y) {
		this(img.getY(x, y), img.getU(x, y), img.getV(x, y));
	}
	
	@Override
	public String toString() {
		return "ColorCluster(" + y + "," + u + "," + v + ")";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ColorCluster) {
			ColorCluster that = (ColorCluster)other;
			return this.y == that.y && this.u == that.u && this.v == that.v;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return (int)y << 16 + (int)u << 8 + (int)v;
	}
	
	@Override
	public ColorCluster deepCopy() {
		return new ColorCluster(y, u, v);
	}

	@Override
	public ColorCluster weightedCentroidWith(ColorCluster other, long thisCount, long otherCount) {
		return new ColorCluster(Clusterable.weightedMean(this.y, other.y, thisCount, otherCount),
				Clusterable.weightedMean(this.u, other.u, thisCount, otherCount),
				Clusterable.weightedMean(this.v, other.v, thisCount, otherCount));
	}

	public static double distance(ColorCluster c1, ColorCluster c2) {
		Util.assertArgument(c1 != null, "c1 null");
		Util.assertArgument(c2 != null, "c2 null");
		return Math.pow(c1.y - c2.y, 2) + Math.pow(c1.u - c2.u, 2) + Math.pow(c1.v - c2.v, 2);
	}
}
