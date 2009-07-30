package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToAndStop implements EntityAction, SnapToPointListener {

	private final boolean killOnSnap;
	private final MoveTowards moveTowards;
	private final SnapToPoint pointToSnap;
	private boolean shouldBeKilled = false;
	private boolean snapped = false;
	private EntityActionListener entityActionListener;

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

	public Point getLocation() {
		return pointToSnap.getPoint();
	}

	public void setLocation(final int x, final int y) {
		setLocation(new Point(x,y));
	}

	public void setLocation(final Point point) {
		snapped = false;
		entityActionListener.actionPerformed();
		moveTowards.setLocation(point);
		pointToSnap.setLocation(point);
	}

	@Override
	public void snappedToPoint(final Point point) {
		snapped = true;
	}

	@Override
	public String toString() {
		return describeAction();
	}

	@Override
	public void addActionListener(final EntityActionListener entityActionListener) {
		this.entityActionListener = entityActionListener;
	}

	@Override
	public boolean isPerformingAction() {
		return !snapped;
	}

}
