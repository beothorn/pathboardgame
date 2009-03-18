package gameLogic.gameFlow.gameStates;

import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.Play;
import gameLogic.gameFlow.BoardListeners;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.PlayResult;

public class GameStatePuttingWeaks implements GameState {

	private int numberOfWeaksLeft;
	private boolean shouldChangeState;
	private final boolean isTopPlayerTurn;

	public GameStatePuttingWeaks(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		numberOfWeaksLeft = GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;
	}

	private PlayResult addPiece(final int pieceColumn, final Board board) {
		final boolean addPieceResult = board.addPiece(getWeakPiece(), pieceColumn);
		if(addPieceResult){
			numberOfWeaksLeft--;
		} else {
			return invalidMoveYouCantDoThisPlayResult();
		}
		if(numberOfWeaksLeft == 0){
			changeState();
		}
		final boolean successful = true;
		return new PlayResult(successful);
	}

	@Override
	public String asStateUniqueName() {
		if(isTopPlayerTurn){
			return GameState.TOP_PLAYER_PUTTING_WEAKS;
		}
		return GameState.BOTTOM_PLAYER_PUTTING_WEAKS;
	}

	private void changeState() {
		shouldChangeState = true;
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_PUTTING_WEAKS_DESCRIPTION;
		}
		return GameState.BOTTOM_PLAYER_PUTTING_WEAKS_DESCRIPTION;
	}

	private Piece getWeakPiece() {
		if(isTopPlayerTurn) {
			return Piece.getTopWeakPiece();
		}
		return Piece.getBottomWeakPiece();
	}

	private PlayResult invalidMoveYouCantDoThisPlayResult(){
		final boolean successful = false;
		final PlayResult playResult = new PlayResult(successful);
		final String errorMessage = PlayResult.MESSAGE_YOU_CANT_DO_THIS+"\n"+getStateDescription();
		playResult.setErrorMessage(errorMessage);
		return playResult;
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
	public GameState nextState(final Board board, final BoardListeners listeners) {
		return new GameStateMovingStrongs(isTopPlayerTurn());
	}

	@Override
	public GameState nextStateIfChanged(final Board board, final BoardListeners listeners) {
		if(board.isGameDraw()){
			return new GameStateGameEnded(GameStateGameEnded.DRAW);
		}
		if(board.isTopTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.TOP_WON);
		}
		if(board.isBottomTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.BOTTOM_WON);
		}
		if(stateEnded()) {
			return nextState(board, listeners);
		}
		return this;
	}

	@Override
	public PlayResult play(final Play play, final Board board){
		return addPiece(play.getColumn(), board);
	}

	@Override
	public boolean stateEnded() {
		return shouldChangeState;
	}
}
