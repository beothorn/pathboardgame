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

	private final Point startDrag = new Point();
	private Point endDrag = new Point();
	private final Game game;
	private final boolean playingAsTop;
	private final PiecesBoard piecesBoard;
	private final ErrorListener errorListener;

	public ProcessPlay(final boolean playingAsTop, final Game game,final PiecesBoard piecesBoard,final ErrorListener errorListener) {
		this.playingAsTop = playingAsTop;
		this.game = game;
		this.piecesBoard = piecesBoard;
		this.errorListener = errorListener;
	}

	private boolean canProcessPlay(){
		return playingAsTop && game.getCurrentState().isTopPlayerTurn() || !playingAsTop && game.getCurrentState().isBottomPlayerTurn();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {}

	@Override
	public void mouseEntered(final MouseEvent e) {}

	@Override
	public void mouseExited(final MouseEvent e) {}

	@Override
	public void mousePressed(final MouseEvent e) {
		if(!canProcessPlay()) {
			return;
		}
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
		if(!canProcessPlay()) {
			return;
		}
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
		endDrag = new Point(pieceColumn, pieceLine);
		visitToKnowHowToPlayAndPlay();
		piecesBoard.refreshBoard(game.getBoard(),game.getCurrentState().getAlreadyMovedOrEmptySet());
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
		if(playingAsTop && !strongToMove.isTopPlayerStrongPiece()){
			return;
		}
		if(!playingAsTop && !strongToMove.isBottomPlayerStrongPiece()){
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

		tryToPlay(new Play(strongToMove.getId(), direction));
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		tryToPlay(new Play((int)endDrag.getX()));
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		tryToPlay(new Play((int)endDrag.getX()));
	}

	private void tryToPlay(final Play play){
		if(play == null){
			errorListener.error("Invalid play");
			return;
		}
		try {
			final ValidPlay validPlay = game.validatePlay(play, playingAsTop);
			game.play(validPlay);
		} catch (final InvalidPlayException invalidPlayException) {
			errorListener.error(invalidPlayException.getMessage());
		}
	}

	public void visitToKnowHowToPlayAndPlay(){
		game.getCurrentState().accept(this);
	}
}
