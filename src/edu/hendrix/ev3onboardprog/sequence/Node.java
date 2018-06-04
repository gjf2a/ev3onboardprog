package edu.hendrix.ev3onboardprog.sequence;

import java.util.function.Consumer;

public class Node<T> {
	private T value;
	private Node<T> next;
	private int size;
	
	Node(T value, Node<T> next) {
		this.value = value;
		this.next = next;
		this.size = (next != null ? next.size : 0) + 1;
	}
	
	public void allForward(Consumer<T> func) {
		func.accept(value);
		if (next != null) {next.allForward(func);}
	}
	
	public void allReverse(Consumer<T> func) {
		if (next != null) {next.allReverse(func);}
		func.accept(value);
	}
	
	public T get() {
		return value;
	}
	
	public int size() {
		return size;
	}
	
	public Node<T> next() {
		return next;
	}
}
