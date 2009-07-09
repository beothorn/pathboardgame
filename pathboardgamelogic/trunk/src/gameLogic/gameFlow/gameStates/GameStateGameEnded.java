package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateGameEnded implements GameState {

	private final boolean isTopPlayerTurn;

	public GameStateGameEnded(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
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
		throw InvalidPlayException.gameAlreadyEnded(isTopPlayerTurn);
	}

	@Override
	public boolean isGameEnded() {
		return true;
	}

	@Override
	public GameState copy() {
		return new GameStateGameEnded(isTopPlayerTurn);
	}

	@Override
	public Set<Integer> getAlreadyMovedOrEmptySet() {
		return new LinkedHashSet<Integer>();
	}

	@Override
	public int getStateId() {
		return GameState.GAME_ENDED_ID;
	}
}
