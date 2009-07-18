package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceBottomStrong extends EntityPieceStrong {

	public EntityPieceBottomStrong(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongBottom);
	}

	@Override
	public void setState(final boolean moved, final boolean isPlaying) {
		if(moved) {
			getEntity().setSprite(GameLayoutDefinitions.pieceMovedStrongBottom);
			return;
		}
		if(isPlaying){
			getEntity().setSprite(GameLayoutDefinitions.pieceSelectedStrongBottom);
			return;
		}
		getEntity().setSprite(GameLayoutDefinitions.pieceStrongBottom);
	}
}
