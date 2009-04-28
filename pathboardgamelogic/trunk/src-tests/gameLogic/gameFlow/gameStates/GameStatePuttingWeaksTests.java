package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class GameStatePuttingWeaksTests {

	private final String testMoveTopStrong = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- TS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	private final String testEndGame = 
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"TWK --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	@Test(expected = InvalidPlayException.class)
	public void testPuttingWeaksMoveStrongPlay() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameStatePuttingWeaks = new GameStatePuttingWeaks(isTopPlayerTurn);
		final Play play = new Play(2,'u');
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		gameStatePuttingWeaks.validatePlay(play, board);
	}
	
	@Test
	public void testPutingWeaksNextStatePlay() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameStatePuttingWeaks = new GameStatePuttingWeaks(isTopPlayerTurn);
		Play play = new Play(Play.NEXT_STATE);
		Board board = new Board();
		ValidPlay validPlay = gameStatePuttingWeaks.validatePlay(play, board);
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
			final ValidPlay validPlay = gameState.validatePlay(play, board);
			Assert.assertTrue(gameState.isTopPlayerTurn());
			gameState = gameState.play(validPlay, board);
		}
		Assert.assertTrue(gameState.isTopPlayerTurn());
		Assert.assertTrue(gameState instanceof GameStateMovingStrongs);
	}
	
	@Test
	public void testEndGame() throws InvalidPlayException{
		boolean isTopPlayerTurn = true;
		GameStatePuttingWeaks gameState = new GameStatePuttingWeaks(isTopPlayerTurn);
		Play play = new Play(0);
		Board board = BoardUtils.newBoardFromString(testEndGame);
		ValidPlay validPlay = gameState.validatePlay(play, board);
		GameState newGameState = gameState.play(validPlay, board);
		Assert.assertTrue(newGameState instanceof GameStateGameEnded);
		Assert.assertTrue(newGameState.isGameEnded());
	}
	
}