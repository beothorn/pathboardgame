package gui.entityPiece;
import gameEngine.GameElement;
import gameEngine.entityClasses.Entity;
import gameEngine.entityClasses.actions.EntityAction;
import gameEngine.entityClasses.actions.MoveToAndStop;
import gameEngine.gameMath.Point;
import gameLogic.Piece;

import java.awt.Graphics;


public abstract class EntityPiece implements GameElement{

	private Entity entity;
	private Piece logicPiece;
	private boolean markedToBeDestroyed = false;
	private final MoveToAndStop moveToAndStop;
	private static final int snappingRadius = 10;
	private static final int speed = 250;

	public EntityPiece(final Piece p) {
		setPiece(p);
		setEntity(new Entity(0, 0));
		final boolean killOnSnap = false;
		moveToAndStop = new MoveToAndStop(new Point(),speed, snappingRadius,killOnSnap,getEntity());
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

	public EntityAction getStepAction(){
		return moveToAndStop;
	}

	public boolean isMarkedToBeDestroyed() {
		return markedToBeDestroyed;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return markedToBeDestroyed;
	}

	public boolean ownsPiece(final Piece p){
		return logicPiece == p;
	}

	public abstract void reset();

	public void setEntity(final Entity entity) {
		this.entity = entity;
	}

	public void setMarkedToBeDestroyed(final boolean markedToBeDestroyed) {
		this.markedToBeDestroyed = markedToBeDestroyed;
	}

	public void setPiece(final Piece p){
		logicPiece = p;
	}

	public void setPointToGo(final Point point){
		moveToAndStop.setLocation(point);
	}

	public void setPosition(final Point p){
		getEntity().setPosition(p);
	}
}
