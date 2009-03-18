package gameLogic.gameFlow.gameStates;

import gameLogic.gameFlow.GameState;


public class GameFlowDefinitionsStateFactory {

	private static boolean bottomStarts = true;

	private static GameState bottomStarts() {
		final boolean isTopPlayerTurn = false;
		final boolean isFirstState = true;
		final GameState startingState = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		return startingState;
	}

	public static GameState getFirstState(){
		if(bottomStarts){
			return bottomStarts();
		}
		return topStarts();
	}

	private static GameState topStarts() {
		final boolean isTopPlayerTurn = true;
		final boolean isFirstState = true;
		final GameState startingState = new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
		return startingState;
	}
}
