package gameLogic.gameFlow.gameStates;

import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.Play;
import gameLogic.gameFlow.BoardListeners;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.PlayResult;

public class GameStatePuttingStrongs implements GameState {

	private int numberOfStrongsLeft;
	private final boolean isTopPlayerTurn;
	private boolean shouldChangeState = false;
	private final boolean isFirstState;

	public GameStatePuttingStrongs(final boolean isTopPlayerTurn, final boolean isFirstState) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		this.isFirstState = isFirstState;
		numberOfStrongsLeft = GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;
	}

	private PlayResult addPiece(final int pieceColumn,final Board board){
		final Piece strongPiece = getStrongPiece();
		final boolean addPieceResult = board.addPiece(strongPiece, pieceColumn);
		if(addPieceResult){
			numberOfStrongsLeft--;
		} else {
			return invalidMoveYouCantDoThisPlayResult();
		}
		if(numberOfStrongsLeft == 0){
			changeState();
		}
		final boolean successful = true;
		return new PlayResult(successful);
	}

	@Override
	public String asStateUniqueName() {
		if(isTopPlayerTurn){
			return GameState.TOP_PLAYER_PUTTING_STRONGS;
		}
		return GameState.BOTTOM_PLAYER_PUTTING_STRONGS;
	}

	private void changeState() {
		shouldChangeState = true;
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_PUTTING_STRONGS_DESCRIPTION;
		} else {
			return GameState.BOTTOM_PLAYER_PUTTING_STRONGS_DESCRIPTION;
		}
	}

	private Piece getStrongPiece() {
		if(isTopPlayerTurn) {
			return Piece.getTopStrongPiece();
		}
		return Piece.getBottomStrongPiece();
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
		return true;
	}

	@Override
	public boolean isTopPlayerTurn() {
		return isTopPlayerTurn;
	}

	@Override
	public GameState nextState(final Board board,final BoardListeners listeners) {
		if(isFirstState){
			if(!stateEnded()) {
				return null;
			}
			final boolean nextIsFirstState = false;
			return new GameStatePuttingStrongs(!isTopPlayerTurn(),nextIsFirstState);
		}
		return new GameStatePuttingWeaks(!isTopPlayerTurn());
	}

	@Override
	public GameState nextStateIfChanged(final Board board, final BoardListeners listeners) {
		if(stateEnded()){
			return nextState(board, listeners);
		}
		return this;
	}

	@Override
	public PlayResult play(final Play play,final Board board){
		return addPiece(play.getColumn(),board);
	}

	@Override
	public boolean stateEnded() {
		return shouldChangeState;
	}
}
