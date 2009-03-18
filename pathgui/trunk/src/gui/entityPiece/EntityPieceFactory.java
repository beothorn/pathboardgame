package gui.entityPiece;

import gameLogic.Piece;

public class EntityPieceFactory {
	public static EntityPiece giveMeAPiece(final Piece p){
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
