package gui.gameEntities.piecesBoard.entityPiece;

import gameLogic.board.piece.Piece;

public class EntityPieceFactory {
	public static EntityPiece entityPieceOwningThis(final Piece p){
		//TODO: BAD smell, put visitor here (or some other fix)
		if(p.isBottomPlayerWeakPiece()) {
			return new EntityPieceBottomWeak(p);
		}

		if(p.isBottomPlayerStrongPiece()) {
			return new EntityPieceBottomStrong(p);
		}

		if(p.isTopPlayerWeakPiece()) {
			return new EntityPieceTopWeak(p);
		}

		if(p.isTopPlayerStrongPiece()) {
			return new EntityPieceTopStrong(p);
		}

		throw new IllegalArgumentException("Piece is invalid.");
	}
}
