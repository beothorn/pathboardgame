package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToAndStop implements EntityAction, SnapToPointListener {

	private final MoveTowards moveTowards;
	private final SnapToPoint pointToSnap;
	private boolean snapped = false;

	public MoveToAndStop(final Point destination,final double speed, final double snappingRadius, final Entity entity) {
		final Point clone = destination.copy();
		moveTowards = new MoveTowards(clone,speed,entity);
		pointToSnap = new SnapToPoint(clone,snappingRadius,entity);
		pointToSnap.addSnapListener(this);
	}

	@Override
	public String describeAction() {
		return "MoveToAndStop: \n"+ moveTowards.describeAction() + pointToSnap.describeAction();
	}

	@Override
	public void doAction(final long delta) {
		if(isSnapped()){
			return;
		}
		moveTowards.doAction(delta);
		pointToSnap.doAction(delta);
	}

	public boolean isSnapped() {
		return snapped;
	}

	public Point getLocation() {
		return pointToSnap.getPoint();
	}

	@Override
	public void snappedToPoint() {
		snapped = true;
	}

	@Override
	public String toString() {
		return describeAction();
	}
	
	@Override
	public boolean actionEnded() {
		return !snapped;
	}
}
