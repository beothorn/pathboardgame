package gui.gameEntities.piecesBoard;
import gameEngine.GameElement;
import gameEngine.GameElementChangedListener;
import gameEngine.JGamePanel;
import gameEngine.entityClasses.Entity;
import gameEngine.entityClasses.actions.MoveToAndStop;
import gameEngine.gameMath.Point;
import gameLogic.board.piece.Piece;

import java.awt.Graphics;


public class EntityPiece implements GameElement{

	private final Entity entity;
	private Piece logicPiece;
	private static final int snappingRadius = 10;
	private static final int speed = 250;
	private final String normalSprite;
	private final String movedSprite;
	private final String playingSprite;
	private final JGamePanel gamePanel;


	public EntityPiece(final Piece p, final String sprite, final JGamePanel gF){
		this(p,sprite,sprite,sprite,gF);
	}

	public EntityPiece(final Piece p, final String normalSprite, final String movedSprite, final String playingSprite, final JGamePanel gF) {
		this.normalSprite = normalSprite;
		entity = new Entity(0, 0);
		this.movedSprite = movedSprite;
		this.playingSprite = playingSprite;
		gamePanel = gF;
		gamePanel.addGameElement(entity);
		setPiece(p);
		entity.setSprite(normalSprite);
		gamePanel.addUniqueStepAction(new MoveToAndStop(new Point(),speed, snappingRadius,getEntity()));
	}

	@Override
	public void addGameElementChangedListener(final GameElementChangedListener gameElementChangedListener) {
		entity.addGameElementChangedListener(gameElementChangedListener);
	}

	@Override
	public void doStep(final long delta) {
		getEntity().doStep(delta);
	}

	@Override
	public void draw(final Graphics g) {
		getEntity().draw(g);
	}

	protected Entity getEntity() {
		return entity;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return getEntity().markedToBeDestroyed();
	}

	public void markToBeDestroyed() {
		getEntity().markToBeDestroyed();
	}

	public boolean ownsPiece(final Piece p){
		return logicPiece == p;
	}

	public void setPiece(final Piece p){
		logicPiece = p;
	}

	public void setPointToGo(final Point point){
		gamePanel.addUniqueStepAction(new MoveToAndStop(point,speed, snappingRadius,getEntity()));;
	}

	public void setPosition(final Point p){
		getEntity().setPosition(p);
	}

	public void setState(final boolean moved, final boolean isPlaying) {
		if(moved) {
			getEntity().setSprite(movedSprite);
			return;
		}
		if(isPlaying){
			getEntity().setSprite(playingSprite);
			return;
		}
		getEntity().setSprite(normalSprite);
	}
}
