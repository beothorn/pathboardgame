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
	
	public static final int GAME_PUTTING_STRONGS_ID = 0;
	public static final int GAME_PUTTING_WEAKS_ID = 1;
	public static final int GAME_MOVING_STRONS_ID = 2;
	public static final int GAME_ENDED_ID = 3;
	public static final int GAME_ENDED_DRAW_ID = 4;

	public int getStateId();
	
	public boolean isBottomPlayerTurn();

	public boolean isTopPlayerTurn();

	public boolean isPuttingStrongsTurn();
	
	public Set<Integer> getAlreadyMovedOrEmptySet();

	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay) throws InvalidPlayException;
	
	public GameState play(ValidPlay validPlay, Board board);
	
	public GameState copy();

	public boolean isGameEnded();
}
