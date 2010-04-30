package ai.permutations;

import gameLogic.board.Board;

public interface BoardScoreCalculator {
	public int getScoreForBoard(final Board board);
	public int scoreGoodEnoughToPlay();
}
