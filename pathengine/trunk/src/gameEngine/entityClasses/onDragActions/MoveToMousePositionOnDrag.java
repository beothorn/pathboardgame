package gameEngine.entityClasses.onDragActions;

import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;

public class MoveToMousePositionOnDrag implements OnDragAction {

	private final Entity entity;

	public MoveToMousePositionOnDrag(Entity entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see gameEngine.entityClasses.onDragActions.OnDragAction#onDrag(double, double)
	 */
	public void onDrag(final double x,final double y) {
		entity.goToRelativePosition(x , y);
	}
	
	public void onDrag(final Point deltaPoint) {
		onDrag(deltaPoint.getX() , deltaPoint.getY());
	}

	/* (non-Javadoc)
	 * @see gameEngine.entityClasses.onDragActions.OnDragAction#actionEnded()
	 */
	public boolean actionEnded(){
		return false;
	}

	@Override
	public boolean canBeDeleted() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
