package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateMovingStrongs implements GameState {

	private final Set<Piece> alreadyMoved;
	private final boolean isTopPlayerTurn;

	public GameStateMovingStrongs(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		alreadyMoved = new LinkedHashSet<Piece>();
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
		final Piece piece = board.getStrongPiece(play.getPieceId(), isTopPlayerTurn);
		if(alreadyMoved.contains(piece)) {
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
		final int pieceId = validPlay.unbox().getPieceId();
		final Piece p = board.getStrongPiece(pieceId, isTopPlayerTurn);
		alreadyMoved.add(p);
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
	public Set<Piece> getAlreadyMovedOrEmptySet() {
		return alreadyMoved;
	}

	@Override
	public void accept(final StateVisitor stateVisitor) {
		stateVisitor.onMovingStrongs(this);
	}
}
