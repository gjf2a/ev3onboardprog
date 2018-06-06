package edu.hendrix.ev3onboardprog;

public class MoveRow extends Row<Move> {
	public MoveRow(String prompt) {
		//super(prompt, Move.FORWARD, (m, f) -> m.advChoice(f ? 1 : -1));
		super(prompt, Move.FORWARD, (m, f) -> RotateFuncs.advChoice(f ? 1 : -1, m, Move.values()));
	}
}
