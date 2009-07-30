package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToStopAndGoBack implements EntityAction{

	private final static int SNAP_RADIUS = 5;
	private Point startingPoint;
	private final MoveToAndStop moveToAndStop; 
	private int timeShown = 0;
	private final int timeToStayStopped;
	private EntityActionListener entityActionListener;
	
	public MoveToStopAndGoBack(final Point startingPoint,final Point endingPointPoint,final int timeStopped,final int speed, final Entity entity) {
		this.timeToStayStopped = timeStopped;
		entity.setPosition(startingPoint);
		moveToAndStop = new MoveToAndStop(startingPoint,speed,SNAP_RADIUS,entity);
	}
	
	public void move(final Point startingPoint, final Point endingPointPoint){
		this.startingPoint = startingPoint;
		moveToAndStop.setLocation(endingPointPoint);
	}
	
	@Override
	public void addActionListener(final EntityActionListener entityActionListener) {
		this.entityActionListener = entityActionListener;
	}

	@Override
	public String describeAction() {
		return "MoveToStopAndGoBack - start: "+startingPoint+" movingTo: "+moveToAndStop;
	}

	@Override
	public void doAction(final long delta) {
		moveToAndStop.doAction(delta);
		if(moveToAndStop.isSnapped()){
			timeShown += delta;
		}
		if(timeShown >= timeToStayStopped){
			timeShown = 0;
			moveToStartingPoint();
		}
	}

	private void moveToStartingPoint() {
		moveToAndStop.setLocation(startingPoint);
	}

	@Override
	public boolean isPerformingAction() {
		return !(moveToAndStop.isSnapped() && moveToAndStop.getLocation().equals(startingPoint));
	}

	@Override
	public boolean markedToBeDestroyed() {
		return false;
	}

}
