package gameLogic.board;

public class InvalidPlayStringException extends Exception {

	public InvalidPlayStringException(String invalidString) {
		super("Play string is invalid: "+invalidString);
	}

}
