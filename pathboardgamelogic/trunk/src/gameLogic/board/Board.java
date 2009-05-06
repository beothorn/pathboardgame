package gameLogic.board;


import gameLogic.board.piece.Piece;
import gameLogic.board.piece.PieceFactory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int BOARD_SIZE = 8;
	public static final int MAX_STRONG_PIECES = 3;

	private static int NO_PLAYER = 0;
	private static int TOP_PLAYER = 1;
	private static int BOTTOM_PLAYER = 2;
	private static int DRAW = 3;

	Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	private PieceFactory pieceMaker;

	public Board() {
		reset();
	}

	public void reset() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = Piece.getEmptyPiece();
			}
		}
		pieceMaker = new PieceFactory();
	}

	public ValidPlay validatePlay(final Play play, final boolean forTopPlayer) throws InvalidPlayException {
		if(play.isAddPiece()){
			if(forTopPlayer && !canAddTopPieceIn(play.getColumn())){
				throw InvalidPlayException.cantAddAPiece(play.getColumn());
			}
			if(!forTopPlayer && !canAddBottomPieceIn(play.getColumn())){
				throw InvalidPlayException.cantAddAPiece(play.getColumn());
			}
		}
		if(play.isMoveDirection()){
			switch(play.getDirection()){
			case Play.UP:
				if(!canMoveStrongUp(play.getPieceId(),forTopPlayer)){
					throw InvalidPlayException.cantMoveAPieceUp(play.getPieceId());
				}
				break;
			case Play.DOWN:
				if(!canMoveStrongDown(play.getPieceId(),forTopPlayer)){
					throw InvalidPlayException.cantMoveAPieceDown(play.getPieceId());
				}
				break;
			case Play.LEFT:
				if(!canMoveStrongLeft(play.getPieceId(),forTopPlayer)){
					throw InvalidPlayException.cantMoveAPieceLeft(play.getPieceId());
				}
				break;
			case Play.RIGHT:
				if(!canMoveStrongRight(play.getPieceId(),forTopPlayer)){
					throw InvalidPlayException.cantMoveAPieceRight(play.getPieceId());
				}
				break;
			default:
				throw InvalidPlayException.invalidDierction(play.getDirection());	
			}
		}
		return new ValidPlay(play);
	}

	private boolean canAddTopPieceIn(int column) {
		if(column < 0 || column >= BOARD_SIZE){
			return false;
		}
		for(int line = 0; line<BOARD_SIZE; line++){
			final Piece currentPiece = board[line][column];
			if(currentPiece.isStrong()) {
				return false;
			}
			if(currentPiece.isEmpty()){
				return true;
			}
		}
		return true;
	}

	private boolean canAddBottomPieceIn(int column) {
		if(column < 0 || column >= BOARD_SIZE){
			return false;
		}
		for(int line = BOARD_SIZE - 1; line>=0; line--){
			final Piece currentPiece = board[line][column];
			if(currentPiece.isStrong()) {
				return false;
			}
			if(currentPiece.isEmpty()){
				return true;
			}
		}
		return true;
	}

	private Point getStrongPiecePosition(int pieceId, boolean topStrong) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Piece piece = board[i][j];
				if(topStrong && piece.getId()==pieceId && piece.isTopPlayerStrongPiece()){
					return new Point(i,j);
				}
				if(!topStrong && piece.getId()==pieceId && piece.isBottomPlayerStrongPiece()){
					return new Point(i,j);
				}
			}
		}
		throw new RuntimeException("Piece "+pieceId +" top="+topStrong+" but it didn't exist.");
	}

	private boolean canMoveStrongRight(int pieceId, boolean topStrong) {
		final Point piecePosition = getStrongPiecePosition(pieceId, topStrong);
		int lineFromIndex = piecePosition.x;
		int columnFromIndex = piecePosition.y;
		if(columnFromIndex == board[lineFromIndex].length-1) {
            return false;
	    }
	    int strongCount = 0;
	    for(int i = columnFromIndex +1; i != columnFromIndex; i = (i+1)%board[lineFromIndex].length  ){
	            if(board[lineFromIndex][i].isEmpty()){
	                    return true;
	            }
	            if(board[lineFromIndex][i].isStrong()) {
	                    strongCount++;
	            }
	            if(strongCount>1) {
	                    return false;
	            }
	    }
		return true;
	}

	private boolean canMoveStrongLeft(int pieceId, boolean topStrong) {
		final Point piecePosition = getStrongPiecePosition(pieceId, topStrong);
		int lineFromIndex = piecePosition.x;
		int columnFromIndex = piecePosition.y;
		if(columnFromIndex == 0) {
            return false;
		}
		int strongCount = 0;
		for(int i = columnFromIndex -1; i != columnFromIndex; i = i-1 == -1?board[lineFromIndex].length-1:i-1   ){
            if(board[lineFromIndex][i].isEmpty()){
                    return true;
            }
            if(board[lineFromIndex][i].isStrong()) {
                    strongCount++;
            }
            if(strongCount>1) {
                    return false;
            }
		}
		return true;
	}

	private boolean canMoveStrongDown(int pieceId, boolean topStrong) {
		final Point piecePosition = getStrongPiecePosition(pieceId, topStrong);
		int lineFromIndex = piecePosition.x;
		int columnFromIndex = piecePosition.y;
		if(lineFromIndex == board.length-1) {
            return false;
	    }
	    int strongCount = 0;
	    for(int i = lineFromIndex+1; i < board.length; i++  ){
	            if(board[i][columnFromIndex].isEmpty()){
	                    return true;
	            }
	            if(board[i][columnFromIndex].isStrong()) {
	                    strongCount++;
	            }
	            if(strongCount>1) {
	                    return false;
	            }
	    }
	    if(board[board.length-1][columnFromIndex].isStrong()) {
	            return true;
	    }

		return true;
	}

	private boolean canMoveStrongUp(int pieceId, boolean topStrong) {
		final Point piecePosition = getStrongPiecePosition(pieceId, topStrong);
		int lineFromIndex = piecePosition.x;
		int columnFromIndex = piecePosition.y;
		if(lineFromIndex == 0) {
            return false;
	    }
	    if(board[lineFromIndex][columnFromIndex].isEmpty()) {
	            return false;
	    }
	    int strongCount = 0;
	    for(int i = lineFromIndex-1; i >= 0; i--  ){
	            if(board[i][columnFromIndex].isEmpty()){
	                    return true;
	            }
	            if(board[i][columnFromIndex].isStrong()) {
	                    strongCount++;
	            }
	            if(strongCount>1) {
	                    return false;
	            }
	    }
	    if(board[0][columnFromIndex].isStrong()) {
	            return false;
	    }
		return true;
	}

	private boolean alreadyPutTopStrongs() {
		return countStrongTop() >= MAX_STRONG_PIECES;
	}
	
	private boolean alreadyPutBottomStrongs() {
		return countStrongBottoms() >= MAX_STRONG_PIECES;
	}

	public int countStrongsFor(boolean isTopPlayerTurn) {
		if(isTopPlayerTurn){
			return countStrongTop();
		}else{
			return countStrongBottoms();
		}
	}

	public int countStrongBottoms() {
			int counter = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if(board[i][j].isBottomPlayerStrongPiece())
						counter++;
				}
			}
			return counter;
		}

	private int countStrongTop() {
		int counter = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j].isTopPlayerStrongPiece())
					counter++;
			}
		}
		return counter;
	}

	public void play(final ValidPlay validPlay, final boolean forTopPlayer){
		Play play = validPlay.unbox();
		if(play.isAddPiece()){
			boolean isStrongPiece;
			if(forTopPlayer){
				isStrongPiece =  !alreadyPutTopStrongs();
			}else{
				isStrongPiece =  !alreadyPutBottomStrongs();
			}
			addPiece(forTopPlayer, isStrongPiece, play.getColumn());
		}
		if(play.isMoveDirection()){
			switch(play.getDirection()){
			case Play.UP:
				movePiecesUp(play.getPieceId(), forTopPlayer);
				break;
			case Play.DOWN:
				movePiecesDown(play.getPieceId(), forTopPlayer);
				break;
			case Play.LEFT:
				movePiecesLeft(play.getPieceId(), forTopPlayer);
				break;
			case Play.RIGHT:
				movePiecesRight(play.getPieceId(), forTopPlayer);
				break;
			}
		}
	}

	private void addBottomWeakPiece(final int columnIndex) {
		movePiecesUp(board.length-1, columnIndex);
		board[board.length-1][columnIndex] = pieceMaker.getBottomWeakPiece();
	}
	
	private void addBottomStrongPiece(final int columnIndex) {
		movePiecesUp(board.length-1, columnIndex);
		board[board.length-1][columnIndex] = pieceMaker.getBottomStrongPiece();
	}

	private void addPiece(final boolean forTopPlayer, final boolean isStrongPiece, final int pieceColumn){
		if(forTopPlayer) {
			if(isStrongPiece){
				addTopStrongPiece(pieceColumn);
			}else{
				addTopWeakPiece(pieceColumn);
			}
		} else {
			if(isStrongPiece){
				addBottomStrongPiece(pieceColumn);
			}else{
				addBottomWeakPiece(pieceColumn);
			}
		}
	}

	private void addTopWeakPiece(final int columnIndex) {
		movePiecesDown(0, columnIndex);
		board[0][columnIndex] = pieceMaker.getTopWeakPiece();
	}
	
	private void addTopStrongPiece(final int columnIndex) {
		movePiecesDown(0, columnIndex);
		board[0][columnIndex] = pieceMaker.getTopStrongPiece();
	}

	@Override
	public String toString(){
		String boardPrint="";
		for (final Piece[] boardLines : board) {
			for (final Piece piece : boardLines) {
				boardPrint += piece.toString();
			}
			boardPrint += "\n";
		}
		return boardPrint;
	}

	public Board copy() {
		final Board boardCopy = new Board();
		Piece[][] copyPiecesArray = new Piece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				copyPiecesArray[i][j] = board[i][j].copy();
			}
		}
		boardCopy.board = copyPiecesArray;
		return boardCopy;
	}

	public void applyGravity(){
		for (int column = 0; column < board.length; column++){
			bubbleUp(column);
			bubbleDown(column);
		}
	}

	private void bubbleDown(final int column) {
		final int from = board[column].length-1;
		final int to = 0;

		for(int i= from-1; i>= to; i-- ){
			for (int line = from-1; line >= to; line--) {
				final boolean isEmptyBelow = board[line+1][column].isEmpty();
				final boolean isStrong = board[line][column].isStrong();
				final boolean isBottomPlayer = board[line][column].isBottomPlayerWeakPiece();
				if(isEmptyBelow && !isStrong && isBottomPlayer){
					board[line+1][column] = board[line][column];
					board[line][column] = Piece.getEmptyPiece();
				}
			}
		}
	}

	private void bubbleUp(final int column) {
		final int from = 0;
		final int to = board[column].length-1;

		for(int i= from+1; i<= to; i++ ){
			for (int line = from+1; line <= to; line++) {
				final boolean isEmptyBelow = board[line-1][column].isEmpty();
				final boolean isStrong = board[line][column].isStrong();
				final boolean isTopPlayer = board[line][column].isTopPlayerWeakPiece();
				if(isEmptyBelow && !isStrong && isTopPlayer){
					board[line-1][column] = board[line][column];
					board[line][column] = Piece.getEmptyPiece();
				}
			}
		}
	}

	public Piece getPieceAt(final int line, final int column){
		return board[line][column];
	}

	public int getPieceColumn(final Piece p){
		for (final Piece[] element : board) {
			for (int j = 0; j < board.length; j++) {
				if(p == element[j]) {
					return j;
				}
			}
		}
		return -1;
	}

	public int getPieceLine(final Piece p){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(p == board[i][j]) {
					return i;
				}
			}
		}
		return -1;
	}

	
	private boolean scanPosition(int line, int column, List<Piece> alreadyScaned, final boolean isEvauatingTop){
		if(line == BOARD_SIZE-1 && isEvauatingTop){
			return true;
		}
		if(line == 0 && !isEvauatingTop){
			return true;
		}
		if(board[line][column].isTopPlayerPiece() && !isEvauatingTop){
			return false;
		}
		if(board[line][column].isBottomPlayerPiece() && isEvauatingTop){
			return false;
		}
		alreadyScaned.add(board[line][column]);
		
		Piece up = null;
		if(line-1 >= 0)
			up = board[line-1][column];
		Piece right = null;
		if(column+1 < BOARD_SIZE)
			right = board[line][column+1];
		Piece down = null;
		if(line+1 < BOARD_SIZE)
			down = board[line+1][column];
		Piece left = null;
		if(column-1 >= 0)
			left = board[line][column-1];
		
		if(up != null){
			boolean upIsMyPiece = (up.isTopPlayerPiece() && isEvauatingTop) || (up.isBottomPlayerPiece() && !isEvauatingTop);
			if(upIsMyPiece && !alreadyScaned.contains(up) && !up.isStrong()){
				alreadyScaned.add(up);
				if(scanPosition(line-1, column, alreadyScaned, isEvauatingTop))
					return true;
			}
		}
		
		if(right != null){
			boolean rightIsMyPiece = (right.isTopPlayerPiece() && isEvauatingTop) || (right.isBottomPlayerPiece() && !isEvauatingTop);
			if(rightIsMyPiece && !alreadyScaned.contains(right) && !right.isStrong()){
				alreadyScaned.add(up);
				if(scanPosition(line, column+1, alreadyScaned, isEvauatingTop))
					return true;
			}
		}
		
		if(down != null){
			boolean downIsMyPiece = (down.isTopPlayerPiece() && isEvauatingTop) || (down.isBottomPlayerPiece() && !isEvauatingTop);
			if(downIsMyPiece && !alreadyScaned.contains(down) && !down.isStrong()){
				alreadyScaned.add(down);
				if(scanPosition(line+1, column, alreadyScaned, isEvauatingTop))
					return true;
			}
		}
		
		if(left != null){
			boolean leftIsMyPiece = (left.isTopPlayerPiece() && isEvauatingTop) || (left.isBottomPlayerPiece() && !isEvauatingTop);
			if(leftIsMyPiece && !alreadyScaned.contains(left) && !left.isStrong()){
				alreadyScaned.add(up);
				if(scanPosition(line, column-1, alreadyScaned, isEvauatingTop))
					return true;
			}
		}
		
		return false;
	}

	private int getWinner(){
		
		boolean topWins = false;
		for(int i = 0;i<BOARD_SIZE && !topWins;i++){
			final List<Piece> piecesAlreadyUsedInPath = new ArrayList<Piece>();
			boolean isEvauatingTop = true;
			topWins = scanPosition(0,i,piecesAlreadyUsedInPath,isEvauatingTop);
		}
		
		
		boolean bottomWins = false;
		for(int i = 0;i<BOARD_SIZE && !bottomWins;i++){
			final List<Piece> piecesAlreadyUsedInPath = new ArrayList<Piece>();
			boolean isEvauatingTop = false;
			bottomWins = scanPosition(BOARD_SIZE-1,i,piecesAlreadyUsedInPath,isEvauatingTop);
		}

		if(topWins && bottomWins)
			return DRAW;
		if(topWins)
			return TOP_PLAYER;
		if(bottomWins)
			return BOTTOM_PLAYER;
		
		return NO_PLAYER;
	}
	
	public boolean isGameDraw() {
		return getWinner() == Board.DRAW;
	}

		
	public boolean isGameEnded() {
		return isGameDraw() || isTopTheWinner() || isBottomTheWinner();
	}

	public boolean isBottomTheWinner() {
		return getWinner() == Board.BOTTOM_PLAYER;
	}

	public boolean isTopTheWinner() {
		return getWinner() == Board.TOP_PLAYER;
	}

	private void movePiecesDown(final int id,final boolean topPiece){
		Point strongPiecePosition = getStrongPiecePosition(id, topPiece);
		movePiecesDown(strongPiecePosition.x, strongPiecePosition.y);
	}

	private void movePiecesDown(final int lineFrom, final int columnFrom){
		final int lineDest = lineFrom + 1;
		if(board[lineFrom][columnFrom].isEmpty()) {
			return;
		}
		if( lineDest == board.length ) {
			return;
		}
		if( board[lineDest][columnFrom].isEmpty() ){
			board[lineDest][columnFrom] = board[lineFrom][columnFrom];
			board[lineFrom][columnFrom] = Piece.getEmptyPiece();
			return;
		}
		movePiecesDown(lineDest, columnFrom);
		board[lineDest][columnFrom] = board[lineFrom][columnFrom];
		board[lineFrom][columnFrom] = Piece.getEmptyPiece();
	}

	private void movePiecesLeft(final int id,final boolean topPiece){
		Point strongPiecePosition = getStrongPiecePosition(id, topPiece);
		movePiecesLeft(strongPiecePosition.x, strongPiecePosition.y, strongPiecePosition.y);
	}

	private void movePiecesLeft(final int lineFrom, final int columnFrom, final int stopOn){
		if(board[lineFrom][columnFrom].isEmpty()) {
			return;
		}
		final int colDest = columnFrom - 1==-1?board[lineFrom].length-1:columnFrom - 1;
		final Piece piece = board[lineFrom][columnFrom];
		if(colDest == stopOn || board[lineFrom][colDest].isEmpty()){
			board[lineFrom][colDest] = piece;
			if(board[lineFrom][columnFrom] == piece) {
				board[lineFrom][columnFrom] = Piece.getEmptyPiece();
			}
			return;
		}
		movePiecesLeft(lineFrom, colDest, stopOn);
		board[lineFrom][colDest] = piece;
		if(board[lineFrom][columnFrom] == piece) {
			board[lineFrom][columnFrom] = Piece.getEmptyPiece();
		}
	}

	private void movePiecesRight(final int id,final boolean topPiece){
		Point strongPiecePosition = getStrongPiecePosition(id, topPiece);
		movePiecesRight(strongPiecePosition.x, strongPiecePosition.y, strongPiecePosition.y);
	}
	private void movePiecesRight(final int lineFrom, final int columnFrom, final int stopOn){
	    if(board[lineFrom][columnFrom].isEmpty()) {
	            return;
	    }
	    final int colDest = (columnFrom + 1) % board[lineFrom].length;
	    final Piece piece = board[lineFrom][columnFrom];
	    if(colDest == stopOn || board[lineFrom][colDest].isEmpty()){
	            board[lineFrom][colDest] = piece;
	            if(board[lineFrom][columnFrom] == piece) {
	                    board[lineFrom][columnFrom] = Piece.getEmptyPiece();
	            }
	            return;
	    }
	    movePiecesRight(lineFrom, colDest, stopOn);
	    board[lineFrom][colDest] = piece;
	    if(board[lineFrom][columnFrom] == piece) {
	            board[lineFrom][columnFrom] = Piece.getEmptyPiece();
	    }
	}

	private void movePiecesUp(final int id,final boolean topPiece){
		Point strongPiecePosition = getStrongPiecePosition(id, topPiece);
		movePiecesUp(strongPiecePosition.x, strongPiecePosition.y);
	}
	private void movePiecesUp(final int lineFrom, final int columnFrom){
		if(board[lineFrom][columnFrom].isEmpty()) {
			return;
		}
		final int lineDest = lineFrom - 1;
		if( lineDest == -1 ) {
			return;
		}
		if( board[lineDest][columnFrom].isEmpty() ){
			board[lineDest][columnFrom] = board[lineFrom][columnFrom];
			board[lineFrom][columnFrom] = Piece.getEmptyPiece();
			return;
		}
		movePiecesUp(lineDest, columnFrom);
		board[lineDest][columnFrom] = board[lineFrom][columnFrom];
		board[lineFrom][columnFrom] = Piece.getEmptyPiece();
	}

	public void setPieceAt(final int line, final int column, final Piece p){
		board[line][column] = p;
	}

	public Piece getPieceAt(Point p) {
		return getPieceAt(p.y, p.x);
	}

	public Point getStrongBottomPositionOrNull(int id) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				Piece p = board[i][j];
				if(p.isBottomPlayerStrongPiece() && p.getId() == id){
					return new Point(i,j);
				}
			}
		}
		return null;
	}

	public Point getStrongTopPositionOrNull(int id) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				Piece p = board[i][j];
				if(p.isTopPlayerStrongPiece() && p.getId() == id){
					return new Point(i,j);
				}
			}
		}
		return null;
	}
}
