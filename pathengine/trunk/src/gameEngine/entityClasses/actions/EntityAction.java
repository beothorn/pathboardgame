package gameEngine.entityClasses.actions;

public interface EntityAction {
	public String describeAction();
	public void doAction(long delta);
	public boolean isPerformingAction();
	public void addActionListener(final EntityActionListener entityActionListener);
	public boolean markedToBeDestroyed();
}
