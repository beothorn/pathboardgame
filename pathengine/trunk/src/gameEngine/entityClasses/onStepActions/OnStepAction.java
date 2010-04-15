package gameEngine.entityClasses.onStepActions;

public interface OnStepAction {
	public String describeAction();
	public void step(long delta);
	public boolean actionEnded();
	public boolean canBeDeleted();
}