package gameLogic.gameFlow.gameStates;

import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.Play;
import gameLogic.gameFlow.BoardListeners;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.PlayResult;

import java.awt.Point;
import java.util.LinkedHashSet;
import java.util.Set;

public class GameStateMovingStrongs implements GameState {

	private final Set<Piece> alreadyMoved;
	private Piece selected;
	private boolean shouldChangeState = false;
	private final boolean isTopPlayerTurn;

	public GameStateMovingStrongs(final boolean isTopPlayerTurn) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		alreadyMoved = new LinkedHashSet<Piece>();
	}

	private PlayResult alreadyMovedPlayResult(){
		return PlayResult.errorPieceAlreadyMoved();
	}

	@Override
	public String asStateUniqueName() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_MOVING_STRONGS;
		}
		return GameState.BOTTOM_PLAYER_MOVING_STRONGS;
	}

	private PlayResult cantMovePlayResult(){
		return PlayResult.errorCantMovePiece();
	}

	private void changeState() {
		shouldChangeState = true;
	}

	@Override
	public String getStateDescription() {
		if(isTopPlayerTurn) {
			return GameState.TOP_PLAYER_MOVING_STRONGS_DESCRIPTION;
		} else {
			return GameState.BOTTOM_PLAYER_MOVING_STRONGS_DESCRIPTION;
		}
	}

	@Override
	public boolean isBottomPlayerTurn() {
		return !isTopPlayerTurn;
	}

	public boolean isMovablePiece(final Piece p){
		final boolean isNotStrong = !p.isStrong();
		final boolean isNotPlayersTurnPiece = p.isTopPlayerPiece() && !isTopPlayerTurn;
		if(isNotStrong || isNotPlayersTurnPiece){
			return false;
		}
		return true;
	}

	@Override
	public boolean isPuttingStrongsTurn() {
		return false;
	}

	public boolean isThereAPieceSelected(){
		return selected != null;
	}


	@Override
	public boolean isTopPlayerTurn() {
		return isTopPlayerTurn;
	}


	private PlayResult moveStrongPiece(final int lineFrom, final int columnFrom, final int lineTo, final int columnTo, final Board board){
		final Piece piece = board.getPieceAt(lineFrom, columnFrom);

		if(lineFrom == lineTo && columnFrom == columnTo ) {
			return PlayResult.selectedPiece(piece);
		}

		if(!isMovablePiece(piece)) {
			return cantMovePlayResult();
		}
		if(alreadyMoved.contains(piece)) {
			return alreadyMovedPlayResult();
		}

		if (!board.moveStrongPiece(lineFrom, columnFrom, lineTo, columnTo)){
			return cantMovePlayResult();
		}
		alreadyMoved.add(piece);
		if(alreadyMoved.size() == GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE) {
			changeState();
		}
		return PlayResult.movedPiece(piece);
	}

	@Override
	public GameState nextState(final Board board, final BoardListeners listeners) {
		return new GameStatePuttingWeaks(!isTopPlayerTurn());
	}

	@Override
	public GameState nextStateIfChanged(final Board board, final BoardListeners listeners) {
		if(board.isTopTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.TOP_WON);
		}
		if(board.isBottomTheWinner()){
			return new GameStateGameEnded(GameStateGameEnded.BOTTOM_WON);
		}
		if(board.isGameDraw()){
			return new GameStateGameEnded(GameStateGameEnded.DRAW);
		}
		if(stateEnded()) {
			return nextState(board, listeners);
		}
		return this;
	}

	private PlayResult play(final int pieceLine, final int pieceColumn, final Board board){
		if(selected == null){
			final Piece piece = board.getPieceAt(pieceLine, pieceColumn);
			if(!isMovablePiece(piece)) {
				return cantMovePlayResult();
			}
			for (final Piece p : alreadyMoved) {
				if(piece == p) {
					return alreadyMovedPlayResult();
				}
			}
			selected = piece;
			return PlayResult.selectedPiece(selected);
		}

		final int line = board.getPieceLine(selected);
		final int column = board.getPieceColumn(selected);
		final PlayResult playResult = moveStrongPiece(line, column, pieceLine, pieceColumn, board);
		if(playResult.isSuccessful()) {
			selected= null;
		}
		return playResult;
	}

	@Override
	public PlayResult play(final Play play, final Board board){
		if(play.isMoveDirection()){
			return playTwoTimes(play, board);
		}

		return play(play.getLine(),play.getColumn(),board);
	}

	private PlayResult playTwoTimes(final Play play, final Board board){
		Point strongCoords;
		if(isTopPlayerTurn()) {
			strongCoords = board.getStrongTopById(play.getPieceId());
		}else{
			strongCoords = board.getStrongBottomId(play.getPieceId());
		}
		Play playSelectStrong = null;
		Play playMoveStrongTo = null;
		switch (play.getDirection()) {
		case Play.UP:
			playSelectStrong = new Play(strongCoords.y,strongCoords.x);
			playMoveStrongTo = new Play(strongCoords.y-1,strongCoords.x);
			break;
		case Play.DOWN:
			playSelectStrong = new Play(strongCoords.y,strongCoords.x);
			playMoveStrongTo = new Play(strongCoords.y+1,strongCoords.x);
			break;
		case Play.LEFT:
			playSelectStrong = new Play(strongCoords.y,strongCoords.x);
			playMoveStrongTo = new Play(strongCoords.y,strongCoords.x-1);
			break;
		case Play.RIGHT:
			playSelectStrong = new Play(strongCoords.y,strongCoords.x);
			playMoveStrongTo = new Play(strongCoords.y,strongCoords.x+1);
			break;
		default:
			break;
		}

		final PlayResult playFrom = play(playSelectStrong, board);
		if(!playFrom.isSuccessful()){
			return playFrom;
		}
		final PlayResult playTo = play(playMoveStrongTo, board);
		if(!playTo.isSuccessful()){
			return playTo;
		}
		return PlayResult.successfullPlay();
	}

	@Override
	public boolean stateEnded(){
		return shouldChangeState;
	}

}
