package gameLogic.gameFlow;

import gameLogic.Board;
import gameLogic.Play;
import gameLogic.gameFlow.gameStates.GameFlowDefinitionsStateFactory;

public class GameFlow {

	private Board board;
	private final BoardListeners listeners = new BoardListeners();

	private GameState currentState;

	public GameFlow() {
		setBoard(new Board());
		restartGame();
	}

	public GameFlow(final Board board,final GameState gS) {
		setBoard(board);
		setCurrentState(gS);
	}

	public void addBoardListener(final BoardListener bCL){
		listeners.addBoardListener(bCL);
	}

	private void changeStateIfNeeded() {
		setCurrentState(currentState.nextStateIfChanged(getBoard(),listeners));
	}

	public Board getBoard() {
		return board;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public String getStateDescription(){
		return currentState.getStateDescription();
	}

	public boolean isBottomPlayerTurn(){
		return currentState.isBottomPlayerTurn();
	}

	public boolean isPuttingStrongsTurn() {
		return currentState.isPuttingStrongsTurn();
	}

	public boolean isTopPlayerTurn(){
		return currentState.isTopPlayerTurn();
	}

	public PlayResult play(final Play play){
		if(play.isNextState()) {
			return skipTurn();
		}
		final PlayResult playResult = currentState.play(play, board);
		listeners.callBoardChangedListeners();
		if(playResult.getSelectedPiece() != null) {
			listeners.callSelectedStrongListeners(playResult.getSelectedPiece());
		} 
		if(playResult.getMovedStrongPiece() != null) {
			listeners.callBoardMovedStrongListeners(playResult.getMovedStrongPiece());
		}
		changeStateIfNeeded();
		return playResult;
	}

	public void restartGame(){
		setCurrentState(GameFlowDefinitionsStateFactory.getFirstState());
		board.reset();
		if(listeners != null) {
			listeners.callBoardChangedListeners();
		}
	}

	public void setBoard(final Board board) {
		this.board = board;
	}

	public void setCurrentState(final GameState newCurrentState) {
		if(currentState == newCurrentState) {
			return;
		}
		currentState = newCurrentState;
		setBoard(board);
		listeners.callGameStateChangeListeners(newCurrentState);
	}

	public PlayResult skipTurn(){
		final GameState nextState = currentState.nextState(board, listeners);
		if(nextState == null) {
			return PlayResult.errorMustFinishPlay();
		}
		setCurrentState(nextState);
		return PlayResult.successfullPlay();
	}

	public boolean isGameEnded() {
		final boolean ended = currentState.asStateUniqueName().startsWith(GameState.GAME_ENDED);
		return ended;
			
	}
}
