package gameLogic.gameFlow;

import gameLogic.Piece;

public class PlayResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String MESSAGE_FINISH_YOUR_PLAY = "You must finish your play first.";
	private static final String MESSAGE_YOU_ALREADY_MOVED = "You already moved this piece";
	private static final String MESSAGE_YOU_CANT_ADD = "You can't add a piece right now.";

	private static final String MESSAGE_YOU_CANT_MOVE = "You can't move this piece";
	private static final String MESSAGE_WAIT_YOUR_TURN = "Wait your turn.";


	private final boolean success;
	private final String errorMessage;
	private Piece selectedPiece;
	private Piece movedStrongPiece;

	private PlayResult(final boolean successful,final String errorMessage,final Piece selectedPiece, final Piece movedStrongPiece) {
		success = successful;
		this.errorMessage = errorMessage;
		this.selectedPiece = selectedPiece;
		this.movedStrongPiece = movedStrongPiece;
	}

	private PlayResult(boolean successful, String errorMessage) {
		this(successful,errorMessage,null,null);
	}

	public PlayResult() {
		this(true,"",null,null);
	}
	
	public PlayResult(Piece piece, boolean isSelectingPiece) {
		this(true,"",null,null);
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public Piece getMovedStrongPiece() {
		return movedStrongPiece;
	}
	
	public Piece getSelectedPiece() {
		return selectedPiece;
	}
	
	public boolean isSuccessful() {
		return success;
	}

	public static PlayResult errorMustFinishPlay() {
		final boolean successful = false;
		String errorMessage = PlayResult.MESSAGE_FINISH_YOUR_PLAY;
		return new PlayResult(successful,errorMessage);
	}

	public static PlayResult successfullPlay() {
		return new PlayResult();
	}

	public static PlayResult errorMustWaitForYourTurn() {
		final boolean successful = false;
		String errorMessage = PlayResult.MESSAGE_WAIT_YOUR_TURN;
		return new PlayResult(successful,errorMessage);
	}

	public static PlayResult errorGameEnded(String stateDescription) {
		final boolean successful = false;
		return new PlayResult(successful,stateDescription);
	}

	public static PlayResult errorPieceAlreadyMoved() {
		final boolean successful = false;
		String errorMessage = PlayResult.MESSAGE_YOU_ALREADY_MOVED;
		return new PlayResult(successful,errorMessage);
	}

	public static PlayResult errorCantMovePiece() {
		final boolean successful = false;
		String errorMessage = PlayResult.MESSAGE_YOU_CANT_MOVE;
		return new PlayResult(successful,errorMessage);
	}

	public static PlayResult selectedPiece(Piece piece) {
		PlayResult successfullPlay = PlayResult.successfullPlay();
		successfullPlay.selectedPiece = piece;
		return successfullPlay;
	}

	public static PlayResult movedPiece(Piece piece) {
		PlayResult successfullPlay = PlayResult.successfullPlay();
		successfullPlay.movedStrongPiece = piece;
		return successfullPlay;
	}

	public static PlayResult errorYouAddAPiece() {
		final boolean successful = false;
		String errorMessage = PlayResult.MESSAGE_YOU_CANT_ADD;
		return new PlayResult(successful,errorMessage);
	}
}
