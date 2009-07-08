package gui.entityPiece;

import gameLogic.board.piece.Piece;

public class EntityPieceStrong extends EntityPiece {

	private boolean moved = false;
	private boolean selected = false;

	public EntityPieceStrong(final Piece p) {
		super(p);
	}

	public boolean isMoved() {
		return moved;
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public void reset() {
		setMoved(false);
		setSelected(false);
	}

	public void setMoved(final boolean moved) {
		this.moved = moved;
	}

	public void setSelected(final boolean selected) {
		this.selected = selected;
	}
}
