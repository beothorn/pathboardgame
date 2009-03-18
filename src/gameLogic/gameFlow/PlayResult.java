package gameLogic.gameFlow;

import gameLogic.Piece;

public class PlayResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final String MESSAGE_FINISH_YOUR_PLAY = "You must finish your play first.";
	public static final String MESSAGE_YOU_ALREADY_MOVED = "You already moved this piece";
	public static final String MESSAGE_YOU_CANT_ADD = "You can't add a piece right now.";

	public static final String MESSAGE_YOU_CANT_DO_THIS = "You can't do this.";
	public static final String MESSAGE_YOU_CANT_MOVE = "You can't move this piece";
	public static final String MESSAGE_YOU_CANT_MOVE_A_STRONG = "You can't move a strong piece right now";
	public static final String MESSAGE_WAIT_YOUR_TURN = "Wait your turn.";


	private boolean success;
	private String errorMessage;
	private Piece changedPiece;
	private boolean selected;


	private boolean movedStrongPiece;

	public PlayResult(final boolean successful) {
		success = successful;
	}

	public Piece getChangedPiece() {
		return changedPiece;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public boolean isMovedStrongPiece() {
		return movedStrongPiece;
	}
	public boolean isSelected() {
		return selected;
	}
	public boolean isSuccessful() {
		return success;
	}
	public void setChangedPiece(final Piece changedPiece) {
		this.changedPiece = changedPiece;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setMovedStrongPiece(final boolean movedStrongPiece) {
		this.movedStrongPiece = movedStrongPiece;
	}

	public void setSelected(final boolean selected) {
		this.selected = selected;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}

}
