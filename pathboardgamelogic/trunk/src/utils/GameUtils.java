package utils;

import gameLogic.Game;
import gameLogic.board.Board;
import gameLogic.board.PieceFactory;
import gameLogic.board.Play;
import gameLogic.board.PlaySequence;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameState;

import java.util.List;
import java.util.Set;

public class GameUtils {

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
			board.setPieceAt(line, column, PieceFactory.getEmptyPiece());
			return;
		}
		if(pieceString.charAt(0) == TOP.charAt(0)){
			if(pieceString.charAt(1) == WEAK.charAt(0)){
				board.setPieceAt(line, column, PieceFactory.getTopWeakPiece());
				return;
			}
			if(pieceString.charAt(1) == STRONG.charAt(0)){
				final String strongId = pieceString.substring(2);
				board.setPieceAt(line, column, PieceFactory.getTopStrongPiece(Integer.parseInt(strongId)));
				return;
			}
		}
		if(pieceString.charAt(0) == BOTTOM.charAt(0)){
			if(pieceString.charAt(1) == WEAK.charAt(0)){
				board.setPieceAt(line, column, PieceFactory.getBottomWeakPiece());
				return;
			}
			if(pieceString.charAt(1) == STRONG.charAt(0)){
				final String strongId = pieceString.substring(2);
				board.setPieceAt(line, column, PieceFactory.getBottomStrongPiece(Integer.parseInt(strongId)));
				return;
			}
		}
		throw new RuntimeException("Invalid piece string: "+pieceString);
	}

	public static String printBoardWithCoordinates(final Game game) {
		String coords = "";
		for(int i=0; i< Board.BOARD_SIZE;i++){
			coords += "00"+i+" ";
		}
		return coords+"\n"+printBoard(game);
	}
	
	public static String printBoard(final Game game) {
		final Board board = game.getBoard();
		String boardString = printBoard(board); 
		Set<Integer> alreadyMovedPieces = game.getAlreadyMovedPieces();
		for (Integer id : alreadyMovedPieces) {
			String player = (game.isTopPlayerTurn())?"T":"B";
			boardString = boardString.replace(player+"S"+id, player+"XX");
		}
		return boardString;
	}
	
	public static Board newBoardSwitchedSides(final Board board){
		final Board boardCopy = new Board();
		boardCopy.copyFrom(board);		
		switchSides(boardCopy);
		return boardCopy;
	}
	
	private static void switchSides(final Board board) {
		int boardSize = Board.BOARD_SIZE;
		final int totalSize = boardSize*boardSize;
		final Board boardCopy = new Board();
		boardCopy.copyFrom(board);
		for(int i=0;i<totalSize;i++){
			int srcLine = i / boardSize;
			int srcColumn = i % boardSize;
			int destLine = (totalSize-1-i) / boardSize;
			int destColumn = (totalSize-1-i) % boardSize;
			final Piece piece = boardCopy.getPieceAt(srcLine, srcColumn);
			final Piece switchedPiece =Piece.switchPieceSide(piece);
			board.setPieceAt(destLine, destColumn, switchedPiece);
		}
	}

	public static PlaySequence invertPlay(PlaySequence playSequence) {
		List<Play> plays = playSequence.getPlays();
		final PlaySequence invertedPlaySequence = new PlaySequence();
		for (int i = 0; i < plays.size(); i++) {
			final Play play = plays.get(i);
			final Play invertedPlay;
			if(play.isAddPiece()){
				invertedPlay = new Play((Board.BOARD_SIZE-1) - play.getColumn());
			}else if(play.isMoveDirection()){
				char direction = play.getDirection();
				switch(direction){
				case Play.UP:
					invertedPlay = new Play(play.getPieceId(),Play.DOWN);
					break;
				case Play.DOWN:
					invertedPlay = new Play(play.getPieceId(),Play.UP);
					break;
				case Play.LEFT:
					invertedPlay = new Play(play.getPieceId(),Play.RIGHT);
					break;
				case Play.RIGHT:
					invertedPlay = new Play(play.getPieceId(),Play.LEFT);
					break;
				default:
					invertedPlay = play;
				}
			}else{
				invertedPlay = play;
			}
			invertedPlaySequence.addPlay(invertedPlay);
		}
		return invertedPlaySequence;
	}

	public static String printBoard(final Board board) {
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
				Piece piece = board.getPieceAt(i, j);
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
	
	public static String printStateDescription(final Game game) {
		if(game.getStateId() == GameState.GAME_ENDED_DRAW_ID){			
			return "Game draw.";
		}
		if(game.isTopPlayerTurn()){
			switch(game.getStateId()){
			case GameState.GAME_ENDED_ID:
					return "Game ended, top player won.";
			case GameState.GAME_PUTTING_STRONGS_ID:
				return "Top player must add 3 strong pieces to the first line.";
			case GameState.GAME_PUTTING_WEAKS_ID:
				return "Top player should put three weak pieces in the first line or pass to moving strongs.";
			case GameState.GAME_MOVING_STRONS_ID:
				return "Top player should move strong pieces or pass the turn.";
			}
			throw new RuntimeException("Error requesting game state.");
		}
		switch(game.getStateId()){
		case GameState.GAME_ENDED_ID:
			return "Game ended, bottom player won.";
		case GameState.GAME_PUTTING_STRONGS_ID:
			return "Bottom player must add 3 strong pieces to the first line.";
		case GameState.GAME_PUTTING_WEAKS_ID:
			return "Bottom player should put three weak pieces in the first line or pass to moving strongs.";
		case GameState.GAME_MOVING_STRONS_ID:
			return "Bottom player should move strong pieces or pass the turn.";
		}
		throw new RuntimeException("Error requesting game state.");
	}
}