package gameEngine.entityClasses.onStepActions;

import gameEngine.GameElementChangedListener;

public class MutableOnStepAction implements OnStepAction{

	private OnStepAction onStepAction;
	private final GameElementChangedListener gamePanel;
	private boolean canBeDeleted;
	
	public MutableOnStepAction(OnStepAction entityAction, final GameElementChangedListener gamePanel) {
		this.gamePanel = gamePanel;
		canBeDeleted = false;
		this.setOnStepAction(entityAction);
	}
	
	@Override
	public boolean actionEnded() {
		return onStepAction.actionEnded();
	}

	@Override
	public String describeAction() {
		return "Mutable "+onStepAction.describeAction();
	}

	@Override
	public void step(long delta) {
		onStepAction.step(delta);
	}

	public void setOnStepAction(OnStepAction entityAction) {
		this.onStepAction = entityAction;
		gamePanel.gameElementChanged();
	}

	public OnStepAction getOnStepAction() {
		return onStepAction;
	}

	@Override
	public boolean canBeDeleted() {
		return canBeDeleted;
	}

	public void markToBeDeleted() {
		canBeDeleted = true;
	}
}
