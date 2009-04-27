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
	
	@Test
	public void testBoardFromStringToString(){
		Board newBoardFromString = BoardUtils.newBoardFromString(board);
		Assert.assertEquals(board, BoardUtils.printBoard(newBoardFromString));
	}
}
