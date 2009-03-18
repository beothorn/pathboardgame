package gameLogic;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Board {

	public static final int BOARD_SIZE = 8;

	private static int NO_PLAYER = 0;
	private static int TOP_PLAYER = 1;
	private static int BOTTOM_PLAYER = 2;
	private static int DRAW = 3;

	Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];

	private ArrayList<Integer> sortedStrongBottomIds;

	private ArrayList<Integer> sortedStrongTopIds;

	public Board() {
		reset();
	}
	
	public Board(final String board) {
		fromString(board);
	}

	private boolean addBottomPlayerPiece(final Piece piece, final int pieceColumn) {

		if(piece.isStrong() && !board[board.length-1][pieceColumn].isEmpty()) {
			return false;
		}

		for(int i = board.length-1; i>=0; i--){
			final Piece currentPiece = board[i][pieceColumn];
			if(currentPiece.isStrong()) {
				return false;
			}
			if(currentPiece.isEmpty()){
				return addBottomPlayerPieceAfterVerifyIfCan(piece, pieceColumn);
			}
		}
		return addBottomPlayerPieceAfterVerifyIfCan(piece, pieceColumn);
	}

	private boolean addBottomPlayerPieceAfterVerifyIfCan(final Piece piece,final int columnIndex) {
		movePiecesUp(board.length-1, columnIndex);
		board[board.length-1][columnIndex] = piece;
		return true;
	}

	public boolean addPiece(final Piece piece, final int pieceColumn){
		if(pieceColumn < 0 || pieceColumn >= BOARD_SIZE){
			throw new IllegalArgumentException("Piece column out of range:	pieceColumn = "+pieceColumn+" board size is "+BOARD_SIZE);
		}

		if(piece.isTopPlayerPiece()) {
			return addTopPlayerPiece(piece,pieceColumn);
		} else {
			return addBottomPlayerPiece(piece,pieceColumn);
		}
	}

	private boolean addTopPlayerPiece(final Piece piece,final int pieceColumn) {

		if(piece.isStrong() && !board[0][pieceColumn].isEmpty()) {
			return false;
		}

		for (final Piece[] element : board) {
			final Piece currentPiece = element[pieceColumn];
			if(currentPiece.isStrong()) {
				return false;
			}
			if(currentPiece.isEmpty()){
				return addTopPlayerPieceAfterVerifyIfCan(piece, pieceColumn);
			}
		}
		return addTopPlayerPieceAfterVerifyIfCan(piece, pieceColumn);
	}


	private boolean addTopPlayerPieceAfterVerifyIfCan(final Piece piece,final int columnIndex) {
		movePiecesDown(0, columnIndex);
		board[0][columnIndex] = piece;
		return true;
	}

	private void applyGravity(){
		for (int column = 0; column < board.length; column++){
			bubbleUp(column);
			bubbleDown(column);
		}
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

	@SuppressWarnings("unchecked")
	public Board copy() {
		final Board boardCopy = new Board();
		for (int i = 0; i < board.length; i++) {
			System.arraycopy(board[i], 0, boardCopy.board[i] , 0, BOARD_SIZE);
			fillSortedStrongIdsIfNeeded();
			boardCopy.sortedStrongBottomIds = (ArrayList<Integer>) sortedStrongBottomIds.clone();
			boardCopy.sortedStrongTopIds = (ArrayList<Integer>) sortedStrongTopIds.clone();
		}
		return boardCopy;
	}

	public void fromString(final String fillWith){
		final String[] split = fillWith.split("\n");
		reset();

		for (int line = 0; line < split.length; line++ ) {
			final String stringLine = split[line];
			for (int column = 0; column < stringLine.length(); column++) {
				final char charAt = stringLine.charAt(column);
				switch (charAt) {
				case Piece.PIECE_TOP_WEAK:
					setPieceAt(line, column, Piece.getTopWeakPiece());
					break;
				case Piece.PIECE_BOTTOM_WEAK:
					setPieceAt(line, column, Piece.getBottomWeakPiece());
					break;
				case Piece.PIECE_TOP_STRONG:
					setPieceAt(line, column, Piece.getTopStrongPiece());
					break;
				case Piece.PIECE_BOTTOM_STRONG:
					setPieceAt(line, column, Piece.getBottomStrongPiece());
					break;
				case Piece.PIECE_EMPTY:
					setPieceAt(line, column, Piece.getEmptyPiece());
				break;
				default:
					throw new IllegalArgumentException("Unknown piece char: "+charAt);
				}
			}
		}
	}

	public int getBoardColumnNumber(){
		return board[0].length;
	}

	public int getBoardLineNumber(){
		return board.length;
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

	public Point getStrongBottomByPositionInSequence(final int strongPositionInSequence){
		fillSortedStrongIdsIfNeeded();

		final int id = sortedStrongBottomIds.get(strongPositionInSequence-1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j].isBottomPlayerStrongPiece()){
					final int pieceId = board[i][j].getId();
					if(pieceId==id) {
						return new Point(j,i);
					}
				}
			}
		}
		return null;
	}
	
	public Point getStrongTopByPositionInSequence(final int strongPositionInSequence){
		fillSortedStrongIdsIfNeeded();
		
		final int id = sortedStrongTopIds.get(strongPositionInSequence-1);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j].isTopPlayerStrongPiece()){
					final int pieceId = board[i][j].getId();
					if(pieceId==id) {
						return new Point(j,i);
					}
				}
			}
		}
		return null;
	}

	private void fillSortedStrongIdsIfNeeded() {
		if(sortedStrongBottomIds==null){
			sortedStrongBottomIds = new ArrayList<Integer>();
			for (final Piece[] boardLines : board) {
				for (final Piece piece : boardLines) {
					if(piece.isBottomPlayerStrongPiece()){
						sortedStrongBottomIds.add(piece.getId());
					}
				}
			}
			Collections.sort(sortedStrongBottomIds);
		}
		
		if(sortedStrongTopIds==null){
			sortedStrongTopIds = new ArrayList<Integer>();
			for (final Piece[] boardLine : board) {
				for (final Piece piece : boardLine) {
					if(piece.isTopPlayerStrongPiece()){
						sortedStrongTopIds.add(piece.getId());
					}
				}
			}
			Collections.sort(sortedStrongTopIds);
		}
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

	public boolean isBottomTheWinner() {
		return getWinner() == Board.BOTTOM_PLAYER;
	}

	public boolean isTopTheWinner() {
		return getWinner() == Board.TOP_PLAYER;
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

	private void movePiecesLeft(final int lineFrom, final int columnFrom){
		movePiecesLeft(lineFrom, columnFrom, columnFrom);
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

	private void movePiecesRight(final int lineFrom, final int columnFrom){
		movePiecesRight(lineFrom, columnFrom, columnFrom);
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

	public boolean moveStrongPiece(final int lineFrom, final int columnFrom, final int lineTo,
			final int columnTo){
	
	
		if(lineFrom < 0 || lineFrom >= BOARD_SIZE) {
			return false;
		}
		if(columnFrom < 0 || columnFrom >= BOARD_SIZE) {
			return false;
		}
		if(lineTo < 0 || lineTo >= BOARD_SIZE) {
			return false;
		}
		if(columnTo < 0 || columnTo >= BOARD_SIZE) {
			return false;
		}
	
		final int horizontalDirection = columnTo - columnFrom;
		final int verticalDirection = lineTo -lineFrom;
	
		if(horizontalDirection != 0 && verticalDirection != 0) {
			return false;
		}
		if(horizontalDirection == 0 && verticalDirection == 0) {
			return false;
		}
	
		if(!board[lineFrom][columnFrom].isStrong()){
			return false;
		}
	
	
		boolean moveResult = false;
		if(horizontalDirection > 0){
			moveResult = moveStrongRightIfPossible(lineFrom, columnFrom);
		}
	
		if(horizontalDirection < 0){
			moveResult = moveStrongLeftIfPossible(lineFrom, columnFrom);
		}
	
		if(verticalDirection > 0){
			moveResult = moveStrongDownIfPossible(lineFrom, columnFrom);
		}
	
		if(verticalDirection < 0){
			moveResult = moveStrongUpIfPossible(lineFrom, columnFrom);
		}
	
		applyGravity();
		return moveResult;
	}

	private boolean moveStrongDownIfPossible(final int lineFromIndex,final int columnFromIndex) {
		if(lineFromIndex == board.length-1) {
			return false;
		}
		int strongCount = 0;
		for(int i = lineFromIndex+1; i < board.length; i++  ){
			if(board[i][columnFromIndex].isEmpty()){
				movePiecesDown(lineFromIndex, columnFromIndex);
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
			return false;
		}
		movePiecesDown(lineFromIndex, columnFromIndex);
		return true;
	}

	private boolean moveStrongLeftIfPossible(final int lineFromIndex,	final int columnFromIndex) {
		if(columnFromIndex == 0) {
			return false;
		}
		int strongCount = 0;
		for(int i = columnFromIndex -1; i != columnFromIndex; i = i-1 == -1?board[lineFromIndex].length-1:i-1   ){
			if(board[lineFromIndex][i].isEmpty()){
				movePiecesLeft(lineFromIndex, columnFromIndex);
				return true;
			}
			if(board[lineFromIndex][i].isStrong()) {
				strongCount++;
			}
			if(strongCount>1) {
				return false;
			}
		}
		movePiecesLeft(lineFromIndex, columnFromIndex);
		return true;
	}

	private boolean moveStrongRightIfPossible(final int lineFromIndex,final int columnFromIndex) {
		if(columnFromIndex == board[lineFromIndex].length-1) {
			return false;
		}
		int strongCount = 0;
		for(int i = columnFromIndex +1; i != columnFromIndex; i = (i+1)%board[lineFromIndex].length  ){
			if(board[lineFromIndex][i].isEmpty()){
				movePiecesRight(lineFromIndex, columnFromIndex);
				return true;
			}
			if(board[lineFromIndex][i].isStrong()) {
				strongCount++;
			}
			if(strongCount>1) {
				return false;
			}
		}
		movePiecesRight(lineFromIndex, columnFromIndex);
		return true;
	}

	private boolean moveStrongUpIfPossible(final int lineFromIndex, final int columnFromIndex) {
		if(lineFromIndex == 0) {
			return false;
		}
		if(board[lineFromIndex][columnFromIndex].isEmpty()) {
			return false;
		}
		int strongCount = 0;
		for(int i = lineFromIndex-1; i >= 0; i--  ){
			if(board[i][columnFromIndex].isEmpty()){
				movePiecesUp(lineFromIndex, columnFromIndex);
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
		movePiecesUp(lineFromIndex, columnFromIndex);
		return true;
	}

	public void reset() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = Piece.getEmptyPiece();
			}
		}
		sortedStrongBottomIds = null;
		sortedStrongTopIds = null;
		Piece.reset();
	}

	public void setPieceAt(final int line, final int column, final Piece p){
		board[line][column] = p;
	}

	public String getPlainPlaySequence(final String plays, final boolean isTopPlay){
		PlaySequence playSeq = new PlaySequence(plays);
		PlaySequence playSeqNormal = new PlaySequence();
		for (Play p : playSeq.getPlays()) {
			if(p.isMoveDirection()){
				playSeqNormal.addPlays(getPlaySequenceForMoveByIdPlay(p, isTopPlay));
			}else{
				playSeqNormal.addPlay(p);
			}
		}
		return playSeqNormal.toString();		
	}
	
	public PlaySequence getPlaySequenceForMoveByIdPlay(final Play moveByNumberPlay, final boolean isTopPlay) {
		if(!moveByNumberPlay.isMoveDirection()){
			throw new IllegalArgumentException("Move is not move to direction strong id: "+moveByNumberPlay);
		}
		
		final Point position;
		if(isTopPlay){
			position = getStrongTopByPositionInSequence(moveByNumberPlay.getPieceSequenceNumber());
		}else{
			position = getStrongBottomByPositionInSequence(moveByNumberPlay.getPieceSequenceNumber());
		}
		
		//(line,column) <- seems switched from the position notation doesn't it?
		final Point from = new Point(position.y,position.x);
		final Point to;
		switch(moveByNumberPlay.getDirection()){
		case Play.UP:
			to = new Point(position.y-1,position.x);
			break;
		case Play.DOWN:
			to = new Point(position.y+1,position.x);
			break;
		case Play.LEFT:
			to = new Point(position.y,position.x-1);
			break;
		case Play.RIGHT:
			to = new Point(position.y,position.x+1);
			break;
		default:
			throw new IllegalArgumentException("Illegal play: "+moveByNumberPlay);
		}
		
		return new PlaySequence(new Play(from),new Play(to));
	}

	public int getPiecePositionInSequence(final int id) {
		fillSortedStrongIdsIfNeeded();
		
		for (int i = 0; i < sortedStrongBottomIds.size(); i++) {
			final Integer strongId = sortedStrongBottomIds.get(i);
			if(strongId == id){
				return i+1;
			}
		}
		
		for (int i = 0; i < sortedStrongTopIds.size(); i++) {
			final Integer strongId = sortedStrongTopIds.get(i);
			if(strongId == id){
				return i+1;
			}
		}
		throw new IllegalArgumentException("ID: "+id+" not found");
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

	public void switchSides() {
		String string = toString();
		String newBoard = "";
		for (int i = string.length()-2; i >= 0; i--) {
			newBoard += string.charAt(i);
		}
		
		newBoard = newBoard.replace(Piece.PIECE_BOTTOM_STRONG, 'X');
		newBoard = newBoard.replace(Piece.PIECE_TOP_STRONG, Piece.PIECE_BOTTOM_STRONG);
		newBoard = newBoard.replace('X', Piece.PIECE_TOP_STRONG);
		
		newBoard = newBoard.replace(Piece.PIECE_BOTTOM_WEAK, 'X');
		newBoard = newBoard.replace(Piece.PIECE_TOP_WEAK, Piece.PIECE_BOTTOM_WEAK);
		newBoard = newBoard.replace('X', Piece.PIECE_TOP_WEAK);
		
		fromString(newBoard);
	}

	public static PlaySequence invertPlay(final PlaySequence playSequence) {
		PlaySequence inverted = new PlaySequence();
		for (Play play : playSequence.getPlays()) {
			if(play.isMoveDirection())
				throw new IllegalArgumentException("Cant invert move to direction play");
			if(play.isNextState()){
				inverted.addPlay(play);
			}else{
				int line = BOARD_SIZE - 1 -play.getLine();
				int column = BOARD_SIZE - 1 -play.getColumn();
				inverted.addPlay(new Play(line,column));
			}
		}
		return inverted;
	}

	public boolean isGameDraw() {
		return getWinner() == Board.DRAW;
	}

	public boolean isGameEnded() {
		return isGameDraw() || isTopTheWinner() || isBottomTheWinner();
	}
}
