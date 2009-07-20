package gui.gameEntities;

import gameEngine.JGamePanel;
import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;
import gameLogic.Game;
import gameLogic.PhaseChangeListener;
import gameLogic.gameFlow.gameStates.GameStateGameEnded;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingStrongs;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;
import gameLogic.gameFlow.gameStates.StateVisitor;
import gui.GameLayoutDefinitions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PuttingPiecesDisplay implements MouseMotionListener,PhaseChangeListener,StateVisitor{

	private final Entity entityPieceWeak;
	private final Entity entityPieceStrong;

	final static private double gridWidth = GameLayoutDefinitions.gridSize;
	public static PuttingPiecesDisplay getBottomDisplay(final JGamePanel gamePanel,final Point boardPosition) {
		return new 	PuttingPiecesDisplay(gamePanel, boardPosition, GameLayoutDefinitions.pieceBottom, GameLayoutDefinitions.pieceStrongBottom,false);
	}

	public static PuttingPiecesDisplay getTopDisplay(final JGamePanel gamePanel,final Point boardPosition) {
		return new 	PuttingPiecesDisplay(gamePanel, boardPosition, GameLayoutDefinitions.pieceTop, GameLayoutDefinitions.pieceStrongTop,true);
	}

	private final int x;
	private final int y;
	private final boolean isTop;

	private PuttingPiecesDisplay(final JGamePanel gamePanel,final Point position, final String weakPieceSprite, final String strongPieceSprite,final boolean isTop) {
		this.isTop = isTop;
		x = (int)position.getX();
		y = (int)position.getY();
		gamePanel.addMouseMotionListener(this);
		entityPieceWeak = new Entity();
		entityPieceWeak.setPosition(position);
		entityPieceWeak.setSprite(weakPieceSprite);
		entityPieceWeak.setVisible(false);
		gamePanel.addGameElement(entityPieceWeak);

		entityPieceStrong = new Entity();
		entityPieceStrong.setPosition(position);
		entityPieceStrong.setSprite(strongPieceSprite);
		entityPieceStrong.setVisible(false);
		gamePanel.addGameElement(entityPieceStrong);
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
		final int mouseX = e.getX();
		if(mouseX < x||mouseX>x+GameLayoutDefinitions.BOARD_SIZE){
			return;
		}
		final int wichSquare = (int)((mouseX-x)/gridWidth);
		final int pieceX = (int) (wichSquare*gridWidth)+x;
		entityPieceWeak.setPosition(pieceX,y);
		entityPieceStrong.setPosition(pieceX,y);
	}

	@Override
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded) {
		entityPieceWeak.setVisible(false);
		entityPieceStrong.setVisible(false);
	}

	@Override
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs) {
		entityPieceWeak.setVisible(false);
		entityPieceStrong.setVisible(false);
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		entityPieceWeak.setVisible(false);
		if(gameStatePuttingStrongs.isTopPlayerTurn() && isTop || gameStatePuttingStrongs.isBottomPlayerTurn() && !isTop) {
			entityPieceStrong.setVisible(true);
		}else{
			entityPieceStrong.setVisible(false);
		}
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		entityPieceStrong.setVisible(false);
		if(gameStatePuttingWeaks.isTopPlayerTurn() && isTop || gameStatePuttingWeaks.isBottomPlayerTurn() && !isTop) {
			entityPieceWeak.setVisible(true);
		} else {
			entityPieceWeak.setVisible(false);
		}
	}

}
