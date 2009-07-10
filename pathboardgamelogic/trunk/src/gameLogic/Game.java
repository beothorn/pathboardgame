package gameLogic;

import gameLogic.board.Board;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.Play;
import gameLogic.board.ValidPlay;
import gameLogic.board.piece.Piece;
import gameLogic.gameFlow.gameStates.GameState;
import gameLogic.gameFlow.gameStates.GameStateFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game {

	private GameState gameState;
	private final Board board;
	private final List<TurnChangeListener> turnListeners = new ArrayList<TurnChangeListener>();
	private boolean topLocked = false;
	private boolean bottomLocked = false;
	private boolean gravityAfterPlay = true;
	private boolean stateChanged;

	public Game() {
		this(new Board(), GameStateFactory.getFirstState());
	}
	
	public Game(final Board board,final GameState gameState){
		this.board = new Board();
		this.board.copyFrom(board);
		this.gameState = gameState;
	}
	
	public void addTurnListener(final TurnChangeListener turnChangeListener){
		turnListeners.add(turnChangeListener);
		if(gameState.isBottomPlayerTurn()){
			turnChangeListener.changedToBottomTurn();
		}else{
			if(gameState.isBottomPlayerTurn()){
				turnChangeListener.changedToTopTurn();
			}
		}
	}

	public void play(final ValidPlay validPlay){
		GameState newGameState = gameState.play(validPlay,board);
		if(newGameState == gameState){
			this.stateChanged = false;
		}else{
			this.stateChanged = true;
		}
		final GameState oldState = gameState;
		gameState = newGameState;
		sendTurnChangedToChangeListeners(oldState);
		if(isGravityAfterPlay())
			board.applyGravity();
	}

	private void sendTurnChangedToChangeListeners(final GameState oldState) {
		if(oldState.isTopPlayerTurn() && gameState.isBottomPlayerTurn()){
			for (TurnChangeListener listener : turnListeners) {
				listener.changedToBottomTurn();
			}
		}
		else if(oldState.isBottomPlayerTurn() && gameState.isTopPlayerTurn()){
			for (TurnChangeListener listener : turnListeners) {
				listener.changedToTopTurn();
			}
		}
	}

	public Board getBoard() {
		final Board boardCopy = new Board();
		boardCopy.copyFrom(board);
		return boardCopy;
	}

	public boolean isBottomPlayerTurn() {
		return gameState.isBottomPlayerTurn();
	}

	public boolean isPuttingStrongsTurn() {
		return gameState.isPuttingStrongsTurn();
	}

	public boolean isTopPlayerTurn() {
		return gameState.isTopPlayerTurn();
	}
	
	public ValidPlay validatePlay(final Play play,final boolean isTopPlayerPlay) throws InvalidPlayException{
		if(isTopPlayerPlay && isTopLocked()){
			throw InvalidPlayException.gameIsLocked();
		}
		if(!isTopPlayerPlay && isBottomLocked()){
			throw InvalidPlayException.gameIsLocked();
		}
		return gameState.validatePlay(play,board,isTopPlayerPlay);
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

	public Set<Piece> getAlreadyMovedPieces() {
		return gameState.getAlreadyMovedOrEmptySet();
	}

	public void applyGravity() {
		board.applyGravity();
	}
	
	public Game copy(){
		Game copy = new Game(getBoard(),gameState.copy());
		copy.gravityAfterPlay = gravityAfterPlay;
		return copy;
	}

	public void setTopLocked(boolean topLocked) {
		this.topLocked = topLocked;
	}

	public boolean isTopLocked() {
		return topLocked;
	}

	public void setBottomLocked(boolean bottomLocked) {
		this.bottomLocked = bottomLocked;
	}

	public boolean isBottomLocked() {
		return bottomLocked;
	}

	public GameState getCurrentState() {
		return gameState;
	}

	public void removeTurnListener(final TurnChangeListener turnChangeListener) {
		turnListeners.remove(turnChangeListener);
	}

	public void restartGame() {
		this.board.copyFrom(new Board());
		this.gameState = GameStateFactory.getFirstState();
	}
}
