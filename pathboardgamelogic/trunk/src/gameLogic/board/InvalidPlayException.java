package gameLogic.board;

public class InvalidPlayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE_FINISH_YOUR_PLAY = "You must finish your play first.";
	public static final String MESSAGE_YOU_ALREADY_MOVED = "You already moved this piece";
	public static final String MESSAGE_YOU_CANT_ADD = "You can't add a piece right now.";

	public static final String MESSAGE_YOU_CANT_MOVE = "You can't move this piece";
	public static final String MESSAGE_GAME_IS_LOCKED = "Wait your turn.";
	
	private final String message;
	
	
	
	public InvalidPlayException(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
