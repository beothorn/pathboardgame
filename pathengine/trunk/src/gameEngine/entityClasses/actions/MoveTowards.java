package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Eval;
import gameEngine.gameMath.Point;


public class MoveTowards implements EntityAction {

	private final Point destination;
	private final Entity entityToMove;
	private final double speed;
	private int timePassed;
	private int timeToHitPoint;

	public MoveTowards(final double x,final double y, final double speed,final Entity entity) {
		this(new Point(x,y),speed,entity);
	}

	public MoveTowards(final Point p, final double speed,final Entity entity) {
		entityToMove = entity;
		destination = p.copy();
		setLocation(p);
		this.speed = speed;
	}

	@Override
	public String describeAction() {
		return "Move "+entityToMove+" towards "+destination+" with speed "+speed+" pix/s .";
	}

	@Override
	public void doAction(final long delta) {
		timePassed += delta;

		if(isOnPoint(entityToMove)){
			entityToMove.setSpeed(0);
		}else{
			if(timePassed>timeToHitPoint){
				entityToMove.setPosition(destination);
			}else{
				entityToMove.setDirection(destination.getX()-entityToMove.getX(),destination.getY()-entityToMove.getY());
				entityToMove.setSpeed(speed);
			}
		}
	}

	private boolean isOnPoint(final Entity e) {
		return Eval.equals(e.getPosition().distance(destination),0);
	}

	private void setLocation(final Point point) {
		destination.setLocation(point.getX(), point.getY());
		timePassed = 0;
		final double distance = point.distance(entityToMove.getPosition());
		timeToHitPoint = (int) (distance / speed*1000);
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
		final MoveTowards other = (MoveTowards)obj;
		return entityToMove.equals(other.entityToMove);
	}
	
	@Override
	public int hashCode() {
		return entityToMove.hashCode();
	}
}
