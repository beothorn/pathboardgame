package gui.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceBottomStrong extends EntityPieceStrong {

	public EntityPieceBottomStrong(final Piece p) {
		super(p);
		setSelected(true);
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongBottom);
	}

	@Override
	public void setMoved(final boolean moved) {
		super.setMoved(moved);
		if(moved) {
			getEntity().setSprite(GameLayoutDefinitions.pieceMovedStrongBottom);
		} else {
			getEntity().setSprite(GameLayoutDefinitions.pieceStrongBottom);
		}
	}

	@Override
	public void setSelected(final boolean selected) {
		super.setSelected(selected);

		if(selected) {
			getEntity().setSprite(GameLayoutDefinitions.pieceSelectedStrongBottom);
		} else {
			getEntity().setSprite(GameLayoutDefinitions.pieceStrongBottom);
		}
	}
}
