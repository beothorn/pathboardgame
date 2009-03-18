package gui.externalPlayer;

import playerTypes.PlayerTypes;
import ai.AIPlayer;
import externalPlayer.ExternalPlayerRunnable;
import gameLogic.Game;

public class ExternalPlayerController{

	private ExternalPlayerRunnable externalBottom;
	private ExternalPlayerRunnable externalTop;

	private int bottomPlayerType;
	private final Game game;
	private int topPlayerType;

	public ExternalPlayerController(final Game game) {
		this.game = game;
		setBottomPlayerType(PlayerTypes.HUMAN);
		setTopPlayerType(PlayerTypes.HUMAN);
	}

	public int getBottomPlayerType(){
		return bottomPlayerType;
	}

	private ExternalPlayerRunnable getCurrentAIEngineInstance(final boolean isTopPlayer) {
		return new ExternalPlayerRunnable(game,new AIPlayer(),isTopPlayer);
	}

	public int getTopPlayerType(){
		return topPlayerType;
	}

	public void setBottomPlayerType(final int type){
		bottomPlayerType = type;
		if(PlayerTypes.isAiType(type)){
			if(externalBottom == null) {
				final boolean isTopPlayer = false;
				externalBottom = getCurrentAIEngineInstance(isTopPlayer);
			}
			startExternalPlayer(externalBottom);
			return;
		}
		if(externalBottom != null){
			externalBottom.stopPlaying();
			externalBottom = null;
		}
	}

	public void setTopPlayerType(final int type){
		topPlayerType = type;
		if(PlayerTypes.isAiType(type)){
			if(externalTop == null) {
				final boolean isTopPlayer = true;
				externalTop = getCurrentAIEngineInstance(isTopPlayer);
			}
			startExternalPlayer(externalTop);
			return;
		}
		if(externalTop != null){
			externalTop.stopPlaying();
			externalTop = null;
		}
	}

	private void startExternalPlayer(final ExternalPlayerRunnable externalPlayer) {
		new Thread(externalPlayer).start();
	}
}
