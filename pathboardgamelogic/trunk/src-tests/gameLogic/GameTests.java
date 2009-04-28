package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.gameFlow.gameStates.GameState;
import junit.framework.Assert;

import org.junit.Test;

import utils.BoardUtils;

public class GameTests {

	private final String testLock = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 --- --- --- --- --- --- ---";
	
	private final String testLock2 = 
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 --- --- --- --- --- ---";
	
	@Test
	public void testASimpleGame() throws InvalidPlayException{
		final Game game = new Game();
		putStrongs(game);//bottom
		putStrongs(game);//top
		simpleAddPlay(game);//bottom
		simpleMovePlay(game);//bottom
		passTurn(game);//top
		passTurn(game);//top
		simpleAddPlay(game);//bottom
		simpleMovePlay(game);//bottom
		simpleAddPlay(game);//top
		passTurn(game);//top
		simpleAddPlay(game);//bottom
		Assert.assertTrue(game.isGameEnded());
		Assert.assertTrue(game.isBottomTheWinner());
		Assert.assertFalse(game.isTopTheWinner());
	}

	private void putStrongs(final Game game) throws InvalidPlayException {
		for(int i = 0; i < GameState.NUMBER_OF_STRONG_PIECES_TO_PUT;i++){
			Play play = new Play(i);
			final ValidPlay validPlay = game.validatePlay(play);
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}

	private void passTurn(final Game game) throws InvalidPlayException {
		Play play = new Play(Play.NEXT_STATE);
		final ValidPlay validPlay = game.validatePlay(play);
		printGame(game,validPlay);
		game.play(validPlay);
	}

	private void simpleMovePlay(final Game game) throws InvalidPlayException {
		for(int i = 1; i < GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE+1;i++){
			Play play = new Play(i,'u');
			final ValidPlay validPlay = game.validatePlay(play);
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}
	
	private void simpleAddPlay(final Game game) throws InvalidPlayException {
		for(int i = 0; i < GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;i++){
			Play play = new Play(Board.BOARD_SIZE-1);
			final ValidPlay validPlay = game.validatePlay(play);
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}

	private void printGame(final Game game, ValidPlay validPlay) {
		System.out.println(BoardUtils.printBoardWithCoordinates(game.getBoard()));
		System.out.println("Play: "+validPlay);
		System.out.println(game.getStateDescription());
	}
	
	@Test
	public void testLockGame() throws InvalidPlayException{
		final Game game = new Game();
		game.setLocked(true);
		Play play = new Play(0);
		boolean exceptionCatch = false;
		try{
			game.validatePlay(play);
		}catch(InvalidPlayException i){
			exceptionCatch = true;
		}
		Assert.assertTrue(exceptionCatch);
		ValidPlay forcedValidPlay = game.forceValidatePlay(play);
		game.play(forcedValidPlay);
		Assert.assertEquals(testLock, BoardUtils.printBoard(game.getBoard()));
		Play play2 = new Play(1);
		game.setLocked(false);
		ValidPlay validPlay2 = game.validatePlay(play2);
		game.play(validPlay2);
		Assert.assertEquals(testLock2, BoardUtils.printBoard(game.getBoard()));
	}
}
