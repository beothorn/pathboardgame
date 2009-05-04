package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

public class GameStateGameEnded implements GameState {

	public static final int DRAW = 0;
	public static final int TOP_WON = 1;
	public static final int BOTTOM_WON = 2;
	private final int result;

	public GameStateGameEnded(final int result) {
		this.result = result;
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
	public GameState play(final ValidPlay validPlay, final Board board){
		if(validPlay.unbox().isNextState()){
			return GameStateFactory.getFirstState();
		}
		return this;
	}

	@Override
	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay)throws InvalidPlayException {
		if(play.isNextState())
			return board.validatePlay(play, isTopPlayerPlay);
		throw InvalidPlayException.gameAlreadyEnded(getStateDescription());
	}

	@Override
	public boolean isGameEnded() {
		return true;
	}

	@Override
	public GameState copy() {
		return new GameStateGameEnded(result);
	}

}
