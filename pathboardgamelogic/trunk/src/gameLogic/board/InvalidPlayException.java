package gameLogic.board;




public class InvalidPlayException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InvalidPlayException(final String message) {
		super(message);
	}
	
	public static InvalidPlayException cantAddPieceWhenMovingStrongs(final int numberOfStrongsToMove){
		return new InvalidPlayException("You can't add a piece. You need to move "+numberOfStrongsToMove+" or pass the turn to putting weaks.");
	}

	public static InvalidPlayException cantMoveStrongWhenPuttingStrongs(final int numberOfStrongsToPut) {
		return new InvalidPlayException("You can't move a strong piece. You need to put "+numberOfStrongsToPut+" strong pieces");
	}

	public static InvalidPlayException cantMoveStrongWhenAddingWeaks(final int numberOfWeaksToPut) {
		return new InvalidPlayException("You can't move a strong piece. You must add "+numberOfWeaksToPut+" weak pieces or pass the turn.");
	}

	public static InvalidPlayException cantAddAPiece(final int column) {
		 return new InvalidPlayException("You can' add a piece in the column "+column);
	}

	public static InvalidPlayException cantMoveAPieceUp(final int pieceId) {
		return new InvalidPlayException("You can' move "+pieceId+" up.");
	}
	
	public static InvalidPlayException cantMoveAPieceDown(final int pieceId) {
		return new InvalidPlayException("You can' move "+pieceId+" down.");
	}
	
	public static InvalidPlayException cantMoveAPieceLeft(final int pieceId) {
		return new InvalidPlayException("You can' move "+pieceId+" left.");
	}
	
	public static InvalidPlayException cantMoveAPieceRight(final int pieceId) {
		return new InvalidPlayException("You can' move "+pieceId+" right.");
	}

	public static InvalidPlayException invalidDierction(final char direction) {
		return new InvalidPlayException("Invalid direction "+direction);
	}

	public static InvalidPlayException cantMovePieceAlreadyMoved(final int pieceId) {
		return new InvalidPlayException("The strong piece "+pieceId+" was already moved this turn");
	}

	public static InvalidPlayException cantSkipPuttingStrongs(final int numberOfStrongPiecesToPut) {
		return new InvalidPlayException("Can't skip the putting strongs turn. You need to put mmore "+numberOfStrongPiecesToPut+" strong pieces.");
	}

	public static InvalidPlayException gameIsLocked() {
		return new InvalidPlayException("Wait your turn.");
	}

	public static InvalidPlayException gameAlreadyEnded(final String winner) {
		return new InvalidPlayException("Game ended. "+winner);
	}

	public static InvalidPlayException itsNotYourTurn(boolean isTopPlayerTurn) {
		if(isTopPlayerTurn){
			return new InvalidPlayException("It is top players turn.");
		}else{
			return new InvalidPlayException("It is bottom players turn.");
		}
	}

	public static InvalidPlayException gameAlreadyEnded(boolean isTopPlayerTurn) {
		if(isTopPlayerTurn){
			return gameAlreadyEnded("Top wins");
		}
		return gameAlreadyEnded("Bottom wins");
	}

	public static InvalidPlayException gameAlreadyEndedInDraw() {
		return new InvalidPlayException("Game Draw.");
	}
}
