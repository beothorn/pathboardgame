package gameLogic.gameFlow.gameStates;

public class GameStateFactory {
	public static GameState getFirstState() {
		final boolean isFirstState = true;
		boolean isTopPlayerTurn = false;
		return new GameStatePuttingStrongs(isTopPlayerTurn,isFirstState);
	}
}
