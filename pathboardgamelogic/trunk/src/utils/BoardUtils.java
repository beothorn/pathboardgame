package utils;

import gameLogic.PlaySequence;
import gameLogic.board.Board;
import gameLogic.board.piece.Piece;
import gameLogic.board.piece.PieceFactory;

public class BoardUtils {

	private static final String TOP = "T";
	private static final String BOTTOM = "B";
	private static final String WEAK = "WK";
	private static final String STRONG = "S";
	private static final String EMPTY = "---";

	public static Board newBoardFromString(String boardString) {
		final Board board = new Board();
		PieceFactory pieceMaker = new PieceFactory();
		String[] lines = boardString.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String[] columns = lines[i].split(" ");
			for (int j = 0; j < columns.length; j++) {
				addPieceAt(columns[j],i,j,pieceMaker,board);
			}
		}
		return board;
	}

	private static void addPieceAt(String pieceString, int line, int column, PieceFactory pieceMaker, Board board) {
		if(pieceString.charAt(0) == EMPTY.charAt(0)){			
			board.setPieceAt(line, column, pieceMaker.getEmptyPiece());
			return;
		}
		if(pieceString.charAt(0) == TOP.charAt(0)){
			if(pieceString.charAt(1) == WEAK.charAt(0)){
				board.setPieceAt(line, column, pieceMaker.getTopWeakPiece());
				return;
			}
			if(pieceString.charAt(1) == STRONG.charAt(0)){
				final String strongId = pieceString.substring(2);
				board.setPieceAt(line, column, pieceMaker.getTopStrongPiece(Integer.parseInt(strongId)));
				return;
			}
		}
		if(pieceString.charAt(0) == BOTTOM.charAt(0)){
			if(pieceString.charAt(1) == WEAK.charAt(0)){
				board.setPieceAt(line, column, pieceMaker.getBottomWeakPiece());
				return;
			}
			if(pieceString.charAt(1) == STRONG.charAt(0)){
				final String strongId = pieceString.substring(2);
				board.setPieceAt(line, column, pieceMaker.getBottomStrongPiece(Integer.parseInt(strongId)));
				return;
			}
		}
		throw new RuntimeException("Invalid piece string: "+pieceString);
	}

	public static String printBoardWithCoordinates(Board b) {
		String coords = "";
		for(int i=0; i< Board.BOARD_SIZE;i++){
			coords += "00"+i+" ";
		}
		return coords+"\n"+printBoard(b);
	}
	
	public static String printBoard(Board b) {
		String boardString = "";
		boolean firstLine = true;
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			if(firstLine){
				firstLine = false;
			}else{
				boardString += "\n";
			}
			boolean firstPiece = true;
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				if(firstPiece){
					firstPiece = false;
				}else{
					boardString += " ";
				}
				Piece piece = b.getPieceAt(i, j);
				if(piece.isTopPlayerPiece()){
					boardString += TOP;
					if(piece.isStrong()){
						boardString += STRONG + piece.getId();
					}else{						
						boardString += WEAK;
					}
				}
				if(piece.isBottomPlayerPiece()){
					boardString += BOTTOM;
					if(piece.isStrong()){
						boardString += STRONG + piece.getId();
					}else{						
						boardString += WEAK;
					}
				}
				if(piece.isEmpty()){
					boardString += EMPTY;
				}
			}
		}
		return boardString;
	}

	//
	//	public void switchSides() {
	//		String string = toString();
	//		String newBoard = "";
	//		for (int i = string.length()-2; i >= 0; i--) {
	//			newBoard += string.charAt(i);
	//		}
	//		
	//		newBoard = newBoard.replace(Piece.PIECE_BOTTOM_STRONG, 'X');
	//		newBoard = newBoard.replace(Piece.PIECE_TOP_STRONG, Piece.PIECE_BOTTOM_STRONG);
	//		newBoard = newBoard.replace('X', Piece.PIECE_TOP_STRONG);
	//		
	//		newBoard = newBoard.replace(Piece.PIECE_BOTTOM_WEAK, 'X');
	//		newBoard = newBoard.replace(Piece.PIECE_TOP_WEAK, Piece.PIECE_BOTTOM_WEAK);
	//		newBoard = newBoard.replace('X', Piece.PIECE_TOP_WEAK);
	//		
	//		fromString(newBoard);
	//	}
	//
	//	public static PlaySequence invertPlay(final PlaySequence playSequence) {
	//		PlaySequence inverted = new PlaySequence();
	//		for (Play play : playSequence.getPlays()) {
	//			if(play.isMoveDirection())
	//				throw new IllegalArgumentException("Cant invert move to direction play");
	//			if(play.isNextState()){
	//				inverted.addPlay(play);
	//			}else{
	//				int line = BOARD_SIZE - 1 -play.getLine();
	//				int column = BOARD_SIZE - 1 -play.getColumn();
	//				inverted.addPlay(new Play(line,column));
	//			}
	//		}
	//		return inverted;
	//	}
	
	public static void switchSides(Board board) {
		// TODO Auto-generated method stub
	}

	public static PlaySequence invertPlay(PlaySequence playSequence) {
		// TODO Auto-generated method stub
		return null;
	}	
}
