package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Eval;
import gameEngine.gameMath.Point;

import java.util.ArrayList;
import java.util.List;

public class SnapToPoint implements EntityAction {

	private final Entity entityToSnap;
	private boolean onPoint;
	private final Point point;
	private final double radius;
	private List<SnapToPointListener> snapListeners;

	public SnapToPoint(final double x,final double y,final double radius, final Entity entity) {
		this(new Point(x,y),radius,entity);
	}

	public SnapToPoint(final Point p, final double radius, final Entity entity) {
		entityToSnap = entity;
		point = p.copy();
		this.radius = radius;
		setOnPoint(false);
	}

	public boolean addSnapListener(final SnapToPointListener spL) {
		if(snapListeners == null) {
			snapListeners = new ArrayList<SnapToPointListener>();
		}
		return snapListeners.add(spL);
	}

	private void callSnapListeners() {
		if(snapListeners == null) {
			return;
		}
		for (final SnapToPointListener spL : snapListeners) {
			spL.snappedToPoint();
		}
	}

	@Override
	public String describeAction() {
		return "Snap "+entityToSnap+" to "+point+" in "+radius+" radius.";
	}

	@Override
	public void doAction(final long delta) {
		final double distance = entityToSnap.getPosition().distance(point);
		if(Eval.lessOrEqualsTo(distance,radius)){
			entityToSnap.setPosition(point);
			entityToSnap.setSpeed(0);
			setOnPoint(true);
			callSnapListeners();
		}else{
			setOnPoint(false);
		}
	}

	public Point getPoint(){
		return point;
	}

	public boolean isOnPoint() {
		return onPoint;
	}

	public boolean removeSnapListener(final SnapToPointListener spL) {
		if(snapListeners == null) {
			return false;
		}
		return snapListeners.remove(spL);
	}

	private void setOnPoint(final boolean onPoint) {
		this.onPoint = onPoint;
	}

	@Override
	public String toString() {
		return describeAction();
	}

	@Override
	public boolean actionEnded() {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MoveToAndStop))
			return false;
		final SnapToPoint other = (SnapToPoint)obj;
		return entityToSnap.equals(other.entityToSnap);
	}
	
	@Override
	public int hashCode() {
		return entityToSnap.hashCode();
	}
	
	@Override
	public boolean canBeDeleted() {
		return actionEnded();
	}

}
