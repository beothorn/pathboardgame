package gameLogic.gameFlow.gameStates;



public class GameStateFactory {

	public static GameState getFirstState(boolean topStarts) {
		final boolean isFirstState = true;
		return new GameStatePuttingStrongs(topStarts,isFirstState);
	}

	public static GameState getFirstState() {
		final boolean isFirstState = true;
		boolean isTopPlayerTurn = false;
		return new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
	}
}
