package gameLogic;

public class Piece {
	
	private static final int MAXIMUN_STRONG_QNTY = 10;
	public static final char PIECE_EMPTY = '0';
	public static final char PIECE_TOP_WEAK = '1';
	public static final char PIECE_BOTTOM_WEAK = '2';
	public static final char PIECE_TOP_STRONG = '3';
	public static final char PIECE_BOTTOM_STRONG = '4';
	
	private static int strongIdGen = 0;
	private static int weakIdGen = MAXIMUN_STRONG_QNTY;

	private static int getNewStrongId(){
		final int newId = ++strongIdGen;
		if(newId>=MAXIMUN_STRONG_QNTY){
			throw new RuntimeException("Too much strong pieces in the game. What are you doing?");
		}
		return newId;
	}
	
	private static int getNewWeakId(){
		final int newId = ++weakIdGen;
		return newId;
	}	
	
	public static void reset() {
		strongIdGen = 0;
		weakIdGen = MAXIMUN_STRONG_QNTY;
	}
	
	public static Piece getTopStrongPiece(){
		return new Piece(PIECE_TOP_STRONG);
	}
	
	public static Piece getTopWeakPiece(){
		return new Piece(PIECE_TOP_WEAK);
	}
	
	public static Piece getBottomStrongPiece(){
		return new Piece(PIECE_BOTTOM_STRONG);
	}
	
	public static Piece getBottomWeakPiece(){
		return new Piece(PIECE_BOTTOM_WEAK);
	}
	
	public static Piece getEmptyPiece(){
		return new Piece(PIECE_EMPTY);
	}
	
	private final int id;
	private final char pieceType;
	
	public int getId(){
		return id;
	}
	
	private Piece(final char pieceType) {
		this.pieceType = pieceType;
		if(isStrong()){
			id = getNewStrongId();			
		}else{
			id = getNewWeakId();
		}		
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
}
