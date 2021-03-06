package edu.hendrix.ev3onboardprog.colorcluster.bsoc;

import edu.hendrix.ev3onboardprog.cluster.Clusterable;
import edu.hendrix.ev3onboardprog.util.DeepCopyable;
import edu.hendrix.ev3onboardprog.util.Util;

public class Edge<T extends Clusterable<T> & DeepCopyable<T>> implements Comparable<Edge<T>>, DeepCopyable<Edge<T>> {
	private int id1, id2;
	private double distance;
	
	Edge(int myId, int otherId, double otherDistance) {
		Util.assertArgument(myId < otherId, "ids out of order");
		this.id1 = myId;
		this.id2 = otherId;
		this.distance = otherDistance;
	}
	
	Edge(String edgeStr) {
		String[] parts = edgeStr.split(";");
		id1 = Integer.parseInt(parts[0]);
		id2 = Integer.parseInt(parts[1]);
		distance = Double.parseDouble(parts[2]);
	}

	@Override
	public int compareTo(Edge<T> that) {
		if (this.distance < that.distance) {
			return -1;
		} else if (this.distance > that.distance) {
			return 1;
		} else if (this.id1 < that.id1) { 
			return -1;
		} else if (this.id1 > that.id1) {
			return 1;
		} else if (this.id2 < that.id2) { 
			return -1;
		} else if (this.id2 > that.id2) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Edge<?>) {
			@SuppressWarnings("unchecked")
			Edge<T> that = (Edge<T>)other;
			return this.compareTo(that) == 0;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return id1 + ";" + id2 + ";" + distance;
	}

	@Override
	public Edge<T> deepCopy() {
		return new Edge<>(id1, id2, distance);
	}
	
	int getNode1() {return id1;}
	int getNode2() {return id2;}
	double getDistance() {return distance;}
	int getOtherNode(int id) {
		Util.assertArgument(id == id1 || id == id2, id + " not part of edge " + toString());
		return id == id1 ? id2 : id1;
	}
}
