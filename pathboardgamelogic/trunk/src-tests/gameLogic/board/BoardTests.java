package gameLogic.board;

import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class BoardTests {
	
	private final String boardWithBS1at0 = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 --- --- --- --- --- --- ---";
	
	private final String testAdd3BottomStrongPiecesAnd1Weak =
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 BS3 BWK --- --- --- ---";
	
	private final String boardWithTS1at0 = 
		"TS1 --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrong = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- TS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testCantMoveTopWeakBlock = 
		"TWK TS3 --- TS1 TWK TS2 TWK TWK\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BS1 --- --- --- ---\n" +
		"--- --- --- BS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongRight = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- TS2 --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongRightPushingPieces = 
		"TWK TWK TWK TWK TS2 TWK TWK TS1\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongRightPushingPiecesResult = 
		"TS1 TWK TWK TWK TWK TS2 TWK TWK\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongLeft = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- TS2 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongUp = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- TS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testMoveTopStrongDown = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- TS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testAdd3TopStrongPiecesAnd1Weak =
		"TS1 TS2 TS3 TWK --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testTopWins =
		"--- --- TWK --- --- --- --- ---\n" +
		"TWK --- TWK TWK --- TWK TWK TWK\n" +
		"TWK --- --- TWK --- TWK --- ---\n" +
		"TWK TWK --- TWK TWK TWK --- ---\n" +
		"--- TWK --- --- --- --- --- ---\n" +
		"--- TWK --- --- --- --- --- ---\n" +
		"--- TWK --- --- --- --- --- ---\n" +
		"--- TWK --- --- --- --- --- ---";
	
	private final String testTopWinsBifurcationLeft =
		"--- --- --- TWK --- --- --- ---\n" +
		"--- --- --- TWK --- --- --- ---\n" +
		"--- --- --- TWK TWK TWK TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- TWK TWK ---\n" +
		"--- --- --- TWK --- --- --- ---";
	
	private final String testTopWinsBifurcationRight =
		"--- --- --- TWK --- --- --- ---\n" +
		"--- --- --- TWK --- --- --- ---\n" +
		"--- --- --- TWK TWK TWK TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- --- TWK ---\n" +
		"--- --- --- TWK --- TWK TWK ---\n" +
		"--- --- --- --- --- TWK --- ---";
	
	private final String testBottomWins =
		"BWK --- --- --- --- --- --- ---\n" +
		"BWK BWK --- --- --- BWK --- ---\n" +
		"--- BWK --- --- --- BWK --- ---\n" +
		"--- BWK BWK BWK BWK BWK --- ---\n" +
		"--- BWK BWK --- --- BWK --- ---\n" +
		"--- BWK BWK --- --- BWK --- ---\n" +
		"--- --- --- BWK BWK BWK --- ---\n" +
		"--- --- --- BWK --- --- --- ---";
	
	private final String testDraw =
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---\n" +
		"--- --- --- BWK TWK --- --- ---";
	
	private final String testGravity =
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"TWK TWK --- --- --- --- --- ---\n" +
		"--- TWK TS1 BWK BS1 --- --- ---\n" +
		"--- --- TWK TWK BWK --- --- ---\n" +
		"--- TWK TWK --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- TWK TWK --- --- --- --- ---";
	
	private final String testGravityResult =
		"TWK TWK --- --- --- --- --- ---\n" +
		"--- TWK --- --- --- --- --- ---\n" +
		"--- TWK --- --- --- --- --- ---\n" +
		"--- TWK TS1 BWK BS1 --- --- ---\n" +
		"--- --- TWK TWK --- --- --- ---\n" +
		"--- --- TWK --- --- --- --- ---\n" +
		"--- --- TWK --- --- --- --- ---\n" +
		"--- --- --- --- BWK --- --- ---";
	
	private final String testPushingPiece =
		"--- --- TWK --- --- --- TS1 TWK\n" +
		"--- --- BS2 --- --- --- BS1 ---\n" +
		"--- --- TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- TS2 --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---";
	
	private final String testPushingPieceTS1Right =
		"TWK --- TWK --- --- --- --- TS1\n" +
		"--- --- BS2 --- --- --- BS1 ---\n" +
		"--- --- TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- TS2 --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---";
	
	private final String testPushingPieceTS3UpDown =
		"--- --- BS2 --- --- --- TS1 TWK\n" +
		"--- --- --- --- --- --- BS1 ---\n" +
		"--- --- TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- TS2 --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---";
	
	private final String testPushingPieceTS2DownUp =
		"--- --- TWK --- --- --- TS1 TWK\n" +
		"--- --- BS2 --- --- --- BS1 ---\n" +
		"--- --- TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- TS2 --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- TWK --- ---\n" +
		"--- --- --- --- --- TWK --- ---";
	
	@Test
	public void testAddBottomStrongPiece() throws InvalidPlayException{
		boolean forTopPlayer = false;
		Board board = new Board();
		addStrongPieceIn0(board,forTopPlayer);
		Assert.assertEquals(boardWithBS1at0, BoardUtils.printBoard(board));
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testInvalidAddBottomStrongPiece() throws InvalidPlayException{
		boolean forTopPlayer = false;
		addStrongPieceIn0(BoardUtils.newBoardFromString(boardWithBS1at0),forTopPlayer);
	}
	
	@Test
	public void testAdd3BottomStrongPiecesAnd1Weak() throws InvalidPlayException{
		boolean forTopPlayer = false;
		final Board board = new Board();
		add3StrongAnd1Weak(board,forTopPlayer);
		Assert.assertEquals(testAdd3BottomStrongPiecesAnd1Weak, BoardUtils.printBoard(board));
	}

	@Test(expected = InvalidPlayException.class)
	public void testCantMoveTopStrongRight() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testCantMoveTopWeakBlock);
		final Play play = new Play(1,'r');
		board.validatePlay(play, forTopPlayer);
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testCantMoveTopStrongLeft() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testCantMoveTopWeakBlock);
		final Play play = new Play(3,'l');
		board.validatePlay(play, forTopPlayer);
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testCantMoveTopStrongUp() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testCantMoveTopWeakBlock);
		final Play play = new Play(1,'u');
		board.validatePlay(play, forTopPlayer);
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testCantMoveTopStrongDown() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testCantMoveTopWeakBlock);
		final Play play = new Play(1,'d');
		board.validatePlay(play, forTopPlayer);
	}
	
	
	@Test
	public void testMoveTopStrongRight() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		final Play play = new Play(2,'r');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testMoveTopStrongRight, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testMoveTopStrongUp() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		final Play play = new Play(2,'u');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testMoveTopStrongUp, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testMoveTopStrongDown() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		final Play play = new Play(2,'d');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testMoveTopStrongDown, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testMoveTopStrongLeft() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		final Play play = new Play(2,'l');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testMoveTopStrongLeft, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testMoveTopStrongRightPushingPieces() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrongRightPushingPieces);
		final Play play = new Play(2,'r');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testMoveTopStrongRightPushingPiecesResult, BoardUtils.printBoard(board));
	}
	
	private void add3StrongAnd1Weak(final Board board,final boolean forTopPlayer)
			throws InvalidPlayException {
		
		for(int i = 0; i<3; i++){			
			final Play play = new Play(i);
			final ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
			board.play(validPlay, forTopPlayer);
		}
		
		final Play play4 = new Play(3);
		final ValidPlay validPlay4 = board.validatePlay(play4, forTopPlayer);
		board.play(validPlay4, forTopPlayer);
	}
	
	@Test
	public void testAddTopStrongPiece() throws InvalidPlayException{
		boolean forTopPlayer = true;
		Board board = new Board();
		addStrongPieceIn0(board,forTopPlayer);
		Assert.assertEquals(boardWithTS1at0, BoardUtils.printBoard(board));
	}

	private void addStrongPieceIn0(final Board board,boolean forTopPlayer)
			throws InvalidPlayException {
		final Play play = new Play(0);
		final ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testInvalidAddTopStrongPiece() throws InvalidPlayException{
		boolean forTopPlayer = true;
		addStrongPieceIn0(BoardUtils.newBoardFromString(boardWithTS1at0),forTopPlayer);
	}
	
	@Test
	public void testAdd3TopStrongPiecesAnd1Weak() throws InvalidPlayException{
		boolean forTopPlayer = true;
		final Board board = new Board();
		add3StrongAnd1Weak(board,forTopPlayer);
		Assert.assertEquals(testAdd3TopStrongPiecesAnd1Weak, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testTopWins(){
		final Board board = BoardUtils.newBoardFromString(testTopWins);
		Assert.assertTrue(board.isGameEnded());
		Assert.assertFalse(board.isGameDraw());
		Assert.assertFalse(board.isBottomTheWinner());
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testTopWinsBifurcationLeft(){
		final Board board = BoardUtils.newBoardFromString(testTopWinsBifurcationLeft);
		Assert.assertTrue(board.isGameEnded());
		Assert.assertFalse(board.isGameDraw());
		Assert.assertFalse(board.isBottomTheWinner());
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testTopWinsBifurcationRight(){
		final Board board = BoardUtils.newBoardFromString(testTopWinsBifurcationRight);
		Assert.assertTrue(board.isGameEnded());
		Assert.assertFalse(board.isGameDraw());
		Assert.assertFalse(board.isBottomTheWinner());
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testBottomWins(){
		final Board board = BoardUtils.newBoardFromString(testBottomWins);
		Assert.assertTrue(board.isGameEnded());
		Assert.assertFalse(board.isGameDraw());
		Assert.assertTrue(board.isBottomTheWinner());
		Assert.assertFalse(board.isTopTheWinner());
	}
	
	@Test
	public void testDraw(){
		final Board board = BoardUtils.newBoardFromString(testDraw);
		Assert.assertTrue(board.isGameEnded());
		Assert.assertTrue(board.isGameDraw());
		Assert.assertFalse(board.isBottomTheWinner());
		Assert.assertFalse(board.isTopTheWinner());
	}
	
	@Test
	public void testGravity(){
		final Board board = BoardUtils.newBoardFromString(testGravity);
		board.applyGravity();
		Assert.assertEquals(testGravityResult, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testPushingPieceTS2DownUp() throws InvalidPlayException{
		final Board board = BoardUtils.newBoardFromString(testPushingPiece);
		boolean forTopPlayer = true;
		final Play play = new Play(2,'d');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		final Play play2 = new Play(2,'u');
		ValidPlay validPlay2 = board.validatePlay(play2, forTopPlayer);
		board.play(validPlay2, forTopPlayer);
		Assert.assertEquals(testPushingPieceTS2DownUp, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testPushingPieceTS1Right() throws InvalidPlayException{
		final Board board = BoardUtils.newBoardFromString(testPushingPiece);
		boolean forTopPlayer = true;
		final Play play = new Play(1,'r');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		Assert.assertEquals(testPushingPieceTS1Right, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testPushingPieceTS3UpDown() throws InvalidPlayException{
		final Board board = BoardUtils.newBoardFromString(testPushingPiece);
		boolean forTopPlayer = true;
		final Play play = new Play(3,'u');
		ValidPlay validPlay = board.validatePlay(play, forTopPlayer);
		board.play(validPlay, forTopPlayer);
		final Play play2 = new Play(3,'d');
		ValidPlay validPlay2 = board.validatePlay(play2, forTopPlayer);
		board.play(validPlay2, forTopPlayer);
		Assert.assertEquals(testPushingPieceTS3UpDown, BoardUtils.printBoard(board));
	}
	
	@Test
	public void testNextStateIsValid() throws InvalidPlayException{
		final Board board = new Board();
		final Play play = new Play(Play.NEXT_STATE);
		board.validatePlay(play, true);
	}
}
