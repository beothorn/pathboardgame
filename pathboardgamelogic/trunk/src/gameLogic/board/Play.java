package gameLogic.board;

import java.io.Serializable;

public class Play implements Serializable{

	public static final char RIGHT = 'r';
	public static final char LEFT = 'l';
	public static final char DOWN = 'd';
	public static final char UP = 'u';

	public static final String NEXT_STATE = "p";
	private static final String NORMAL_PLAY_NOTATION = "[0-8]";
	private static final String MOVE_BY_ID_NOTATION = "m[0-3]["+RIGHT+LEFT+DOWN+UP+"]";

	private static final long serialVersionUID = 1L;
	public static final String MOVE = "m";

	private int column;
	private char direction;
	private int pieceId;
	private final boolean moveDirection;
	private final boolean nextState;
	private final boolean addPiece;

	public Play(final int column) {
		setColumn(column);
		addPiece = true;
		nextState = false;
		moveDirection = false;
	}

	public Play(final int strongId, final char direction){
		moveStrongInDirection(strongId, direction);
		moveDirection = true;
		nextState = false;
		addPiece = false;
	}
	
	public Play(final String play){
		String playLower = play.toLowerCase();
		if(playLower.matches(NEXT_STATE)){
			nextState = true;
			moveDirection = false;
			addPiece = false;
		}else if(playLower.matches(NORMAL_PLAY_NOTATION)){
			final int c = Integer.parseInt(playLower);
			setColumn(c);
			addPiece = true;
			nextState = false;
			moveDirection = false;
		}else if(playLower.matches(MOVE_BY_ID_NOTATION)){
			final int strongId = Integer.parseInt(playLower.substring(1, 2));
			final char direction = playLower.charAt(playLower.length()-1);
			moveStrongInDirection(strongId, direction);
			moveDirection = true;
			nextState = false;
			addPiece = false;
		}else{
			throw new IllegalArgumentException("Play string invalid: "+playLower);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if(!(obj instanceof Play)){
			return false;
		}
		return ((Play)obj).toString().equals(toString());
	}

	public int getColumn() {
		return column;
	}

	public char getDirection() {
		return direction;
	}

	public int getPieceId() {
		return pieceId;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public boolean isMoveDirection() {
		return moveDirection;
	}

	public boolean isNextState() {
		return nextState;
	}

	public boolean isAddPiece() {
		return addPiece;
	}
	
	private void moveStrongInDirection(final int strongId, final char direction){
		if(direction != UP && direction != DOWN && direction != LEFT && direction != RIGHT){
			throw new IllegalArgumentException("Direction not valid: "+direction);
		}
		if(strongId > 3){
			throw new IllegalArgumentException("Strong id invalid: "+strongId);
		}
		pieceId = strongId;
		this.direction = direction;
	}

	private void setColumn(final int column) {
		if(column > Board.BOARD_SIZE) {
			throw new IllegalArgumentException("column value is greater than board size");
		}
		this.column = column;
	}

	@Override
	public String toString() {
		if(isNextState()) {
			return NEXT_STATE;
		}
		if(isMoveDirection()){
			final String playDesc = MOVE+getPieceId()+getDirection();
			return playDesc;
		}
		return String.valueOf(column);
	}
}