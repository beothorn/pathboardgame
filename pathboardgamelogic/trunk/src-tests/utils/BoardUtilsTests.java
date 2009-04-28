package utils;

import gameLogic.board.Board;
import junit.framework.Assert;

import org.junit.Test;

public class BoardUtilsTests {
	
	private final String board = 
		"TWK TWK TWK TWK TWK TWK TWK TWK\n" +
		"TWK --- TS1 --- TS2 --- TS3 ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- BS3 --- BS2 --- BS1 BWK\n" +
		"BWK BWK BWK BWK BWK BWK BWK BWK";
	
	private final String boardNormal = 
		"--- TWK --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- TS1 BS1 TS2 --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- TS3 BWK --- --- BWK ---\n" +
		"--- --- BS3 BWK --- --- BWK BWK\n" +
		"--- --- BS2 BWK --- --- BWK BWK";
	
	private final String boardInverted = 
		"TWK TWK --- --- TWK TS2 --- ---\n" +
		"TWK TWK --- --- TWK TS3 --- ---\n" +
		"--- TWK --- --- TWK BS3 --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- BS2 TS1 BS1 --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- BWK ---";
	
	@Test
	public void testBoardFromStringToString(){
		Board newBoardFromString = BoardUtils.newBoardFromString(board);
		Assert.assertEquals(board, BoardUtils.printBoard(newBoardFromString));
	}
	
	@Test
	public void testSwitchSides(){
		final Board board = BoardUtils.newBoardFromString(boardNormal);
		BoardUtils.switchSides(board);
		Assert.assertEquals(boardInverted, BoardUtils.printBoard(board));
		BoardUtils.switchSides(board);
		Assert.assertEquals(boardNormal, BoardUtils.printBoard(board));
	}
}
