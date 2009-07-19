package gui.gameEntities;

import gameEngine.JGamePanel;
import gameEngine.gameMath.Point;
import gameLogic.Game;
import gameLogic.PhaseChangeListener;
import gameLogic.gameFlow.gameStates.GameStateGameEnded;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;
import gameLogic.gameFlow.gameStates.StateVisitor;
import gui.gameEntities.piecesBoard.entityPiece.EntityPieceTopWeak;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class TopPuttingPiecesDisplay implements MouseMotionListener,PhaseChangeListener,StateVisitor{

	private final EntityPieceTopWeak entityPieceTopWeak;

	public TopPuttingPiecesDisplay(final JGamePanel gamePanel) {
		entityPieceTopWeak = new EntityPieceTopWeak(null);//TODO:EntityPiece is a mess
		gamePanel.addGameElement(entityPieceTopWeak);
		gamePanel.addStepAction(entityPieceTopWeak.getStepAction());
	}

	@Override
	public void changedPhase(final Game game) {
		game.getCurrentState().accept(this);
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		//Not used
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		entityPieceTopWeak.setPointToGo(new Point(e.getX(),e.getY()));
	}

	@Override
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded) {
		entityPieceTopWeak.setVisible(false);
	}

	@Override
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs) {
		entityPieceTopWeak.setVisible(false);
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		entityPieceTopWeak.setVisible(false);
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		if(gameStatePuttingWeaks.isTopPlayerTurn()) {
			entityPieceTopWeak.setVisible(true);
		} else {
			entityPieceTopWeak.setVisible(false);
		}
	}

}
