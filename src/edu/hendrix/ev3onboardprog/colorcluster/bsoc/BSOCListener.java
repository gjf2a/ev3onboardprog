package edu.hendrix.ev3onboardprog.colorcluster.bsoc;

public interface BSOCListener {
	public void addingNode(int node);
	public void removingNode(int node);
	public void replacingNode(int target, int replacement);
}
