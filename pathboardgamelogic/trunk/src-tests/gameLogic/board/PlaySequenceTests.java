package gameLogic.board;

import gameLogic.Game;
import gameLogic.gameFlow.gameStates.GameStatePuttingWeaks;

import org.junit.Assert;
import org.junit.Test;

import utils.GameUtils;

public class PlaySequenceTests {
	
	private final String boardWithBS1at0 = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 --- --- --- --- --- --- ---";
	
	private final String boardTest = 
		"TS1 TS2 TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 BS3 --- --- --- --- ---";
	
	private final String boardTestResult = 
		"TS1 TS2 TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"BS1 BS2 BS3 BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---";
	
	private final String playBottom = "3 3 3 1u 2u 3u";
	private final String invalidPlaySequence = "3 3 3 1u 2d 3u";
	private final String invalidPlaySequenceTryingToPlayForOtherPlayer = "3 3 3 1u 2u 3u 3 3 3 1d 2d 3d";
	
	@Test
	public void onePlayOnPlaySequenceTests() throws InvalidPlayStringException, InvalidPlayException{
		final Game game = new Game();
		final PlaySequenceValidator pSValidator = new PlaySequenceValidator(game);
		final PlaySequence pSPutStrongsBottom = new PlaySequence("0");
		final ValidPlaySequence vPSPutStrongsBottom = pSValidator.validatePlays(pSPutStrongsBottom, game.isTopPlayerTurn());
		pSValidator.play(vPSPutStrongsBottom);
		Assert.assertEquals(boardWithBS1at0,GameUtils.printBoard(game));
	}
	
	@Test
	public void normalPlaySequenceTest() throws InvalidPlayStringException, InvalidPlayException{
		final Board board = GameUtils.newBoardFromString(boardTest);
		final boolean isTopPlayerTurn = false;
		final Game game = new Game(board,new GameStatePuttingWeaks(isTopPlayerTurn));
		final PlaySequenceValidator playSequenceValidator = new PlaySequenceValidator(game);
		final PlaySequence playSequence = new PlaySequence(playBottom);
		final ValidPlaySequence validPlays = playSequenceValidator.validatePlays(playSequence, game.isTopPlayerTurn());
		playSequenceValidator.play(validPlays);
		Assert.assertEquals(boardTestResult, GameUtils.printBoard(game));
	}
	
	@Test
	public void wrongPlaySequenceTest() throws InvalidPlayStringException{
		final Board board = GameUtils.newBoardFromString(boardTest);
		final boolean isTopPlayerTurn = false;
		final Game game = new Game(board,new GameStatePuttingWeaks(isTopPlayerTurn));
		final PlaySequenceValidator playSequenceValidator = new PlaySequenceValidator(game);
		final PlaySequence playSequence = new PlaySequence(invalidPlaySequence);
		boolean error = false;
		try {
			playSequenceValidator.validatePlays(playSequence, game.isTopPlayerTurn());
		} catch (InvalidPlayException e) {
			error = true;
		}
		Assert.assertEquals(boardTest, GameUtils.printBoard(game));
		Assert.assertTrue(error);
	}

	@Test
	public void oneStringPlayOnPlaySequenceTests() throws InvalidPlayStringException, InvalidPlayException{
		final Game game = new Game();
		final PlaySequenceValidator pSValidator = new PlaySequenceValidator(game);
		final ValidPlaySequence vPSPutStrongsBottom = pSValidator.validatePlays("0", game.isTopPlayerTurn());
		pSValidator.play(vPSPutStrongsBottom);
		Assert.assertEquals(boardWithBS1at0,GameUtils.printBoard(game));
	}
	
	@Test
	public void normalStringPlaySequenceTest() throws InvalidPlayStringException, InvalidPlayException{
		final Board board = GameUtils.newBoardFromString(boardTest);
		final boolean isTopPlayerTurn = false;
		final Game game = new Game(board,new GameStatePuttingWeaks(isTopPlayerTurn));
		final PlaySequenceValidator playSequenceValidator = new PlaySequenceValidator(game);
		final ValidPlaySequence validPlays = playSequenceValidator.validatePlays(playBottom, game.isTopPlayerTurn());
		playSequenceValidator.play(validPlays);
		Assert.assertEquals(boardTestResult, GameUtils.printBoard(game));
	}
	
	@Test
	public void wrongStringPlaySequenceTest() throws InvalidPlayStringException{
		final Board board = GameUtils.newBoardFromString(boardTest);
		final boolean isTopPlayerTurn = false;
		final Game game = new Game(board,new GameStatePuttingWeaks(isTopPlayerTurn));
		final PlaySequenceValidator playSequenceValidator = new PlaySequenceValidator(game);
		boolean error = false;
		try {
			playSequenceValidator.validatePlays(invalidPlaySequence, game.isTopPlayerTurn());
		} catch (InvalidPlayException e) {
			error = true;
		}
		Assert.assertEquals(boardTest, GameUtils.printBoard(game));
		Assert.assertTrue(error);
	}
	
	@Test(expected=InvalidPlayException.class)
	public void tryToPlayForOtherPlaySequenceTest() throws InvalidPlayStringException, InvalidPlayException{
		final Board board = GameUtils.newBoardFromString(boardTest);
		final boolean isTopPlayerTurn = false;
		final Game game = new Game(board,new GameStatePuttingWeaks(isTopPlayerTurn));
		final PlaySequenceValidator playSequenceValidator = new PlaySequenceValidator(game);
		final PlaySequence playSequence = new PlaySequence(invalidPlaySequenceTryingToPlayForOtherPlayer);
		playSequenceValidator.validatePlays(playSequence, game.isTopPlayerTurn());
	}
	
}