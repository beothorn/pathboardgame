package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceBottomStrong extends EntityPieceStrong {

	public EntityPieceBottomStrong(final Piece p) {
		super(p);
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
}
