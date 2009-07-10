package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.util.Set;

public interface GameState {

	public static final int NUMBER_OF_STRONG_PIECES_TO_PUT = 3;
	public static final int NUMBER_OF_WEAK_PIECES_TO_PUT = 3;
	public static final int NUMBER_OF_STRONG_PIECES_TO_MOVE = 3;

	public void visit(final StateVisitor stateVisitor);
	
	public boolean isBottomPlayerTurn();

	public boolean isTopPlayerTurn();

	public boolean isPuttingStrongsTurn();
	
	public Set<Integer> getAlreadyMovedOrEmptySet();

	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay) throws InvalidPlayException;
	
	public GameState play(ValidPlay validPlay, Board board);
	
	public GameState copy();

	public boolean isGameEnded();
}
