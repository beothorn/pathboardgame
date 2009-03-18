package gameLogic;

import java.awt.Point;
import java.io.Serializable;

public class Play implements Serializable{

	public static final char RIGHT = 'r';
	public static final char LEFT = 'l';
	public static final char DOWN = 'd';
	public static final char UP = 'u';

	public static final String NEXT_STATE = "PASS_PLAY";
	private static final String NORMAL_PLAY_NOTATION = "\\([0-"+(Board.BOARD_SIZE-1)+"],[0-"+(Board.BOARD_SIZE-1)+"]\\)";
	private static final String MOVE_BY_ID_NOTATION = "mov[0-9]["+RIGHT+LEFT+DOWN+UP+"]";

	private static final long serialVersionUID = 1L;
	public static final String MOVE = "mov";

	private int column;
	private char direction;
	private int line;
	private boolean moveDirection = false;
	private boolean nextState = false;
	private int pieceSequenceNumber;

	public Play(final int line, final int column) {
		setCoords(line, column);
	}

	public Play(final int strongSequenceNumber, final char direction){
		moveStrongInDirection(strongSequenceNumber, direction);
	}
	
	public Play(final String play) {
		if(play.matches(NEXT_STATE)){
			setNextStatePlay();
		}else if(play.matches(NORMAL_PLAY_NOTATION)){
			final int comaPosition = play.indexOf(',');
			final int x = Integer.parseInt(play.substring(1, comaPosition));
			final int y = Integer.parseInt(play.substring(comaPosition+1,play.length()-1));
			setCoords(x,y);
		}else if(play.matches(MOVE_BY_ID_NOTATION)){
			final int strongId = Integer.parseInt(play.substring(3, 4));
			final char direction = play.charAt(play.length()-1);
			moveStrongInDirection(strongId, direction);
		}else{
			throw new IllegalArgumentException("Play string invalid: "+play);
		}
	}

	public Play(final Point positionOnBoard) {
		this(positionOnBoard.x,positionOnBoard.y);
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

	public int getLine() {
		return line;
	}

	public int getPieceSequenceNumber() {
		return pieceSequenceNumber;
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

	private void moveStrongInDirection(final int strongId, final char direction){
		if(direction != UP && direction != DOWN && direction != LEFT && direction != RIGHT){
			throw new IllegalArgumentException("Direction not valid: "+direction);
		}
		if(strongId > 9){
			throw new IllegalArgumentException("Strong id invalid: "+strongId);
		}
		pieceSequenceNumber = strongId;
		this.direction = direction;
		moveDirection = true;
		nextState = false;
	}

	private void setColumn(final int column) {
		if(column > Board.BOARD_SIZE) {
			throw new IllegalArgumentException("column value is greater than board size");
		}
		this.column = column;
		setIsPlayWithCoords();
	}

	private void setCoords(final int line, final int column) {
		setLine(line);
		setColumn(column);
	}

	private void setIsPlayWithCoords() {
		nextState = false;
		moveDirection = false;
	}

	private void setLine(final int line) {
		if(line > Board.BOARD_SIZE) {
			throw new IllegalArgumentException("line value is greater than board size");
		}
		this.line = line;
		setIsPlayWithCoords();
	}

	private void setNextStatePlay() {
		nextState = true;
		moveDirection = false;
	}

	@Override
	public String toString() {
		if(isNextState()) {
			return NEXT_STATE;
		}
		if(isMoveDirection()){
			final String playDesc = "mov"+getPieceSequenceNumber()+getDirection();
			return playDesc;
		}
		return "("+line+","+column+")";
	}
}