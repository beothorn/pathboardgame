package gui.gameEntities;

import gameLogic.gameFlow.GameState;
import gui.GameLayoutDefinitions;

public class NextStageButtomSpriteFactory {

	public static String getNormalSprite(final GameState currentState){
		if(currentState.isTopPlayerTurn()){
			if(currentState.asStateUniqueName().equals(GameState.TOP_PLAYER_MOVING_STRONGS)){
				return GameLayoutDefinitions.buttomNextStageTopMovingStrongs;
			}
			if(currentState.asStateUniqueName().equals(GameState.TOP_PLAYER_PUTTING_WEAKS)){
				return GameLayoutDefinitions.buttomNextStageTopPuttingWeaks;
			}
			return null;
		}else{
			if(currentState.asStateUniqueName().equals(GameState.BOTTOM_PLAYER_MOVING_STRONGS)){
				return GameLayoutDefinitions.buttomNextStageBottomMovingStrongs;
			}
			if(currentState.asStateUniqueName().equals(GameState.BOTTOM_PLAYER_PUTTING_WEAKS)){
				return GameLayoutDefinitions.buttomNextStageBottomPuttingWeaks;
			}
		}
		return null;
	}

	public static String getPresseSprite(final GameState currentState) {
		if(currentState.isTopPlayerTurn()){
			if(currentState.asStateUniqueName().equals(GameState.TOP_PLAYER_MOVING_STRONGS)){
				return GameLayoutDefinitions.buttomNextStageTopMovingStrongsPressed;
			}
			if(currentState.asStateUniqueName().equals(GameState.TOP_PLAYER_PUTTING_WEAKS)){
				return GameLayoutDefinitions.buttomNextStageTopPuttingWeaksPressed;
			}
			return null;
		}else{
			if(currentState.asStateUniqueName().equals(GameState.BOTTOM_PLAYER_MOVING_STRONGS)){
				return GameLayoutDefinitions.buttomNextStageBottomMovingStrongsPressed;
			}
			if(currentState.asStateUniqueName().equals(GameState.BOTTOM_PLAYER_PUTTING_WEAKS)){
				return GameLayoutDefinitions.buttomNextStageBottomPuttingWeaksPressed;
			}
		}
		return null;
	}

}
