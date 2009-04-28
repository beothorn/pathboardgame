package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.gameFlow.gameStates.GameState;
import gameLogic.gameFlow.gameStates.GameStateFactory;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class Game {

	private GameState gameState;
	private final Board board;
	private final Set<Integer> alreadyMoved;
	private boolean locked = false;
	private boolean stateChanged = false;
	private CountDownLatch endedCountDownLatch;
	private CountDownLatch topCountDownLatch;
	private CountDownLatch bottomCountDownLatch;
	private boolean gravityAfterPlay = true;

	public Game() {
		this(new Board(), GameStateFactory.getFirstState(false));
	}
	
	public Game(final Board board,final GameState gameState){
		createCountDownLatches();
		this.board = board.copy();
		this.gameState = gameState;
		alreadyMoved = new LinkedHashSet<Integer>();		
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

	private void playAndGetNewState(final ValidPlay validPlay) {
		GameState newGameState = gameState.play(validPlay,board);
		if(newGameState == gameState){
			this.stateChanged = false;
			Play play = validPlay.unbox();
			if(play.isMoveDirection()){
				getAlreadyMovedPieces().add(play.getPieceId());
			}
		}else{
			this.stateChanged = true;
			getAlreadyMovedPieces().clear();
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

	public Set<Integer> getAlreadyMovedPieces() {
		return alreadyMoved;
	}

	public void applyGravity() {
		board.applyGravity();
	}
}
