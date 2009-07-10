package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateGameEnded implements GameState {

	public static final int RESULT_TOP_WIN = 1;
	public static final int RESULT_BOTTOM_WIN = 2;
	public static final int RESULT_DRAW = 3;
	private final int gameResult;

	public GameStateGameEnded(final int gameResult) {
		this.gameResult = gameResult;
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return false;
	}
	
	public boolean isTopTheWinner(){
		return gameResult == RESULT_TOP_WIN;
	}
	
	public boolean isBottomTheWinner(){
		return gameResult == RESULT_BOTTOM_WIN;
	}
	
	public boolean isGameDraw(){
		return gameResult == RESULT_DRAW;
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
		if(gameResult == RESULT_TOP_WIN)
			throw InvalidPlayException.gameAlreadyEnded(true);
		if(gameResult == RESULT_BOTTOM_WIN)
			throw InvalidPlayException.gameAlreadyEnded(false);
		throw InvalidPlayException.gameAlreadyEndedInDraw();
	}

	@Override
	public boolean isGameEnded() {
		return true;
	}

	@Override
	public GameState copy() {
		return new GameStateGameEnded(gameResult);
	}

	@Override
	public Set<Piece> getAlreadyMovedOrEmptySet() {
		return new LinkedHashSet<Piece>();
	}

	@Override
	public void accept(final StateVisitor stateVisitor) {
		stateVisitor.onGameEnded(this);
	}
}
