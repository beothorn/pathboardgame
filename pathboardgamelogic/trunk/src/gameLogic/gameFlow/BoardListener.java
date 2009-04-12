package gameLogic.gameFlow;



public interface BoardListener {

	public void boardChanged();

	public void gameStateChanged(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn,final GameState gameState);
}
