package gameLogic;

import gameLogic.gameFlow.BoardListener;
import gameLogic.gameFlow.GameFlow;
import gameLogic.gameFlow.GameState;
import gameLogic.gameFlow.PlayResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Game {

	private final GameFlow gameFlow;
	private GameErrorListener error;
	private boolean locked = false;
	private List<PlayListener> playListeners;
	private CountDownLatch endedCountDownLatch;
	private CountDownLatch topCountDownLatch;
	private CountDownLatch bottomCountDownLatch;

	public Game() {
		gameFlow = new GameFlow();
		restartGame();
	}

	public Game(final Board board, final GameState gameState) {
		gameFlow = new GameFlow(board,gameState);
		createCountDownLatches();
	}

	public Game(final boolean isTopPlayer) {
		gameFlow = new GameFlow();
	}

	public void addBoardListener(final BoardListener b) {
		gameFlow.addBoardListener(b);
	}

	public void addPlayListener(final PlayListener pL) {
		if(playListeners == null) {
			playListeners = new ArrayList<PlayListener>();
		}
		playListeners.add(pL);
	}

	public void callBottomPlayedListeners(final Play play) {
		if(playListeners==null) {
			return;
		}
		for (final PlayListener pL : playListeners) {
			pL.bottomPlayed(play);
		}
	}

	public void callTopPlayedListeners(final Play play) {
		if(playListeners==null) {
			return;
		}
		for (final PlayListener pL : playListeners) {
			pL.topPlayed(play);
		}
	}

	public PlayResult forcePlay(final Play play){
		callPlayListeners(play);
		final PlayResult playResult = gameFlow.play(play);
		
		
		if (gameFlow.isTopPlayerTurn()){
			topCountDownLatch.countDown();
		}else{
			if(topCountDownLatch.getCount() == 0)
				topCountDownLatch = new CountDownLatch(1);
		}
		
		if (gameFlow.isBottomPlayerTurn()){
			bottomCountDownLatch.countDown();
		}else{
			if(bottomCountDownLatch.getCount() == 0)
				bottomCountDownLatch = new CountDownLatch(1);
		}
		
		if (gameFlow.isGameEnded()){
			endedCountDownLatch.countDown();
			topCountDownLatch.countDown();
			bottomCountDownLatch.countDown();
		}
		
		return playResult;
	}

	public Board getBoard() {
		return gameFlow.getBoard();
	}

	public GameState getCurrentState() {
		return gameFlow.getCurrentState();
	}

	public String getStateDescription() {
		return gameFlow.getStateDescription();
	}

	public boolean isBottomPlayerTurn() {
		return gameFlow.isBottomPlayerTurn();
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isPuttingStrongsTurn() {
		return gameFlow.isPuttingStrongsTurn();
	}

	public boolean isTopPlayerTurn() {
		return gameFlow.isTopPlayerTurn();
	}

	public PlayResult play(final Play play){

		if(isLocked()){
			PlayResult errorMustWaitForYourTurn = PlayResult.errorMustWaitForYourTurn();
			showErrorMessage(errorMustWaitForYourTurn);
			return errorMustWaitForYourTurn;
		}
		
		final PlayResult playResult = forcePlay(play);
		if(!playResult.isSuccessful()){
			showErrorMessage(playResult);
		}
		
		return playResult;
	}

	private void callPlayListeners(final Play play) {
		if(isTopPlayerTurn()){
			callTopPlayedListeners(play);
		}else{
			callBottomPlayedListeners(play);
		}
	}

	public void restartGame() {
		createCountDownLatches();
		gameFlow.restartGame();
	}

	private void createCountDownLatches() {
		endedCountDownLatch = new CountDownLatch(1);
		topCountDownLatch = new CountDownLatch(1);
		bottomCountDownLatch = new CountDownLatch(1);
	}

	public void setErrorMessageDisplayer(final GameErrorListener err) {
		error = err;
	}

	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

	private void showErrorMessage(final PlayResult msg) {
		if(error != null) {
			error.showMessage(msg.getErrorMessage());
		}
	}

	public void waitUntilEnded() {
		if (gameFlow.isGameEnded())
			return;
		try {
			endedCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void waitTopTurnOrGameEnd() {
		if (gameFlow.isTopPlayerTurn())
			return;
		try {
			topCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void waitBottomTurnOrGameEnd() {
		if (gameFlow.isBottomPlayerTurn())
			return;
		try {
			bottomCountDownLatch.await();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isGameEnded() {
		return gameFlow.isGameEnded();
	}
}
