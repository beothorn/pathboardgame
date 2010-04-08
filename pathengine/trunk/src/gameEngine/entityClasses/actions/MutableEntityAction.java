package gameEngine.entityClasses.actions;

import gameEngine.JGamePanel;

public class MutableEntityAction implements EntityAction{

	private EntityAction entityAction;
	private final JGamePanel gamePanel;
	private boolean canBeDeleted;
	
	public MutableEntityAction(EntityAction entityAction, final JGamePanel gamePanel) {
		this.gamePanel = gamePanel;
		canBeDeleted = false;
		this.setEntityAction(entityAction);
	}
	
	@Override
	public boolean actionEnded() {
		return entityAction.actionEnded();
	}

	@Override
	public String describeAction() {
		return "Mutable "+entityAction.describeAction();
	}

	@Override
	public void doAction(long delta) {
		entityAction.doAction(delta);
	}

	public void setEntityAction(EntityAction entityAction) {
		this.entityAction = entityAction;
		gamePanel.gameElementChanged();
	}

	public EntityAction getEntityAction() {
		return entityAction;
	}

	@Override
	public boolean canBeDeleted() {
		return canBeDeleted;
	}

	public void markToBeDeleted() {
		canBeDeleted = true;
	}
}
