package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class GameStateMovingStrongsTests {

	private final String testMoveTopStrong = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- TS1 TS2 TS3 --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testEndGame = 
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- --- TWK --- --- ---\n" +
		"--- --- --- TS2 TWK --- --- ---\n" +
		"--- --- TS1 TWK --- --- --- ---";
	
	@Test(expected = InvalidPlayException.class)
	public void testMovingStrongsAddPiecePlay() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameStateMovingStrongs gameStateMovingStrongs = new GameStateMovingStrongs(isTopPlayerTurn);
		final Play play = new Play(2);
		final Board board = new Board();
		gameStateMovingStrongs.validatePlay(play, board);
	}
	
	@Test
	public void testMovingStrongsNextStatePlay() throws InvalidPlayException, InvalidPlayStringException{
		boolean isTopPlayerTurn = true;
		GameStateMovingStrongs gameStateMovingStrongs = new GameStateMovingStrongs(isTopPlayerTurn);
		Play play = new Play(Play.NEXT_STATE);
		Board board = new Board();
		ValidPlay validPlay = gameStateMovingStrongs.validatePlay(play, board);
		GameState newGameState = gameStateMovingStrongs.play(validPlay, board);
		Assert.assertTrue(newGameState instanceof GameStatePuttingWeaks);
		Assert.assertFalse(newGameState.isTopPlayerTurn());
	}
	
	@Test
	public void testMovingStrongsStateAdvance() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameState gameState = new GameStateMovingStrongs(isTopPlayerTurn);
		Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		
		for(int i=1; i < GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE+1;i++){
			final Play play = new Play(i,'u');
			final ValidPlay validPlay = gameState.validatePlay(play, board);
			Assert.assertTrue(gameState.isTopPlayerTurn());
			gameState = gameState.play(validPlay, board);
		}
		Assert.assertFalse(gameState.isTopPlayerTurn());
		Assert.assertTrue(gameState instanceof GameStatePuttingWeaks);
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testTryToMoveStrongTwice() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameState gameState = new GameStateMovingStrongs(isTopPlayerTurn);
		Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		final Play play = new Play(1,'u');
		final ValidPlay validPlay = gameState.validatePlay(play, board);
		gameState.play(validPlay, board);
		final Play playAgain = new Play(1,'u');
		gameState.validatePlay(playAgain, board);
	}
	
	@Test
	public void testEndGame() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameStateMovingStrongs gameState = new GameStateMovingStrongs(isTopPlayerTurn);
		Play play = new Play(1,'r');
		Board board = BoardUtils.newBoardFromString(testEndGame);
		ValidPlay validPlay = gameState.validatePlay(play, board);
		GameState newGameState = gameState.play(validPlay, board);
		Assert.assertTrue(newGameState instanceof GameStateGameEnded);
		Assert.assertTrue(newGameState.isGameEnded());
	}
}
