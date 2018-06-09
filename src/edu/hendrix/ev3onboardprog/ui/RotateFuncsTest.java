package edu.hendrix.ev3onboardprog.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.hendrix.ev3onboardprog.reactive.Op;

public class RotateFuncsTest {

	@Test
	public void test() {
		assertEquals(Op.GT, RotateFuncs.nextChoice(Op.LT, Op.values()));
		assertEquals(Op.EQ, RotateFuncs.nextChoice(Op.GT, Op.values()));
		assertEquals(Op.LT, RotateFuncs.nextChoice(Op.EQ, Op.values()));
		assertEquals(Op.LT, RotateFuncs.prevChoice(Op.GT, Op.values()));
		assertEquals(Op.GT, RotateFuncs.prevChoice(Op.EQ, Op.values()));
		assertEquals(Op.EQ, RotateFuncs.prevChoice(Op.LT, Op.values()));
	}

}
