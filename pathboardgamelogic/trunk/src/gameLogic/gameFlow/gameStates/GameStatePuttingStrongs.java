package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStatePuttingStrongs implements GameState {

	private final boolean isTopPlayerTurn;
	private final boolean isFirstState;

	public GameStatePuttingStrongs(final boolean isTopPlayerTurn, final boolean isFirstState) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		this.isFirstState = isFirstState;
	}

	@Override
	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay)throws InvalidPlayException {
		if(isTopPlayerPlay != isTopPlayerTurn ){
			throw InvalidPlayException.itsNotYourTurn(!isTopPlayerTurn);
		}
		if(play.isMoveDirection()){
			throw InvalidPlayException.cantMoveStrongWhenPuttingStrongs(GameState.NUMBER_OF_STRONG_PIECES_TO_PUT);
		}
		if(play.isNextState()){
			throw InvalidPlayException.cantSkipPuttingStrongs(GameState.NUMBER_OF_STRONG_PIECES_TO_PUT - board.countStrongsFor(isTopPlayerTurn));
		}
		return board.validatePlay(play, isTopPlayerTurn);
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return !isTopPlayerTurn;
	}

	@Override
	public boolean isPuttingStrongsTurn() {
		return true;
	}

	@Override
	public boolean isTopPlayerTurn() {
		return isTopPlayerTurn;
	}

	@Override
	public GameState play(ValidPlay validPlay, Board board){
		board.play(validPlay, isTopPlayerTurn);
		final int numberOfStrongsLeft = GameState.NUMBER_OF_STRONG_PIECES_TO_PUT - board.countStrongsFor(isTopPlayerTurn);
		if(numberOfStrongsLeft == 0){
			if(isFirstState){
				final boolean nextIsFirstState = false;
				return new GameStatePuttingStrongs(!isTopPlayerTurn(),nextIsFirstState);
			}else{
				return new GameStatePuttingWeaks(!isTopPlayerTurn());
			}
		}
		return this;
	}
	
	@Override
	public boolean isGameEnded() {
		return false;
	}

	@Override
	public GameState copy() {
		return new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
	}

	@Override
	public Set<Integer> getAlreadyMovedOrEmptySet() {
		return new LinkedHashSet<Integer>();
	}

	@Override
	public void accept(final StateVisitor stateVisitor) {
		stateVisitor.onPuttingStrongs(this);
	}
}
