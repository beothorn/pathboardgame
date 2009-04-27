package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.gameStates.GameStateFactory;

import java.util.concurrent.CountDownLatch;

public class Game {

	private GameState gameState;
	private Board board;
	private boolean locked = false;
	private CountDownLatch endedCountDownLatch;
	private CountDownLatch topCountDownLatch;
	private CountDownLatch bottomCountDownLatch;
	private final boolean topStarts = false;

	public Game() {
		restartGame();
	}

	public void play(final ValidPlay play){
		gameState = gameState.play(play,board);
		
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
	}

	public Board getBoard() {
		return board;
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

	public void restartGame() {
		createCountDownLatches();
		board = new Board();
		gameState = GameStateFactory.getFirstState(topStarts);
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
}
