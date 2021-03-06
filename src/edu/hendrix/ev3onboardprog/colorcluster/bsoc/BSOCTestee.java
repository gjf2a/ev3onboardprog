package edu.hendrix.ev3onboardprog.colorcluster.bsoc;

import edu.hendrix.ev3onboardprog.cluster.Clusterable;
import edu.hendrix.ev3onboardprog.util.DeepCopyable;

public class BSOCTestee implements Clusterable<BSOCTestee>, DeepCopyable<BSOCTestee> {
	private long value;
	
	public BSOCTestee() {this(0);}
	
	public BSOCTestee(long d) {this.value = d;}
	
	public BSOCTestee(String src) {this(Long.parseLong(src));}
	
	public BSOCTestee(BSOCTestee other) {this.value = other.value;}
	
	public long get() {return value;}
	
	public double distanceTo(BSOCTestee other) {return Math.abs(this.value - other.value);}

	@Override
	public BSOCTestee weightedCentroidWith(BSOCTestee other, long thisCount, long otherCount) {
		return new BSOCTestee((this.value * thisCount + other.value * otherCount) / (thisCount + otherCount));
	}
	
	@Override
	public String toString() {return Long.toString(value);}

	@Override
	public BSOCTestee deepCopy() {
		return new BSOCTestee(value);
	}
}
