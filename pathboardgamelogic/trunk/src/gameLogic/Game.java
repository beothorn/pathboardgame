package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.gameFlow.gameStates.GameState;
import gameLogic.gameFlow.gameStates.GameStateFactory;

import java.util.concurrent.CountDownLatch;

public class Game {

	private GameState gameState;
	private final Board board;
	private boolean locked = false;
	private boolean stateChanged = false;
	private CountDownLatch endedCountDownLatch;
	private CountDownLatch topCountDownLatch;
	private CountDownLatch bottomCountDownLatch;
	private final boolean topStarts = false;
	private boolean gravityAfterPlay = true;

	public Game() {
		createCountDownLatches();
		board = new Board();
		gameState = GameStateFactory.getFirstState(topStarts);
	}

	public void play(final ValidPlay validPlay){
		playAndGetNewState(validPlay);
		if (gameState.isTopPlayerTurn()){
			topCountDownLatch.countDown();
		}else{
			if(topCountDownLatch.getCount() == 0)
				topCountDownLatch = new CountDownLatch(1);
		}
		
		if (gameState.isBottomPlayerTurn()){
			bottomCountDownLatch.countDown();
		}else{
			if(bottomCountDownLatch.getCount() == 0)
				bottomCountDownLatch = new CountDownLatch(1);
		}
		
		if (gameState.isGameEnded()){
			endedCountDownLatch.countDown();
			topCountDownLatch.countDown();
			bottomCountDownLatch.countDown();
		}
		if(isGravityAfterPlay())
			board.applyGravity();
	}

	private void playAndGetNewState(final ValidPlay play) {
		GameState newGameState = gameState.play(play,board);
		if(newGameState == gameState){
			this.stateChanged = false;
		}else{
			this.stateChanged = true;
		}
		gameState = newGameState;
	}

	public Board getBoard() {
		return board.copy();
	}

	public String getStateDescription() {
		return gameState.getStateDescription();
	}

	public boolean isBottomPlayerTurn() {
		return gameState.isBottomPlayerTurn();
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isPuttingStrongsTurn() {
		return gameState.isPuttingStrongsTurn();
	}

	public boolean isTopPlayerTurn() {
		return gameState.isTopPlayerTurn();
	}
	
	public ValidPlay validatePlay(final Play play) throws InvalidPlayException{
		if(isLocked()){
			throw new InvalidPlayException(InvalidPlayException.MESSAGE_GAME_IS_LOCKED);
		}
		return gameState.validatePlay(play,board);
	}

	public ValidPlay forceValidatePlay(final Play play) throws InvalidPlayException{
		return gameState.validatePlay(play,board);
	}
	
	private void createCountDownLatches() {
		endedCountDownLatch = new CountDownLatch(1);
		topCountDownLatch = new CountDownLatch(1);
		bottomCountDownLatch = new CountDownLatch(1);
	}

	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

	public void waitUntilEnded() {
		if (gameState.isGameEnded())
			return;
		try {
			endedCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void waitTopTurnOrGameEnd() {
		if (gameState.isTopPlayerTurn())
			return;
		try {
			topCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void waitBottomTurnOrGameEnd() {
		if (gameState.isBottomPlayerTurn())
			return;
		try {
			bottomCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isGameEnded() {
		return gameState.isGameEnded();
	}
	
	public boolean isTopTheWinner() {
		return board.isTopTheWinner();
	}
	
	public boolean isBottomTheWinner() {
		return board.isBottomTheWinner();
	}

	public boolean isGameDraw() {
		return board.isGameDraw();
	}
	
	public boolean stateChanged() {
		return stateChanged;
	}

	public boolean isGravityAfterPlay() {
		return gravityAfterPlay;
	}

	public void setGravityAfterPlay(boolean gravityAfterPlay) {
		this.gravityAfterPlay = gravityAfterPlay;
	}
}
