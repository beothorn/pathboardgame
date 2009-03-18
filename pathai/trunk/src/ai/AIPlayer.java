package ai;

import utils.Logger;
import ai.permutations.BoardScoreCalculator;
import ai.permutations.DefaultCalculator;
import ai.permutations.PlayEvaluator;
import ai.permutations.PlayTree;
import externalPlayer.PathAI;
import gameLogic.Board;
import gameLogic.PlaySequence;
import gameLogic.gameFlow.GameState;

public class AIPlayer implements PathAI{

	private static final long MAX_TIME_THINKING = 10000;
	public static int timeBetweenPlays = 2000;

	private final Logger logger = Logger.getLogger(AIPlayer.class);

	private final PlayTree tree;
	private final PlayEvaluator evaluator;

	public AIPlayer() {
		this(new DefaultCalculator());
	}
	
	public AIPlayer(final BoardScoreCalculator calculator) {
		this.evaluator = new PlayEvaluator(calculator);
		say("Hello, Good Luck");
		tree = new PlayTree();
		
		tree.setEvaluator(evaluator);
	}

	private void say(final String message) {
		logger.info("Ai "+evaluator.toString()+" says: "+message);
	}

	private boolean isPuttingStrongsTurn(final String board) {
		return new Board(board).countStrongBottoms() < GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;
	}

	private String startingPlay() {
		final int first = (int)(Math.random()*100)%Board.BOARD_SIZE;
		int second = (int)(Math.random()*100)%Board.BOARD_SIZE;
		while(first == second) {
			second = (int)(Math.random()*100)%Board.BOARD_SIZE;
		}
		int third = (int)(Math.random()*100)%Board.BOARD_SIZE;
		while(second == third || first == third) {
			third = (int)(Math.random()*100)%Board.BOARD_SIZE;
		}

		return "(0,"+first+")"+PlaySequence.PLAYS_SEPARATOR+"(0,"+second+")"+PlaySequence.PLAYS_SEPARATOR+"(0,"+third+")";
	}

	@Override
	public String play(final String board) {
		
		say("I'm Thinking");
		
		if(isPuttingStrongsTurn(board)) {
			return startingPlay();
		}else{
			return tree.bestPlayFor(board,MAX_TIME_THINKING);
		}
	}
	
	@Override
	public String toString() {
		return evaluator.toString();
	}
}