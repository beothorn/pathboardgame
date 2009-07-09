package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

public class GameStateGameEndedDraw extends GameStateGameEnded {

	public GameStateGameEndedDraw() {
		super(false);
	}

	@Override
	public ValidPlay validatePlay(final Play play,final Board board,final boolean isTopPlayerPlay)throws InvalidPlayException {
		if(play.isNextState())
			return board.validatePlay(play, isTopPlayerPlay);
		throw InvalidPlayException.gameAlreadyEndedInDraw();
	}
	
	@Override
	public GameState copy() {
		return new GameStateGameEndedDraw();
	}
	
	@Override
	public int getStateId() {
		return GameState.GAME_ENDED_DRAW_ID;
	}

}
