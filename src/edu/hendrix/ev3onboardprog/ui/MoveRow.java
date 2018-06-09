package edu.hendrix.ev3onboardprog.ui;

import edu.hendrix.ev3onboardprog.Move;

public class MoveRow extends Row<Move> {
	public MoveRow(String prompt) {
		//super(prompt, Move.FORWARD, (m, f) -> m.advChoice(f ? 1 : -1));
		super(prompt, Move.FORWARD, (m, f) -> RotateFuncs.advChoice(f ? 1 : -1, m, Move.values()));
	}
}
