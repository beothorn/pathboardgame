package gameLogic.board;

import gameLogic.board.piece.Piece;

public class PieceFactory {
	
	private static int idGen = 0;
	
	public static Piece getTopStrongPiece(final Board board){
		int id = 1;
		while(board.getStrongTopPositionOrNull(id) != null){
			id ++;
		}
		return Piece.getTopStrongPiece(id);
	}
	
	public static Piece getTopStrongPiece(final int forceId){
		return Piece.getTopStrongPiece(forceId);
	}
	
	public static Piece getTopWeakPiece(){
		return Piece.getTopWeakPiece(genId());
	}
	
	private static int genId() {
		return idGen++;
	}

	public static Piece getBottomStrongPiece(final Board board){
		int id = 1;
		while(board.getStrongBottomPositionOrNull(id) != null){
			id ++;
		}
		return Piece.getBottomStrongPiece(id);
	}
	
	public static Piece getBottomStrongPiece(final int forceId){
		return Piece.getBottomStrongPiece(forceId);
	}
	
	public static Piece getBottomWeakPiece(){
		return Piece.getBottomWeakPiece(genId());
	}
	
	public static Piece getEmptyPiece(){
		return Piece.getEmptyPiece();
	}
	
}
