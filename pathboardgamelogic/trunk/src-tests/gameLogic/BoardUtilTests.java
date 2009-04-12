package gameLogic;

import junit.framework.Assert;

import org.junit.Test;

public class BoardUtilTests {
	
	private static final String topStrongId1 = "TS1";
	private static final String bottomWeak = "BWK";
	
	private static final String boardStringWithIds =
		"TS1 TS2 --- TS3 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BWK --- --- --- --- --- --- ---\n" +
		"BWK BS1 --- BS2 BWK BS3 --- ---";
	
	@Test(expected=RuntimeException.class)
	public void testNewBoardFromString(){
		Board b = BoardUtils.newBoardFromString(boardStringWithIds);
		Assert.assertEquals(boardStringWithIds, BoardUtils.printBoard(b));
	}
}
