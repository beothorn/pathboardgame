package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

import java.util.ArrayList;
import java.util.List;

public class MoveToAndStop implements EntityAction, SnapToPointListener {

	private final boolean killOnSnap;
	private final MoveTowards moveTowards;
	private final SnapToPoint pointToSnap;
	private boolean shouldBeKilled = false;
	private List<SnapToPointListener> snapListeners;
	private boolean snapped = false;

	public MoveToAndStop(final Point destination,final double speed, final double snappingRadius, final boolean killOnSnap,final Entity entity) {
		this.killOnSnap = killOnSnap;
		final Point clone = destination.copy();
		moveTowards = new MoveTowards(clone,speed,entity);
		pointToSnap = new SnapToPoint(clone,snappingRadius,entity);
		pointToSnap.addSnapListener(this);
	}

	public MoveToAndStop(final Point destination,final double speed, final double snappingRadius,final Entity entity) {
		this(destination,speed,snappingRadius, false, entity);
	}

	public boolean addSnapListener(final SnapToPointListener spL) {
		if(snapListeners == null) {
			snapListeners = new ArrayList<SnapToPointListener>();
		}
		return snapListeners.add(spL);
	}

	private void callSnapListeners(final Point point) {
		if(snapListeners == null) {
			return;
		}
		for (final SnapToPointListener spL : snapListeners) {
			spL.snappedToPoint(point);
		}
	}

	@Override
	public String describeAction() {
		return "MoveToAndStop: \n"+ moveTowards.describeAction() + pointToSnap.describeAction();
	}

	@Override
	public void doAction(final long delta) {
		if(isSnapped() && killOnSnap){
			shouldBeKilled = true;
			return;
		}
		snapped = false;
		moveTowards.doAction(delta);
		pointToSnap.doAction(delta);
	}

	public boolean isSnapped() {
		return snapped;
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

	public void setLocation(final int x, final int y) {
		setLocation(new Point(x,y));
	}

	public void setLocation(final Point point) {
		moveTowards.setLocation(point);
		pointToSnap.setLocation(point);
	}

	@Override
	public void snappedToPoint(final Point point) {
		snapped = true;
		callSnapListeners(point);
	}

	@Override
	public String toString() {
		return describeAction();
	}

}
