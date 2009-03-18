package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Eval;
import gameEngine.gameMath.Point;

import java.util.ArrayList;
import java.util.List;

public class SnapToPoint implements EntityAction {

	private final Entity entityToSnap;
	private boolean killOnSnap;
	private boolean onPoint;
	private final Point point;
	private final double radius;
	private boolean shouldBeKilled = false;
	private List<SnapToPointListener> snapListeners;

	public SnapToPoint(final double x,final double y, final double radius, final boolean killOnSnap, final Entity entity) {
		this(x,y,radius,entity);
		setKillOnSnap(killOnSnap);
	}

	public SnapToPoint(final double x,final double y,final double radius, final Entity entity) {
		this(new Point(x,y),radius,entity);
	}

	public SnapToPoint(final Point p, final double radius, final boolean killOnSnap, final Entity entity) {
		this(p,radius,entity);
		setKillOnSnap(killOnSnap);
	}

	public SnapToPoint(final Point p, final double radius, final Entity entity) {
		entityToSnap = entity;
		point = p.copy();
		this.radius = radius;
		setOnPoint(false);
		setKillOnSnap(false);
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
			spL.snappedToPoint(point);
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
			callSnapListeners();
			entityToSnap.setPosition(point);
			entityToSnap.setSpeed(0);
			setOnPoint(true);
			if(isKillOnSnap()) {
				shouldBeKilled = true;
			}
		}else{
			setOnPoint(false);
		}
	}

	public Point getPoint(){
		return point;
	}

	public boolean isKillOnSnap() {
		return killOnSnap;
	}

	public boolean isOnPoint() {
		return onPoint;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return shouldBeKilled;
	}

	public boolean removeSnapListener(final SnapToPointListener spL) {
		if(snapListeners == null) {
			return false;
		}
		return snapListeners.remove(spL);
	}

	public void setKillOnSnap(final boolean killOnSnap) {
		this.killOnSnap = killOnSnap;
	}

	public void setLocation(final Point point) {
		this.point.setLocation(point.getX(), point.getY());
	}

	private void setOnPoint(final boolean onPoint) {
		this.onPoint = onPoint;
	}

	@Override
	public String toString() {
		return describeAction();
	}

}
