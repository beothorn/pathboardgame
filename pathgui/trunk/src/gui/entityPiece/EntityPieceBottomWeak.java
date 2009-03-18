package gui.entityPiece;
import gameLogic.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceBottomWeak extends EntityPiece{

	public EntityPieceBottomWeak(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceBottom);
	}

	@Override
	public void reset() {
		//NOTAISSUE: unused event
	}
}
