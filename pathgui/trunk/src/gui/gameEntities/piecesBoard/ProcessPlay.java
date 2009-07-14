package gui.gameEntities.piecesBoard;

import gameEngine.gameMath.Point;
import gameLogic.Game;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameStateGameEnded;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;
import gameLogic.gameFlow.gameStates.StateVisitor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProcessPlay implements StateVisitor,MouseListener {

	private Play play;
	private Point startDrag;
	private Point endDrag;
	private final Game game;
	private final boolean isTopPlayerTurn;
	private final PiecesBoard piecesBoard;

	public ProcessPlay(final boolean isTopPlayerTurn, final Game game, final PiecesBoard piecesBoard) {
		this.isTopPlayerTurn = isTopPlayerTurn;
		this.game = game;
		this.piecesBoard = piecesBoard;
		game.getCurrentState().accept(this);
	}

	public Play getPlayOrNull(){
		return play;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {

	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		//		System.out.println(e);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		//		System.out.println(e);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		final int mouseX = e.getX();
		final int mouseY = e.getY();
		final double boardX1 = piecesBoard.getX();
		final double boardX2 = boardX1+piecesBoard.getBoardWidth();
		final double boardY1 = piecesBoard.getY();
		final double boardY2 = boardY1+piecesBoard.getBoardHeight();
		if(mouseX<boardX1){return;}
		if(mouseX>boardX2){return;}
		if(mouseY<boardY1){return;}
		if(mouseY>boardY2){return;}

		startDrag.setX((int) ((mouseX - boardX1)/ piecesBoard.getGridWidth()));
		startDrag.setY((int) ((mouseY - boardY1)/ piecesBoard.getGridHeight()));
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		final int mouseX = e.getX();
		final int mouseY = e.getY();
		final double boardX1 = piecesBoard.getX();
		final double boardX2 = boardX1+piecesBoard.getBoardWidth();
		final double boardY1 = piecesBoard.getY();
		final double boardY2 = boardY1+piecesBoard.getBoardHeight();
		if(mouseX<boardX1) {return;}
		if(mouseX>boardX2) {return;}
		if(mouseY<boardY1) {return;}
		if(mouseY>boardY2) {return;}
		final int pieceColumn = (int) ((mouseX - boardX1)/ piecesBoard.getGridWidth());
		final int pieceLine = (int) ((mouseY - boardY1)/ piecesBoard.getGridHeight());
		final Point endDrag = new Point(pieceColumn, pieceLine);
		getPlayOrNull();
		if(playOrNull == null){
			errorListener.error("Invalid play");
			return;
		}
		try {
			final ValidPlay validPlay = getCurrentGame().validatePlay(playOrNull, getCurrentGame().isTopPlayerTurn());
			getCurrentGame().play(validPlay);
		} catch (final InvalidPlayException invalidPlayException) {
			errorListener.error(invalidPlayException.getMessage());
		}
		refreshBoard();
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
		final int line = (int)startDrag.getY();
		final int column = (int)startDrag.getX();
		final java.awt.Point strongToMovePosition = new java.awt.Point(line,column);
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
