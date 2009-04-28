package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;

import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateMovingStrongs implements GameState {

	private final Set<Integer> alreadyMoved;
	private Piece selected;
	private final boolean isTopPlayerTurn;

	public GameStateMovingStrongs(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		alreadyMoved = new LinkedHashSet<Integer>();
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_MOVING_STRONGS_DESCRIPTION;
		} else {
			return GameState.BOTTOM_PLAYER_MOVING_STRONGS_DESCRIPTION;
		}
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return !isTopPlayerTurn;
	}

	@Override
	public boolean isPuttingStrongsTurn() {
		return false;
	}

	public boolean isThereAPieceSelected(){
		return selected != null;
	}

	@Override
	public boolean isTopPlayerTurn() {
		return isTopPlayerTurn;
	}

	@Override
	public ValidPlay validatePlay(Play play, Board board)throws InvalidPlayException {
		if(play.isAddPiece()){
			throw new InvalidPlayException("You can't add a piece. You need to move "+GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE+" or pass the turn to putting weaks.");
		}
		if(alreadyMoved.contains(play.getPieceId())) {
			throw new InvalidPlayException("The strong piece "+play.getPieceId()+" was already moved this turn");
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
			return new GameStateGameEnded(GameStateGameEnded.TOP_WON);
		}
		if(board.isBottomTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.BOTTOM_WON);
		}
		if(board.isGameDraw()){
			return new GameStateGameEnded(GameStateGameEnded.DRAW);
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
}
