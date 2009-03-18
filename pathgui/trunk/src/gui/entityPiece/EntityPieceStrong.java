package gui.entityPiece;

import gameLogic.Piece;

public class EntityPieceStrong extends EntityPiece {

	private boolean moved = false;
	private boolean selected = false;
	
	public EntityPieceStrong(Piece p) {
		super(p);
	}
	
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void reset() {
		setMoved(false);
		setSelected(false);
	}
}
