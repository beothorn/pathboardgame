package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceTopStrong extends EntityPieceStrong {

	public EntityPieceTopStrong(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongTop);
	}

	@Override
	public void setState(final boolean moved, final boolean isPlaying) {
		if(moved) {
			getEntity().setSprite(GameLayoutDefinitions.pieceMovedStrongTop);
			return;
		}
		if(isPlaying){
			getEntity().setSprite(GameLayoutDefinitions.pieceSelectedStrongTop);
			return;
		}
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongTop);
	}
}
