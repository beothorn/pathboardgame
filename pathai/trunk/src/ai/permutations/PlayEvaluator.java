package ai.permutations;

import gameLogic.board.Board;
import gameLogic.board.PlaySequence;

public class PlayEvaluator {
	private final BoardScoreCalculator boardCalculator;
	
	private PlaySequence bestPlay;
	private int bestScore;
	private boolean alreadyFoundGoodEnoughPlay = false;
	
	public PlayEvaluator(final BoardScoreCalculator boardCalculator) {
		this.boardCalculator = boardCalculator;
	}
	
	public void evaluatePlay(final PlaySequence plays,final Board board) {
		final int calculateScoreForBoard = boardCalculator.getScoreForBoard(board);
		if(calculateScoreForBoard > bestScore){
			bestPlay = plays;
			bestScore = calculateScoreForBoard;
		}
		if(bestScore>=boardCalculator.scoreGoodEnoughToPlay()){
			alreadyFoundGoodEnoughPlay = true;
		}
	}
	
	public boolean stopEvaluatingAndUseBestPlay(){
		return alreadyFoundGoodEnoughPlay;
	}
	

	public void reset() {
		alreadyFoundGoodEnoughPlay = false;
		bestPlay = null;
		bestScore = Integer.MIN_VALUE;
	}
	
	public PlaySequence getBestPlay() {
		return bestPlay;
	}

	@Override
	public String toString() {
		return boardCalculator.toString();
	}
}
