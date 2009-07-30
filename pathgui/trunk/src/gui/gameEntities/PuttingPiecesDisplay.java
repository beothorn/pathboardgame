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

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PuttingPiecesDisplay implements MouseMotionListener,PhaseChangeListener,StateVisitor{

	private final Entity entityPieceWeak;
	private final Entity entityPieceStrong;

	private final int x;
	private final int y;
	private final boolean isTop;
	private final int boardSize;
	private final int gridWidth;
	private boolean weakVisible = false;
	private boolean strongVisible = false;
	private final Point boardPosition;

	public PuttingPiecesDisplay(final JGamePanel gamePanel,final Point position, final String weakPieceSprite, final String strongPieceSprite,final boolean isTop,final Point boardPosition,final int boardSize, final int gridWidth) {
		this.boardPosition = boardPosition;
		this.boardSize = boardSize;
		this.gridWidth = gridWidth;
		this.isTop = isTop;
		x = (int)position.getX();
		y = (int)position.getY();
		gamePanel.addMouseMotionListener(this);
		entityPieceWeak = new Entity();
		entityPieceWeak.setPosition(position);
		entityPieceWeak.setSprite(weakPieceSprite);
		setWeakInvisible();
		gamePanel.addGameElement(entityPieceWeak);

		entityPieceStrong = new Entity();
		entityPieceStrong.setPosition(position);
		entityPieceStrong.setSprite(strongPieceSprite);
		setStrongInvisible();
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
		final int mouseY = e.getY();

		if(mouseX < boardPosition.getX()){
			setAllInvisible();
			return;
		}
		if(mouseX >= boardPosition.getX()+boardSize){
			setAllInvisible();
			return;
		}
		if(mouseY < boardPosition.getY()){
			setAllInvisible();
			return;
		}
		if(mouseY >= boardPosition.getY()+boardSize){
			setAllInvisible();
			return;
		}


		final int wichSquare = (mouseX-x)/gridWidth;
		final int pieceX = wichSquare*gridWidth+x;
		entityPieceWeak.setVisible(weakVisible);
		entityPieceStrong.setVisible(strongVisible);
		entityPieceWeak.setPosition(pieceX,y);
		entityPieceStrong.setPosition(pieceX,y);
	}

	@Override
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded) {
		setWeakInvisible();
		setStrongInvisible();
	}

	@Override
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs) {
		setWeakInvisible();
		setStrongInvisible();
	}

	@Override
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs) {
		setWeakInvisible();
		if(gameStatePuttingStrongs.isTopPlayerTurn() && isTop || gameStatePuttingStrongs.isBottomPlayerTurn() && !isTop) {
			setStrongVisible();
		}else{
			setStrongInvisible();
		}
	}

	@Override
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks) {
		setStrongInvisible();
		if(gameStatePuttingWeaks.isTopPlayerTurn() && isTop || gameStatePuttingWeaks.isBottomPlayerTurn() && !isTop) {
			setWeakVisible();
		} else {
			setWeakInvisible();
		}
	}

	private void setAllInvisible() {
		entityPieceWeak.setVisible(false);
		entityPieceStrong.setVisible(false);
	}

	private void setStrongInvisible() {
		strongVisible = false;
		entityPieceStrong.setVisible(strongVisible);
	}

	private void setStrongVisible() {
		strongVisible = true;
		entityPieceStrong.setVisible(strongVisible);
	}

	private void setWeakInvisible() {
		weakVisible = false;
		entityPieceWeak.setVisible(weakVisible);
	}

	private void setWeakVisible() {
		weakVisible = true;
		entityPieceWeak.setVisible(weakVisible);
	}

}
