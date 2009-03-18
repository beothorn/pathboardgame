package ai.permutations;

import gameLogic.Play;
import gameLogic.PlaySequence;

public class PlayEvaluator {
	private final BoardScoreCalculator boardCalculator;
	
	private String bestPlay;
	private int bestScore;
	private boolean alreadyFoundGoodEnoughPlay = false;
	
	public PlayEvaluator(final BoardScoreCalculator boardCalculator) {
		this.boardCalculator = boardCalculator;
	}
	
	public void evaluatePlay(final String plays, final String board) {
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
		bestPlay = Play.NEXT_STATE+PlaySequence.PLAYS_SEPARATOR+Play.NEXT_STATE;
		bestScore = Integer.MIN_VALUE;
	}
	
	public String getBestPlay() {
		return bestPlay;
	}

	@Override
	public String toString() {
		return boardCalculator.toString();
	}
}
