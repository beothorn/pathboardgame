package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

public class GameStatePuttingStrongs implements GameState {

	private final boolean isTopPlayerTurn;
	private final boolean isFirstState;

	public GameStatePuttingStrongs(final boolean isTopPlayerTurn, final boolean isFirstState) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		this.isFirstState = isFirstState;
	}

	@Override
	public ValidPlay validatePlay(Play play, Board board)throws InvalidPlayException {
		if(play.isMoveDirection()){
			throw new InvalidPlayException("You can't move a strong piece. You need to put "+GameState.NUMBER_OF_STRONG_PIECES_TO_PUT+" strong pieces");
		}
		if(play.isNextState()){
			throw new InvalidPlayException("Can't skip the putting strongs turn. You need to put "+GameState.NUMBER_OF_STRONG_PIECES_TO_PUT+" strong pieces");
		}
		return board.validatePlay(play, isTopPlayerTurn);
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_PUTTING_STRONGS_DESCRIPTION;
		} else {
			return GameState.BOTTOM_PLAYER_PUTTING_STRONGS_DESCRIPTION;
		}
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
}
