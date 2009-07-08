package gui.externalPlayer;

import playerTypes.PlayerTypes;
import ai.AIPlayer;
import externalPlayer.AiControl;
import gameLogic.Game;

public class ExternalPlayerController{

	private AiControl externalBottom;
	private AiControl externalTop;

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

	private AiControl getCurrentAIEngineInstance(final boolean isTopPlayer) {
		return new AiControl(game,new AIPlayer(),isTopPlayer);
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
			return;
		}
		if(externalTop != null){
			externalTop.stopPlaying();
			externalTop = null;
		}
	}
}
