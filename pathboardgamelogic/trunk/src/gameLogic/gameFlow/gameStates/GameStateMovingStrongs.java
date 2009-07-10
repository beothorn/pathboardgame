package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateMovingStrongs implements GameState {

	private final Set<Integer> alreadyMoved;
	private final boolean isTopPlayerTurn;

	public GameStateMovingStrongs(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		alreadyMoved = new LinkedHashSet<Integer>();
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
		if(play.isAddPiece()){
			throw InvalidPlayException.cantAddPieceWhenMovingStrongs(GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE); 
		}
		if(alreadyMoved.contains(play.getPieceId())) {
			throw InvalidPlayException.cantMovePieceAlreadyMoved(play.getPieceId());
		}
		return board.validatePlay(play, isTopPlayerTurn);
	}
	
	@Override
	public GameState play(ValidPlay validPlay, Board board){
		if(validPlay.unbox().isNextState()){
			return new GameStatePuttingWeaks(!isTopPlayerTurn());
		}
		board.play(validPlay, isTopPlayerTurn);
		alreadyMoved.add(validPlay.unbox().getPieceId());
		if(board.isTopTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.RESULT_TOP_WIN);
		}
		if(board.isBottomTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.RESULT_BOTTOM_WIN);
		}
		if(board.isGameDraw()){
			return new GameStateGameEnded(GameStateGameEnded.RESULT_DRAW);
		}
		if(alreadyMoved.size() == GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE) {
			return new GameStatePuttingWeaks(!isTopPlayerTurn());
		}
		return this;
	}
	
	@Override
	public boolean isGameEnded() {
		return false;
	}

	@Override
	public GameState copy() {
		final GameStateMovingStrongs copy = new GameStateMovingStrongs(isTopPlayerTurn);
		copy.alreadyMoved.addAll(alreadyMoved);
		return copy;
	}

	@Override
	public Set<Integer> getAlreadyMovedOrEmptySet() {
		return alreadyMoved;
	}

	@Override
	public void visit(final StateVisitor stateVisitor) {
		stateVisitor.visit(this);
	}
}
