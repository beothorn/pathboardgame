package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStatePuttingWeaks implements GameState {

	private int numberOfWeaksLeft;
	private final boolean isTopPlayerTurn;

	public GameStatePuttingWeaks(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		numberOfWeaksLeft = GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return !isTopPlayerTurn;
	}

	@Override
	public boolean isPuttingStrongsTurn() {
		return false;
	}

	@Override
	public boolean isTopPlayerTurn() {
		return isTopPlayerTurn;
	}

	@Override
	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay)throws InvalidPlayException {
		if(isTopPlayerPlay != isTopPlayerTurn ){
			throw InvalidPlayException.itsNotYourTurn(!isTopPlayerTurn);
		}
		if(play.isMoveDirection()){
			throw InvalidPlayException.cantMoveStrongWhenAddingWeaks(GameState.NUMBER_OF_WEAK_PIECES_TO_PUT);
		}
		return board.validatePlay(play, isTopPlayerTurn);
	}
	
	@Override
	public GameState play(ValidPlay validPlay, Board board){
		if(validPlay.unbox().isNextState()){
			return new GameStateMovingStrongs(isTopPlayerTurn());
		}
		board.play(validPlay, isTopPlayerTurn);
		numberOfWeaksLeft--;
		if(board.isGameDraw()){
			return new GameStateGameEndedDraw();
		}
		if(board.isTopTheWinner() || board.isBottomTheWinner()){
			return new GameStateGameEnded(board.isTopTheWinner());
		}
		if(numberOfWeaksLeft == 0){
			return new GameStateMovingStrongs(isTopPlayerTurn());
		}
		return this;
	}
	
	@Override
	public boolean isGameEnded() {
		return false;
	}

	@Override
	public GameState copy() {
		final GameStatePuttingWeaks copy = new GameStatePuttingWeaks(isTopPlayerTurn);
		copy.numberOfWeaksLeft = numberOfWeaksLeft;
		return copy;
	}

	@Override
	public Set<Integer> getAlreadyMovedOrEmptySet() {
		return new LinkedHashSet<Integer>();
	}

	@Override
	public int getStateId() {
		return GameState.GAME_PUTTING_WEAKS_ID;
	}
}
