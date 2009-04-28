package gameLogic.gameFlow.gameStates;



public class GameStateFactory {

	public static GameState getFirstState(boolean topStarts) {
		final boolean isFirstState = true;
		return new GameStatePuttingStrongs(topStarts,isFirstState);
	}
}
