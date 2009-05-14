package gameLogic.board.piece;

public class Piece {
	private static final int PIECE_EMPTY = 0;
	private static final int PIECE_TOP_WEAK = 1;
	private static final int PIECE_BOTTOM_WEAK = 2;
	private static final int PIECE_TOP_STRONG = 3;
	private static final int PIECE_BOTTOM_STRONG = 4;
	
	public static Piece getTopStrongPiece(final int id){
		return new Piece(PIECE_TOP_STRONG, id);
	}
	
	public static Piece getTopWeakPiece(final int id){
		return new Piece(PIECE_TOP_WEAK, id);
	}
	
	public static Piece getBottomStrongPiece(final int id){
		return new Piece(PIECE_BOTTOM_STRONG, id);
	}
	
	public static Piece getBottomWeakPiece(final int id){
		return new Piece(PIECE_BOTTOM_WEAK, id);
	}
	
	public static Piece getEmptyPiece(){
		return new Piece(PIECE_EMPTY);
	}
	
	private final int id;
	private final int pieceType;
	
	public int getId(){
		return id;
	}
	
	private Piece(final int pieceType){
		this(pieceType, -1);
	}
	
	private Piece(final int pieceType, final int id) {
		this.pieceType = pieceType;
		this.id = id;	
	}
	
	public boolean isStrong(){
		return pieceType == PIECE_BOTTOM_STRONG || pieceType == PIECE_TOP_STRONG;
	}

	public boolean isTopPlayerPiece(){
		return pieceType == PIECE_TOP_STRONG || pieceType == PIECE_TOP_WEAK;
	}
	
	public boolean isBottomPlayerPiece(){
		return pieceType == PIECE_BOTTOM_STRONG || pieceType == PIECE_BOTTOM_WEAK;
	}
	
	public boolean isEmpty(){
		return pieceType == PIECE_EMPTY;
	}
	
	public boolean isTopPlayerWeakPiece(){
		return pieceType == PIECE_TOP_WEAK;
	}
	
	public boolean isBottomPlayerWeakPiece(){
		return pieceType == PIECE_BOTTOM_WEAK;
	}
	
	public boolean isTopPlayerStrongPiece(){
		return pieceType == PIECE_TOP_STRONG;
	}
	
	public boolean isBottomPlayerStrongPiece(){
		return pieceType == PIECE_BOTTOM_STRONG;
	}
	
	@Override
	public String toString() {
		return String.valueOf(pieceType);
	}

	public static Piece switchPieceSide(Piece piece) {
		if(piece.pieceType == PIECE_TOP_WEAK){
			return new Piece(PIECE_BOTTOM_WEAK,piece.id);
		}
		if(piece.pieceType == PIECE_BOTTOM_WEAK){
			return new Piece(PIECE_TOP_WEAK,piece.id);
		}
		if(piece.pieceType == PIECE_TOP_STRONG){
			return new Piece(PIECE_BOTTOM_STRONG,piece.id);
		}
		if(piece.pieceType == PIECE_BOTTOM_STRONG){
			return new Piece(PIECE_TOP_STRONG,piece.id);
		}
		return piece;
	}
}
