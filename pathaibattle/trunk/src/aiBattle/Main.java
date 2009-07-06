package aiBattle;

import externalPlayer.AiControl;
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
		new AiControl(game, aiTop, stopPlayingOnGameEnd, stopPlayingOnGameEnd);
		new AiControl(game, aiBottom, false, stopPlayingOnGameEnd);
		System.out.println(game.getStateDescription());
	}
}
