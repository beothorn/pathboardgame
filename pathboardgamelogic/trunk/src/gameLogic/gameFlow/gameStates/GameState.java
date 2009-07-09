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

	public static final String BOTTOM_PLAYER = "Bottom player";
	public static final String GAME_ENDED_BOTTOM_WINS_DESCRIPTION = BOTTOM_PLAYER+" wins!!";
	public static final String GAME_ENDED_DRAW_DESCRIPTION = "Game draw.";
	public static final String BOTTOM_PLAYER_PUTTING_STRONGS_DESCRIPTION = BOTTOM_PLAYER+": add 3 strong pieces to the first line.";
	public static final String BOTTOM_PLAYER_MOVING_STRONGS_DESCRIPTION = BOTTOM_PLAYER+": Move your strong pieces one time each or pass our turn.";
	public static final String BOTTOM_PLAYER_PUTTING_WEAKS_DESCRIPTION = BOTTOM_PLAYER+": Put three weak pieces in first line or pass to moving strongs.";

	//TODO: make layer name changeable
	public static final String TOP_PLAYER = "Top player";
	public static final String GAME_ENDED_TOP_WINS_DESCRIPTION = TOP_PLAYER+" wins!!";
	public static final String TOP_PLAYER_MOVING_STRONGS_DESCRIPTION = TOP_PLAYER+": Move your strong pieces one time each or pass our turn.";
	public static final String TOP_PLAYER_PUTTING_STRONGS_DESCRIPTION = TOP_PLAYER+": add 3 strong pieces to the first line.";
	public static final String TOP_PLAYER_PUTTING_WEAKS_DESCRIPTION = TOP_PLAYER+": Put three weak pieces in first line or pass to moving strongs.";

	//TODO: totally Lame solution, stop being lazy and implement equals/hash on gamestates
	public static final int PUTTING_STRONGS = 0;
	public static final int MOVING_STRONGS = 1;
	public static final int PUTTING_WEAKS = 2;
	public static final int GAME_ENDED = 3;

	public String getStateDescription();
	
	public int getState();

	public boolean isBottomPlayerTurn();

	public boolean isTopPlayerTurn();

	public boolean isPuttingStrongsTurn();
	
	public Set<Integer> getAlreadyMovedOrEmptySet();

	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay) throws InvalidPlayException;
	
	public GameState play(ValidPlay validPlay, Board board);
	
	public GameState copy();

	public boolean isGameEnded();
}
