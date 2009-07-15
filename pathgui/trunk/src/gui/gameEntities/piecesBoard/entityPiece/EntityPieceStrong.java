package gui.gameEntities.piecesBoard.entityPiece;

import gameLogic.board.piece.Piece;

public class EntityPieceStrong extends EntityPiece {

	private boolean moved = false;

	public EntityPieceStrong(final Piece p) {
		super(p);
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(final boolean moved) {
		this.moved = moved;
	}
}
