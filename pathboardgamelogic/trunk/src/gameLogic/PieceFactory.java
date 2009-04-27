package gameLogic;

public class PieceFactory {
	
	private int topStrongIdGen = 0;
	private int bottomStrongIdGen = 0;
	private int topWeakIdGen = 0;
	private int bottomWeakIdGen = 0;
	
	private int genTopStrongId(){
		topStrongIdGen++;
		return topStrongIdGen;
	}
	
	private int genTopWeakId(){
		topWeakIdGen++;
		return topWeakIdGen;
	}
	
	private int genBottomStrongId(){
		bottomStrongIdGen++;
		return bottomStrongIdGen;
	}
	
	private int genBottomWeakId(){
		bottomWeakIdGen++;
		return bottomWeakIdGen;
	}
	
	public Piece getTopStrongPiece(){
		return Piece.getTopStrongPiece(genTopStrongId());
	}
	
	public Piece getTopStrongPiece(final int forceId){
		return Piece.getTopStrongPiece(forceId);
	}
	
	public Piece getTopWeakPiece(){
		return Piece.getTopWeakPiece(genTopWeakId());
	}
	
	public Piece getBottomStrongPiece(){
		return Piece.getBottomStrongPiece(genBottomStrongId());
	}
	
	public Piece getBottomStrongPiece(final int forceId){
		return Piece.getBottomStrongPiece(forceId);
	}
	
	public Piece getBottomWeakPiece(){
		return Piece.getBottomWeakPiece(genBottomWeakId());
	}
	
	public Piece getEmptyPiece(){
		return Piece.getEmptyPiece();
	}
	
}
