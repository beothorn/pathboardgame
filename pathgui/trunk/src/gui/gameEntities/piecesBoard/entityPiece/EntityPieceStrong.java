package gui.gameEntities.piecesBoard.entityPiece;

import gameLogic.board.piece.Piece;


public abstract class EntityPieceStrong extends EntityPiece {

	public EntityPieceStrong(final Piece p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public abstract void setState(final boolean moved,final boolean isPlaying) ;
}
