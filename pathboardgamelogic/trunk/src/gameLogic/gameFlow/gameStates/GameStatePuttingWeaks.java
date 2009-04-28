package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

public class GameStatePuttingWeaks implements GameState {

	private int numberOfWeaksLeft;
	private final boolean isTopPlayerTurn;

	public GameStatePuttingWeaks(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		numberOfWeaksLeft = GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_PUTTING_WEAKS_DESCRIPTION;
		}
		return GameState.BOTTOM_PLAYER_PUTTING_WEAKS_DESCRIPTION;
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
	public ValidPlay validatePlay(Play play, Board board)throws InvalidPlayException {
		if(play.isMoveDirection()){
			throw new InvalidPlayException("You can't move a strong piece. You must add "+GameState.NUMBER_OF_WEAK_PIECES_TO_PUT+" weak pieces or pass the turn.");
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
			return new GameStateGameEnded(GameStateGameEnded.DRAW);
		}
		if(board.isTopTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.TOP_WON);
		}
		if(board.isBottomTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.BOTTOM_WON);
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
}
