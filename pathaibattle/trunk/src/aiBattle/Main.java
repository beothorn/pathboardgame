package aiBattle;

import externalPlayer.ExternalPlayerRunnable;
import externalPlayer.PathAI;
import gameLogic.Game;
import gameLogic.Piece;
import gameLogic.Play;
import gameLogic.PlayListener;
import gameLogic.gameFlow.BoardListener;
import gameLogic.gameFlow.GameState;
import ai.AIPlayer;
import ai.permutations.NaiveCalculator;

public class Main implements PlayListener, BoardListener {

	private final Game game;
	private final PathAI aiTop;
	private final PathAI aiBottom;
	

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(final String[] args) throws InterruptedException {
		new Main();
	}

	public Main() throws InterruptedException {
		game = new Game();
		game.addPlayListener(this);
		game.addBoardListener(this);
		
		aiTop = new AIPlayer();
		aiBottom = new AIPlayer(new NaiveCalculator());
		
		final boolean stopPlayingOnGameEnd = true;
		final ExternalPlayerRunnable aiTopRunnable = new ExternalPlayerRunnable(
				game, aiTop, stopPlayingOnGameEnd, stopPlayingOnGameEnd);
		final ExternalPlayerRunnable aiBottomRunnable = new ExternalPlayerRunnable(
				game, aiBottom, false, stopPlayingOnGameEnd);
		new Thread(aiBottomRunnable).start();
		new Thread(aiTopRunnable).start();
		game.waitUntilEnded();
	}

	@Override
	public void bottomPlayed(final Play play) {
		System.out.println(game.getBoard());
		System.out.println( aiBottom + ": " + play);
	}

	@Override
	public void topPlayed(final Play play) {
		System.out.println(game.getBoard());
		System.out.println(aiTop+ ": " + play);
	}

	@Override
	public void boardChanged() {
	}

	@Override
	public void gameStateChanged(final boolean isTopPlayerTurn,
			final boolean isBottomPlayerTurn, final GameState gameState) {
		System.out.println("State advanced: " + gameState.asStateUniqueName());
		if(gameState.asStateUniqueName().toLowerCase().contains("win")){
			System.out.println("Final result: "+gameState.asStateUniqueName());
			if (gameState.asStateUniqueName().toLowerCase().contains("bottom")){
				System.out.println(aiBottom+" wins");			
			}
			if (gameState.asStateUniqueName().toLowerCase().contains("top")){
				System.out.println(aiTop+" wins");
			}
		}
	}

	@Override
	public void movedStrong(final Piece movedPiece) {
	}

	@Override
	public void selectedStrong(final Piece selectedPiece) {
	}

	@Override
	public void unselectedStrong(final Piece unselectedPiece) {
	}

}
