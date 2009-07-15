package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceTopStrong extends EntityPieceStrong {

	public EntityPieceTopStrong(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongTop);
	}

	@Override
	public void setMoved(final boolean moved) {
		super.setMoved(moved);
		if(moved) {
			getEntity().setSprite(GameLayoutDefinitions.pieceMovedStrongTop);
		} else {
			getEntity().setSprite(GameLayoutDefinitions.pieceStrongTop);
		}
	}
}
