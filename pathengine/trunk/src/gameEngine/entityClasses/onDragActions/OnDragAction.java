package gameEngine.entityClasses.onDragActions;

import gameEngine.gameMath.Point;

public interface OnDragAction {

	public abstract void onDrag(Point deltaPoint);

	public abstract boolean actionEnded();

	public abstract boolean canBeDeleted();


}