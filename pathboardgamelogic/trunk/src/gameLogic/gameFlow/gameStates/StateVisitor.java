package gameLogic.gameFlow.gameStates;

public interface StateVisitor {
	public void visit(final GameStateGameEnded gameStateGameEnded);
	public void visit(final GameStateMovingStrongs gameStateMovingStrongs);
	public void visit(final GameStatePuttingStrongs gameStatePuttingStrongs);
	public void visit(final GameStatePuttingWeaks gameStatePuttingWeaks);
}
