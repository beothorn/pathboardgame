package aiBattle;


import java.io.IOException;

import main.Main;
import ai.AIPlayer;
import externalPlayer.AiControl;

public class MainPlayAsHuman extends Main{
	
	public MainPlayAsHuman(final String[] args) throws IOException {
		super(args);
	}

	@Override
	protected void addAIPlayers() {
		game.addTurnListener(new AiControl(new AIPlayer(), true, true));
	}
	
	public static void main(final String[] args) throws IOException {
		new MainPlayAsHuman(args);
	}
}
