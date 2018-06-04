package edu.hendrix.ev3onboardprog.sequence;

import java.util.function.Consumer;

public class ListZipper<T> {
	
	private Node<T> left, right;
	
	public ListZipper() {
		left = right = null;
	}
	
	public boolean atStart() {return left == null;}
	public boolean atEnd() {return right == null;}
	
	public int size() {
		return (left == null ? 0 : left.size()) + (right == null ? 0 : right.size());
	}
	
	public void forAll(Consumer<T> func) {
		allBefore(func);
		allAfter(func);
	}
	
	public void allBefore(Consumer<T> func) {
		if (left != null)
			left.allReverse(func);
	}
	
	public void allAfter(Consumer<T> func) {
		if (right != null)
			right.allForward(func);
	}
	
	public T getBefore() {
		return left.get();
	}
	
	public T getAfter() {
		return right.get();
	}
	
	public void insertBefore(T obj) {
		left = new Node<T>(obj, left);
	}
	
	public void insertAfter(T obj) {
		right = new Node<T>(obj, right);
	}
	
	public void replaceBefore(T obj) {
		left = new Node<T>(obj, left.next());
	}
	
	public void backspace() {
		if (left != null) {
			left = left.next();
		}
	}
	
	public void delete() {
		if (right != null) {
			right = right.next();
		}
	}
	
	public void moveLeft() {
		insertAfter(left.get());
		backspace();
	}
	
	public void moveRight() {
		insertBefore(right.get());
		delete();
	}
}
