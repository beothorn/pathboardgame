package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.gameFlow.gameStates.GameState;
import gameLogic.gameFlow.gameStates.GameStateMovingStrongs;
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
		"--- BS1 --- --- --- --- --- ---";
	
	private final String testGravity = 
		"TS1 TS2 TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 BS3 BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---";
	
	private final String testGravityNoGravity = 
		"TS1 TS2 TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 --- BXX BWK --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---";
	
	private final String testGravityApplyGravity = 
		"TS1 TS2 TS3 --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"--- --- --- --- --- --- --- ---\n" +
		"BS1 BS2 --- BS3 --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK --- --- --- ---\n" +
		"--- --- --- BWK BWK --- --- ---";
	
	@Test
	public void testASimpleGame() throws InvalidPlayException, InvalidPlayStringException{
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
			final ValidPlay validPlay = game.validatePlay(play, game.isTopPlayerTurn());
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}

	private void passTurn(final Game game) throws InvalidPlayException, InvalidPlayStringException {
		Play play = new Play(Play.NEXT_STATE);
		final ValidPlay validPlay = game.validatePlay(play, game.isTopPlayerTurn());
		printGame(game,validPlay);
		game.play(validPlay);
	}

	private void simpleMovePlay(final Game game) throws InvalidPlayException {
		for(int i = 1; i < GameState.NUMBER_OF_STRONG_PIECES_TO_MOVE+1;i++){
			Play play = new Play(i,'u');
			final ValidPlay validPlay = game.validatePlay(play, game.isTopPlayerTurn());
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}
	
	private void simpleAddPlay(final Game game) throws InvalidPlayException {
		for(int i = 0; i < GameState.NUMBER_OF_WEAK_PIECES_TO_PUT;i++){
			Play play = new Play(Board.BOARD_SIZE-1);
			final ValidPlay validPlay = game.validatePlay(play, game.isTopPlayerTurn());
			printGame(game,validPlay);
			game.play(validPlay);
		}
	}

	private void printGame(final Game game, ValidPlay validPlay) {
		System.out.println(BoardUtils.printBoardWithCoordinates(game));
		System.out.println("Play: "+validPlay);
		System.out.println(game.getStateDescription());
	}
	
	@Test
	public void testLockGame() throws InvalidPlayException{
		final Game game = new Game();
		game.setBottomLocked(true);
		Play play = new Play(0);
		boolean exceptionCatch = false;
		try{
			game.validatePlay(play, game.isTopPlayerTurn());
		}catch(InvalidPlayException i){
			exceptionCatch = true;
		}
		Assert.assertTrue(exceptionCatch);
		Play play2 = new Play(1);
		game.setBottomLocked(false);
		ValidPlay validPlay2 = game.validatePlay(play2, game.isTopPlayerTurn());
		game.play(validPlay2);
		Assert.assertEquals(testLock, BoardUtils.printBoard(game.getBoard()));
	}
	
	@Test
	public void testGravityOff() throws InvalidPlayException{
		final Board board = BoardUtils.newBoardFromString(testGravity);
		final boolean isTopPlayerTurn = false;
		final GameStateMovingStrongs gameState = new GameStateMovingStrongs(isTopPlayerTurn);
		final Game game = new Game(board,gameState);
		game.setGravityAfterPlay(false);
		Play play = new Play(3,'r');
		final ValidPlay validPlay = game.validatePlay(play, game.isTopPlayerTurn());
		game.play(validPlay);
		Assert.assertEquals(testGravityNoGravity, BoardUtils.printBoard(game));
		game.applyGravity();
		Assert.assertEquals(testGravityApplyGravity, BoardUtils.printBoard(game.getBoard()));
	}
}
