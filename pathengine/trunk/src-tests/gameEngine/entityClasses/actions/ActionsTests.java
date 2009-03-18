package gameEngine.entityClasses.actions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

import org.junit.Assert;
import org.junit.Test;


public class ActionsTests {

	private static final int oneSecondDelta=1000;

	@Test
	public void testMoveTowards(){
		final Point startingPosition = new Point(0,0);
		final Point endingPosition = new Point(10,10);
		final Entity entity = new Entity(startingPosition);
		Assert.assertTrue("Entity start is wrong: ", entity.getPosition().equals(startingPosition));
		final double speed = startingPosition.distance(endingPosition);
		final MoveTowards moveTowards = new MoveTowards(endingPosition,speed,entity);
		moveTowards.doAction(oneSecondDelta);
		entity.doMove(oneSecondDelta);
		Assert.assertTrue("Entity didn't moved towards: "+entity.getPosition(), entity.getPosition().equals(endingPosition));
	}

	@Test
	public void testSnapToPoint(){
		final Point startingPosition = new Point(0,0);
		final Entity entity = new Entity(startingPosition);
		Assert.assertTrue("Entity start is wrong: ", entity.getPosition().equals(startingPosition));
		final Point snapToThisPoint = new Point(5,5);
		final double radius = startingPosition.distance(snapToThisPoint);
		final SnapToPoint snapPointTooFarAway = new SnapToPoint(snapToThisPoint,radius-0.1, entity);
		snapPointTooFarAway.doAction(oneSecondDelta);
		Assert.assertTrue("Entity snapped to a point while outside radius: "+entity.getPosition(), entity.getPosition().equals(startingPosition));

		final SnapToPoint snapingPointOnRange = new SnapToPoint(snapToThisPoint,radius,entity);

		snapingPointOnRange.doAction(oneSecondDelta);
		Assert.assertTrue("Entity didn't snapped to a point: "+entity.getPosition(), entity.getPosition().equals(snapToThisPoint));

		entity.setPosition(startingPosition);
		snapingPointOnRange.doAction(oneSecondDelta);
		Assert.assertTrue("Entity didn't snapped to a point: "+entity.getPosition(), entity.getPosition().equals(snapToThisPoint));

		final boolean killOnSnap = true;
		final SnapToPoint snapingPointOnRangeKillOnSnap = new SnapToPoint(snapToThisPoint,radius,killOnSnap, entity);

		snapingPointOnRangeKillOnSnap.doAction(oneSecondDelta);
		Assert.assertTrue("Entity didn't snapped to a point: "+entity.getPosition(), entity.getPosition().equals(snapToThisPoint));
		entity.setPosition(startingPosition);
		snapingPointOnRangeKillOnSnap.doAction(oneSecondDelta);
		Assert.assertTrue("Entity snapped after snap point should have been killed: "+entity.getPosition(), entity.getPosition().equals(startingPosition));
	}
}
