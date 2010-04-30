package gui.gameEntities.piecesBoard;

import gameEngine.JGamePanel;
import gameLogic.board.piece.Piece;
import gui.GameDefinitions;

public class EntityPieceFactory {
	public static EntityPiece entityPieceOwningThis(final Piece p, final GameDefinitions gameDefinitions, final JGamePanel gamePanel){
		if(p.isBottomPlayerWeakPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceBottom(),gamePanel);
		}

		if(p.isBottomPlayerStrongPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceStrongBottom(),gameDefinitions.getPieceStrongMovedBottom(),gameDefinitions.getPieceStrongPlayingBottom(),gamePanel);
		}

		if(p.isTopPlayerWeakPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceTop(),gamePanel);
		}

		if(p.isTopPlayerStrongPiece()) {
			return new EntityPiece(p,gameDefinitions.getPieceStrongTop(),gameDefinitions.getPieceStrongMovedTop(),gameDefinitions.getPieceStrongPlayingTop(),gamePanel);
		}

		throw new IllegalArgumentException("Piece is invalid.");
	}
}
