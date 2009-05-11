package ai.permutations;

import gameLogic.board.Board;

import java.util.List;

import utils.BoardUtils;

public class DummyCalculators implements BoardScoreCalculator{

	public List<String> expectedBoards;
	private int boardCount = 0;
	
	@Override
	public int getScoreForBoard(final Board board) {
		boardCount++;
		if(boardCount>expectedBoards.size()){
			throw new RuntimeException("Board count greater than expected boards size: ");
					}
		final String boardAsString = BoardUtils.printBoard(board);
		for (final String boardExpected : expectedBoards) {
			if(boardExpected.equals(boardAsString)){
				return 0;
			}
		}
		throw new RuntimeException("Board wasn't expected:\n"+boardAsString);
	}

	@Override
	public int scoreGoodEnoughToPlay() {
		return 999;
	}

	public int getBoardCount() {
		return boardCount;
	}
}
