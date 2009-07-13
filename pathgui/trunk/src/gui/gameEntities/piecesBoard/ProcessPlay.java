package gui.gameEntities.piecesBoard;

import gameEngine.gameMath.Point;
import gameLogic.Game;
import gameLogic.board.Play;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameStateGameEnded;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;
import gameLogic.gameFlow.gameStates.StateVisitor;

public class ProcessPlay implements StateVisitor {

	private Play play;
	private final Point startDrag;
	private final Point endDrag;
	private final Game game;
	private final boolean isTopPlayerTurn;

	public ProcessPlay(final Point startDrag, final Point endDrag,final boolean isTopPlayerTurn, final Game game) {
		this.startDrag = startDrag;
		this.endDrag = endDrag;
		this.isTopPlayerTurn = isTopPlayerTurn;
		this.game = game;
		game.getCurrentState().accept(this);
	}

	public Play getPlayOrNull(){
		return play;
	}

	@Override
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded) {
		//Nothing to do
	}

	@Override
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs) {
		if(startDrag.getX() != endDrag.getX()&&startDrag.getY() != endDrag.getY() ) {
			return;
		}
		final java.awt.Point strongToMovePosition = new java.awt.Point((int)startDrag.getX(),(int)startDrag.getY());
		final Piece strongToMove = game.getBoard().getPieceAt(strongToMovePosition);
		if(isTopPlayerTurn && !strongToMove.isTopPlayerStrongPiece()){
			return;
		}
		if(!isTopPlayerTurn && !strongToMove.isBottomPlayerStrongPiece()){
			return;
		}

		char direction = 'x';
		if(startDrag.getX() > endDrag.getX()){
			direction = Play.LEFT;
		}
		if(startDrag.getX() < endDrag.getX()){
			direction = Play.RIGHT;
		}
		if(startDrag.getY() > endDrag.getY()){
			direction = Play.UP;
		}
		if(startDrag.getY() < endDrag.getY()){
			direction = Play.DOWN;
		}

		play = new Play(strongToMove.getId(), direction);
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		play = new Play((int)endDrag.getX());
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		play = new Play((int)endDrag.getX());
	}

}
