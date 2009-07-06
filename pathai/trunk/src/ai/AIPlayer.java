package ai;

import utils.BoardUtils;
import ai.permutations.BoardScoreCalculator;
import ai.permutations.DefaultCalculator;
import ai.permutations.PlayTree;
import externalPlayer.PathAI;
import gameLogic.board.Board;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.PlaySequence;
import gameLogic.gameFlow.gameStates.GameState;

public class AIPlayer implements PathAI{

//	private final Logger logger = Logger.getLogger(AIPlayer.class);
	private final PlayTree tree;

	public AIPlayer() {
		this(new DefaultCalculator());
	}
	
	public AIPlayer(final BoardScoreCalculator calculator) {
		tree = new PlayTree(new DefaultCalculator());
	}

	private boolean isPuttingStrongsTurn(final Board board) {
		return board.countStrongBottoms() < GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;
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

		return first+PlaySequence.PLAYS_SEPARATOR+second+PlaySequence.PLAYS_SEPARATOR+third;
	}

	@Override
	public String play(final String boardString) {
		final Board board = BoardUtils.newBoardFromString(boardString);
		if(isPuttingStrongsTurn(board)) {
			return startingPlay();
		}else{
			final PlaySequence bestPlayFor = tree.bestPlayFor(board);
			final int maxPlays = 6;
			if(bestPlayFor.size()<maxPlays){
				try {
					final int maxPlaysPerTurn = 3;
					if(bestPlayFor.size()>=maxPlaysPerTurn){
						bestPlayFor.addPlay(new Play(Play.NEXT_STATE));
					}else{
						bestPlayFor.addPlay(new Play(Play.NEXT_STATE));
						bestPlayFor.addPlay(new Play(Play.NEXT_STATE));
					}
				} catch (final InvalidPlayStringException e) {
					e.printStackTrace();
				}
			}
			return bestPlayFor.toString();
		}
	}
}