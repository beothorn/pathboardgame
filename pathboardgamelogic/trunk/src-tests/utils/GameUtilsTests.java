package utils;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.PlaySequence;
import junit.framework.Assert;

import org.junit.Test;

public class GameUtilsTests {
	
	@Test
	public void testBoardFromStringToString(){
		final String boardString = 
			"TWK TWK TWK TWK TWK TWK TWK TWK\n" +
			"TWK --- TS1 --- TS2 --- TS3 ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- BS3 --- BS2 --- BS1 BWK\n" +
			"BWK BWK BWK BWK BWK BWK BWK BWK";
		
		Board newBoardFromString = GameUtils.newBoardFromString(boardString);
		Assert.assertEquals(boardString, GameUtils.printBoard(newBoardFromString));
	}
	
	@Test
	public void testSwitchSides(){
		final String boardNormal = 
			"--- TWK --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- TS1 BS1 TS2 --- --- ---\n" +
			"--- --- --- BWK --- --- --- ---\n" +
			"--- --- --- BWK --- --- --- ---\n" +
			"--- --- TS3 BWK --- --- BWK ---\n" +
			"--- --- BS3 BWK --- --- BWK BWK\n" +
			"--- --- BS2 BWK --- --- BWK BWK";
		final String boardInverted = 
			"TWK TWK --- --- TWK TS2 --- ---\n" +
			"TWK TWK --- --- TWK TS3 --- ---\n" +
			"--- TWK --- --- TWK BS3 --- ---\n" +
			"--- --- --- --- TWK --- --- ---\n" +
			"--- --- --- --- TWK --- --- ---\n" +
			"--- --- --- BS2 TS1 BS1 --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- BWK ---";
		
		Board board = GameUtils.newBoardFromString(boardNormal);
		board = GameUtils.newBoardSwitchedSides(board);
		Assert.assertEquals(boardInverted, GameUtils.printBoard(board));
		board = GameUtils.newBoardSwitchedSides(board);
		Assert.assertEquals(boardNormal, GameUtils.printBoard(board));
	}
	
	@Test
	public void testInvertPlay() throws InvalidPlayStringException{
		final String normalPlay = "0 2 6 1u 2d 3r";
		final String invertedPlay = "7 5 1 1d 2u 3l";
		PlaySequence playSequence = new PlaySequence(normalPlay);
		PlaySequence invertPlay = GameUtils.invertPlay(playSequence);
		Assert.assertEquals(invertedPlay, invertPlay.toString());
	}
}