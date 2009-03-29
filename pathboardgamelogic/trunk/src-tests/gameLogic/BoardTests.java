package gameLogic;


import junit.framework.Assert;

import org.junit.Test;

public class BoardTests {
	
	private static final String normalPlay =   "(0,0)|(0,7)|(5,5)|(6,0)|"+Play.NEXT_STATE;
	private static final String invertedPlay = "(7,7)|(7,0)|(2,2)|(1,7)|"+Play.NEXT_STATE;

	private static final String boardString =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"00000000\n" +
		"20000000\n" +
		"24042400\n";
	
	private static final String bottomWinByNewPathRules =
		"33030200\n" +
		"00000200\n" +
		"00000200\n" +
		"00002200\n" +
		"00002400\n" +
		"00002000\n" +
		"00002000\n" +
		"04042000\n";
	
	private static final String topWinByNewPathRules =
		"00100000\n" +
		"00130000\n" +
		"00113322\n" +
		"00011111\n" +
		"00222201\n" +
		"00111121\n" +
		"00100111\n" +
		"04144000\n";
	
	private static final String topWinByNewPathRulesWithLoop =
		"00100000\n" +
		"00130000\n" +
		"00113322\n" +
		"00011111\n" +
		"00212201\n" +
		"00111121\n" +
		"00100111\n" +
		"04144010\n";
	
	private static final String topWinByNewPathRulesWithBifurcationRight =
		"00100000\n" +
		"00130000\n" +
		"00113322\n" +
		"00011111\n" +
		"00212201\n" +
		"00110021\n" +
		"00100011\n" +
		"04044010\n";
	
	private static final String topWinByNewPathRulesWithBifurcationLeft =
		"00100000\n" +
		"00130000\n" +
		"00113322\n" +
		"00011111\n" +
		"00212201\n" +
		"00110021\n" +
		"00100011\n" +
		"04144000\n";
	
	
	private static final String draw =
		"20100333\n" +
		"20100000\n" +
		"20100000\n" +
		"20100000\n" +
		"20100000\n" +
		"20100000\n" +
		"20100000\n" +
		"24144000\n";
	
	private static final String bottomStrongsCount =
		"33030000\n" +
		"00000000\n" +
		"00000000\n" +
		"00002000\n" +
		"00002000\n" +
		"00002000\n" +
		"00002000\n" +
		"04042000\n";
	
	private static final String bottomView =
		"33030200\n" +
		"00000200\n" +
		"00000200\n" +
		"00002200\n" +
		"00002400\n" +
		"00002000\n" +
		"00002000\n" +
		"04042000\n";
	
	private static final String topView =
		"00013030\n" +
		"00010000\n" +
		"00010000\n" +
		"00310000\n" +
		"00110000\n" +
		"00100000\n" +
		"00100000\n" +
		"00104044\n";
	
	@Test
	public void testGetPlaySequenceFromMoveId(){
		final Board board = new Board(boardString);
		final Piece pieceAt71 = board.getPieceAt(7, 1);
		final int idNumber = pieceAt71.getId();
		final String move = Play.MOVE+idNumber+Play.UP;
		final Play moveByIdPlay = new Play(move);
		final PlaySequence expected = new PlaySequence(new Play(7,1),new Play(6,1));
		final boolean isTopPlay = false;
		Assert.assertEquals(expected, board.getPlaySequenceForMoveByIdPlay(moveByIdPlay,isTopPlay));
	}
	
	@Test
	public void testTopWin(){
		final Board board = new Board(topWinByNewPathRules);
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testTopWinWithLoop(){
		final Board board = new Board(topWinByNewPathRulesWithLoop);
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testTopWinWithBifurcationRight(){
		final Board board = new Board(topWinByNewPathRulesWithBifurcationRight);
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testTopWinWithBifurcationLeft(){
		final Board board = new Board(topWinByNewPathRulesWithBifurcationLeft);
		Assert.assertTrue(board.isTopTheWinner());
	}
	
	@Test
	public void testBottomWin(){
		final Board board = new Board(bottomWinByNewPathRules);
		Assert.assertTrue(board.isBottomTheWinner());
	}
	
	@Test
	public void testDraw(){
		final Board board = new Board(draw);
		Assert.assertFalse(board.isTopTheWinner());
		Assert.assertFalse(board.isBottomTheWinner());
		Assert.assertTrue(board.isGameDraw());
	}
	
	@Test
	public void testBoardSwitchSides(){
		final Board board = new Board(topView);
		board.switchSides();
		Assert.assertEquals(bottomView, board.toString());
	}
	
	@Test
	public void testCountBottomStrong(){
		final Board board = new Board(bottomStrongsCount);
		Assert.assertEquals(2, board.countStrongBottoms());
	}
	
	@Test
	public void testInvertPlay(){
		final int i = (5+7)%7;
		System.out.println(i);
		final PlaySequence playSequence = new PlaySequence(normalPlay);
		final PlaySequence invertPlay = Board.invertPlay(playSequence);
		Assert.assertEquals(invertedPlay, invertPlay.toString());
	}
}
