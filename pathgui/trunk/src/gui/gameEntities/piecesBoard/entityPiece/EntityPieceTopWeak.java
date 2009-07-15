package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceTopWeak extends EntityPiece {

	public EntityPieceTopWeak(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceTop);
	}

}
