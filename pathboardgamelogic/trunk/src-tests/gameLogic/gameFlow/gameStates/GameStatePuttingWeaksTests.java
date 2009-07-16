package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import junit.framework.Assert;

import org.junit.Test;

import utils.GameUtils;

public class GameStatePuttingWeaksTests {
	
	@Test(expected = InvalidPlayException.class)
	public void testPuttingWeaksMoveStrongPlay() throws InvalidPlayException{
		final String testMoveTopStrong = 
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- TS2 --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---";
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameStatePuttingWeaks = new GameStatePuttingWeaks(isTopPlayerTurn);
		final Play play = new Play(2,'u');
		final Board board = GameUtils.newBoardFromString(testMoveTopStrong);
		gameStatePuttingWeaks.validatePlay(play, board,isTopPlayerTurn);
	}
	
	@Test
	public void testPutingWeaksNextStatePlay() throws InvalidPlayException, InvalidPlayStringException{
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameStatePuttingWeaks = new GameStatePuttingWeaks(isTopPlayerTurn);
		Play play = new Play(Play.NEXT_STATE);
		Board board = new Board();
		ValidPlay validPlay = gameStatePuttingWeaks.validatePlay(play, board,isTopPlayerTurn);
		GameState newGameState = gameStatePuttingWeaks.play(validPlay, board);
		Assert.assertTrue(newGameState instanceof GameStateMovingStrongs);
		Assert.assertTrue(newGameState.isTopPlayerTurn());
	}
	
	@Test
	public void testPutingWeaksStateAdvance() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameState gameState = new GameStatePuttingWeaks(isTopPlayerTurn);
		Board board = new Board();
		
		for(int i=0; i < GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;i++){
			final Play play = new Play(i);
			final ValidPlay validPlay = gameState.validatePlay(play, board,gameState.isTopPlayerTurn());
			Assert.assertTrue(gameState.isTopPlayerTurn());
			gameState = gameState.play(validPlay, board);
		}
		Assert.assertTrue(gameState.isTopPlayerTurn());
		Assert.assertTrue(gameState instanceof GameStateMovingStrongs);
	}
	
	@Test
	public void testEndGame() throws InvalidPlayException{
		final String almostEndedGame = 
			"TWK --- TS1 TS2 TS3 --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"--- --- --- --- --- --- --- ---";
		
		final String testEndGame = 
			"TWK --- TS1 TS2 TS3 --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---\n" +
			"TWK --- --- --- --- --- --- ---";
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameState = new GameStatePuttingWeaks(isTopPlayerTurn);
		Board board = GameUtils.newBoardFromString(almostEndedGame);
		Play play = new Play(0);
		ValidPlay validPlay = gameState.validatePlay(play, board, isTopPlayerTurn);
		GameState newGameState = gameState.play(validPlay, board);
		Assert.assertEquals(testEndGame, GameUtils.printBoard(board));
		Assert.assertTrue(newGameState instanceof GameStateGameEnded);
		Assert.assertTrue(newGameState.isGameEnded());
	}
	
}
