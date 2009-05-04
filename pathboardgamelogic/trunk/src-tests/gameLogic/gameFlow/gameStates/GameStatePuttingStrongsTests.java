package gameLogic.gameFlow.gameStates;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class GameStatePuttingStrongsTests {

	private final String testMoveTopStrong = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- TS2 --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---";
	
	@Test(expected = InvalidPlayException.class)
	public void testPutingStrongsNextStatePlay() throws InvalidPlayException, InvalidPlayStringException{
		boolean isFirstState = true;
		boolean isTopPlayerTurn = true;
		GameStatePuttingStrongs gameStatePuttingStrongs = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		Play play = new Play(Play.NEXT_STATE);
		Board board = new Board();
		gameStatePuttingStrongs.validatePlay(play, board,gameStatePuttingStrongs.isTopPlayerTurn());
	}
	
	@Test(expected = InvalidPlayException.class)
	public void testPutingStrongsMoveStrongPlay() throws InvalidPlayException{
		boolean isFirstState = true;
		boolean isTopPlayerTurn = true;
		GameStatePuttingStrongs gameStatePuttingStrongs = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		final Play play = new Play(2,'u');
		final Board board = BoardUtils.newBoardFromString(testMoveTopStrong);
		gameStatePuttingStrongs.validatePlay(play, board, gameStatePuttingStrongs.isTopPlayerTurn());
	}
	
	@Test
	public void testPutingStrongsStateAdvanceFirst() throws InvalidPlayException{
		boolean isFirstState = true;
		boolean isTopPlayerTurn = true;
		GameState gameState = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		Board board = new Board();
		
		for(int i=0; i < GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;i++){
			final Play play = new Play(i);
			final ValidPlay validPlay = gameState.validatePlay(play, board,gameState.isTopPlayerTurn());
			Assert.assertTrue(gameState.isTopPlayerTurn());
			gameState = gameState.play(validPlay, board);
		}
		Assert.assertFalse(gameState.isTopPlayerTurn());
	}
	
	@Test
	public void testPutingStrongsStateAdvanceNotFirst() throws InvalidPlayException{
		boolean isFirstState = false;
		boolean isTopPlayerTurn = true;
		GameState gameState = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		Board board = new Board();
		
		for(int i=0; i < GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;i++){
			final Play play = new Play(i);
			final ValidPlay validPlay = gameState.validatePlay(play, board,gameState.isTopPlayerTurn());
			Assert.assertTrue(gameState instanceof GameStatePuttingStrongs);
			gameState = gameState.play(validPlay, board);
		}
		Assert.assertFalse(gameState instanceof GameStatePuttingStrongs);
	}
}
