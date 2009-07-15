package gui.gameEntities.piecesBoard.entityPiece;
import gameLogic.board.piece.Piece;
import gui.GameLayoutDefinitions;


public class EntityPieceBottomWeak extends EntityPiece{

	public EntityPieceBottomWeak(final Piece p) {
		super(p);
		getEntity().setSprite(GameLayoutDefinitions.pieceBottom);
	}

}
