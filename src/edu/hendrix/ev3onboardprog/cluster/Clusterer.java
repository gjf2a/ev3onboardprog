package edu.hendrix.ev3onboardprog.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import edu.hendrix.ev3onboardprog.util.Duple;
import edu.hendrix.ev3onboardprog.util.Util;


public interface Clusterer<C,I> {
	public int train(I example);
	
	public double distance(C one, C two);
	
	public C transform(I input);
	
	default public int getClosestMatchFor(I example) {
		return getClosestNodeDistanceFor(example).getFirst();
	}
	
	default public C getMatchingIdealInputFor(I example) {
		return getIdealInputFor(getClosestMatchFor(example));
	}
	
	default public Duple<Integer, Double> getClosestNodeDistanceFor(I example) {
		Util.assertArgument(example != null, "Null example given!");
		C transformed = transform(example);
		Duple<Integer,Double> result = null;
		for (int id: getClusterIds()) {
			double dist = distance(transformed, getIdealInputFor(id));
			if (result == null || dist < result.getSecond()) {
				result = new Duple<>(id, dist);
			}
		}
		Util.assertState(result != null, "Returning null result!");
		return result;
	}
	
	default public ArrayList<Duple<Integer, Double>> getNodeRanking(I example) {
		C transformed = transform(example);
		ArrayList<Duple<Integer, Double>> result = new ArrayList<>();
		for (int id: getClusterIds()) {
			result.add(new Duple<>(id, distance(transformed, getIdealInputFor(id))));
		}
		Collections.sort(result, (o1, o2) -> o1.getSecond() < o2.getSecond() ? -1 : o1.getSecond() > o2.getSecond() ? 1 : 0);
		return result;
	}
	
	default public ArrayList<C> getAllIdealInputs() {
		ArrayList<C> result = new ArrayList<>();
		for (int id: getClusterIds()) {
			result.add(getIdealInputFor(id));
		}
		return result;
	}
	
	public C getIdealInputFor(int node);
	
	public int size();
	
	public Collection<Integer> getClusterIds();
}
