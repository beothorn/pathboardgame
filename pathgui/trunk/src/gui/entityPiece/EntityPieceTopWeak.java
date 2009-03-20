package gui.entityPiece;
import gameLogic.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceTopWeak extends EntityPiece {

	public EntityPieceTopWeak(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceTop);
	}

	@Override
	public void reset() {
		//NOTAISSUE: unused event
	}
}