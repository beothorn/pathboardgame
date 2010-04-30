package aiBattle;

import externalPlayer.AiControl;
import externalPlayer.PathAI;
import gameLogic.Game;
import utils.GameUtils;
import ai.AIPlayer;
import ai.permutations.NaiveCalculator;

public class Main{
	
	private final GameUtils gameUtils = new GameUtils();

	public static void main(final String[] args) throws InterruptedException {
		new Main();
	}

	public Main() throws InterruptedException {
		final Game game = new Game();		
		final PathAI aiTop = new AIPlayer();
		final PathAI aiBottom = new AIPlayer(new NaiveCalculator());		
		final boolean stopPlayingOnGameEnd = true;
		game.addTurnListener(new AiControl(aiTop, stopPlayingOnGameEnd, stopPlayingOnGameEnd));
		game.addTurnListener(new AiControl(aiBottom, false, stopPlayingOnGameEnd));
		gameUtils.printStateDescription(game);
	}
}
