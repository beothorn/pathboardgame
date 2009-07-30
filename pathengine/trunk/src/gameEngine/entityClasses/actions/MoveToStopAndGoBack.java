package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToStopAndGoBack implements EntityAction{

	private final static int SNAP_RADIUS = 5;
	private final Point startingPoint;
	private MoveToAndStop moveToAndStop; 
	private int timeShown = 0;
	private final int timeToStayStopped;
	private final int speed;
	private final Entity entity;
	
	public MoveToStopAndGoBack(final Point startingPoint,final Point endingPointPoint,final int timeStopped,final int speed, final Entity entity) {
		this.startingPoint = startingPoint;
		this.timeToStayStopped = timeStopped;
		this.speed = speed;
		this.entity = entity;
		entity.setPosition(startingPoint);
		moveToAndStop = new MoveToAndStop(startingPoint,speed,SNAP_RADIUS,entity);
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
		moveToAndStop = new MoveToAndStop(startingPoint,speed,SNAP_RADIUS,entity);
	}

	@Override
	public boolean actionEnded() {
		return !(moveToAndStop.isSnapped() && moveToAndStop.getLocation().equals(startingPoint));
	}
}
