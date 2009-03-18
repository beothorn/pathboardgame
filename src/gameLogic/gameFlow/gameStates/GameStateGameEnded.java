package gameLogic.gameFlow.gameStates;

import gameLogic.Board;
import gameLogic.Play;
import gameLogic.gameFlow.BoardListeners;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.PlayResult;

public class GameStateGameEnded implements GameState {

	public static final int DRAW = 0;
	public static final int TOP_WON = 1;
	public static final int BOTTOM_WON = 2;
	
	private final int result;

	public GameStateGameEnded(final int result) {
		this.result = result;
	}

	@Override
	public String asStateUniqueName() {
		if(result == TOP_WON) {
			return GameState.GAME_ENDED_TOP_WINS;
		}
		if(result == BOTTOM_WON)
			return GameState.GAME_ENDED_BOTTOM_WINS;
		return GameState.GAME_ENDED_DRAW;
	}

	@Override
	public String getStateDescription() {
		if(result == TOP_WON) {
			return GameState.GAME_ENDED_TOP_WINS_DESCRIPTION;
		} 
		if(result == BOTTOM_WON){
			return GameState.GAME_ENDED_BOTTOM_WINS_DESCRIPTION;
		}
		return GameState.GAME_ENDED_DRAW_DESCRIPTION;
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return false;
	}

	@Override
	public boolean isPuttingStrongsTurn() {
		return false;
	}

	@Override
	public boolean isTopPlayerTurn() {
		return false;
	}

	@Override
	public GameState nextState(final Board board, final BoardListeners listeners) {
		board.reset();
		if(listeners != null) {
			listeners.callBoardChangedListeners();
		}
		return GameFlowDefinitionsStateFactory.getFirstState();
	}

	@Override
	public GameState nextStateIfChanged(final Board board, final BoardListeners listeners) {
		return this;
	}

	@Override
	public PlayResult play(final Play play, final Board board){
		final boolean successful = false;
		final PlayResult playResult = new PlayResult(successful);
		playResult.setErrorMessage(getStateDescription());
		return playResult;
	}

	@Override
	public boolean stateEnded() {
		return false;
	}

}
