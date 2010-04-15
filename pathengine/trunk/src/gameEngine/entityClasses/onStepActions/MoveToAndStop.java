package gameEngine.entityClasses.onStepActions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToAndStop implements OnStepAction, SnapToPointListener {

	private final MoveTowards moveTowards;
	private final SnapToPoint pointToSnap;
	private boolean snapped = false;
	private final Entity entity;

	public MoveToAndStop(final Point destination,final double speed, final double snappingRadius, final Entity entity) {
		this.entity = entity;
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
	public void step(final long delta) {
		if(isSnapped()){
			return;
		}
		moveTowards.step(delta);
		pointToSnap.step(delta);
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
		return snapped;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MoveToAndStop))
			return false;
		final MoveToAndStop other = (MoveToAndStop)obj;
		return entity.equals(other.entity);
	}
	
	@Override
	public int hashCode() {
		return entity.hashCode();
	}

	@Override
	public boolean canBeDeleted() {
		return actionEnded();
	}
}
