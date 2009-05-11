package aiBattle;

import externalPlayer.ExternalPlayerRunnable;
import externalPlayer.PathAI;
import gameLogic.Game;
import ai.AIPlayer;
import ai.permutations.NaiveCalculator;

public class Main{

	public static void main(final String[] args) throws InterruptedException {
		new Main();
	}

	public Main() throws InterruptedException {
		final Game game = new Game();		
		final PathAI aiTop = new AIPlayer();
		final PathAI aiBottom = new AIPlayer(new NaiveCalculator());		
		final boolean stopPlayingOnGameEnd = true;
		final ExternalPlayerRunnable aiTopRunnable = new ExternalPlayerRunnable(game, aiTop, stopPlayingOnGameEnd, stopPlayingOnGameEnd);
		final ExternalPlayerRunnable aiBottomRunnable = new ExternalPlayerRunnable(game, aiBottom, false, stopPlayingOnGameEnd);
		new Thread(aiBottomRunnable).start();
		new Thread(aiTopRunnable).start();
		game.waitUntilEnded();
	}
}
