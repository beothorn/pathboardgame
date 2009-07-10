package gameLogic.gameFlow.gameStates;

public interface StateVisitor {
	public void onGameEnded(final GameStateGameEnded gameStateGameEnded);
	public void onMovingStrongs(final GameStateMovingStrongs gameStateMovingStrongs);
	public void onPuttingStrongs(final GameStatePuttingStrongs gameStatePuttingStrongs);
	public void onPuttingWeaks(final GameStatePuttingWeaks gameStatePuttingWeaks);
}
