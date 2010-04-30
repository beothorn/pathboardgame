package gameLogic.board;

public class InvalidPlayStringException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPlayStringException(String invalidString) {
		super("Play string is invalid: "+invalidString);
	}

}
