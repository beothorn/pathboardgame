package gui.gameEntities.piecesBoard;

import gameLogic.board.piece.Piece;
import gui.GameDefinitions;

public class EntityPieceFactory {
	public static EntityPiece entityPieceOwningThis(final Piece p, final GameDefinitions gameDefinitions){
		if(p.isBottomPlayerWeakPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceBottom());
		}

		if(p.isBottomPlayerStrongPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceStrongBottom(),gameDefinitions.getPieceStrongMovedBottom(),gameDefinitions.getPieceStrongPlayingBottom());
		}

		if(p.isTopPlayerWeakPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceTop());
		}

		if(p.isTopPlayerStrongPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceStrongTop(),gameDefinitions.getPieceStrongMovedTop(),gameDefinitions.getPieceStrongPlayingTop());
		}

		throw new IllegalArgumentException("Piece is invalid.");
	}
}
