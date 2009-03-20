package gui.gameEntities;

import gameEngine.GameElement;
import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;
import gui.GameLayoutDefinitions;

import java.awt.Graphics;

import playerTypes.PlayerTypes;

public class Avatar implements GameElement {

	private final Entity avatar;
	private static final long intervalToStartThinking = 15000;
	private boolean playing = false;
	private final boolean isTopPlayer;
	private final Entity thought;
	private long timeWaitingForPlay;
	private int playerType = PlayerTypes.HUMAN;

	public Avatar(final Point position, final Point thoughtPosition, final boolean isTopPlayer) {
		this.isTopPlayer = isTopPlayer;
		final Point avatarPosition = position.copy();
		avatar = new Entity(currentAvatarSpritePlaying(),avatarPosition);
		thought = new Entity(currentAvatarThoughtSprite(),thoughtPosition);
		thought.setVisible(false);
	}

	private String currentAvatarSpritePlaying() {
		if(PlayerTypes.isAiType(playerType)){
			return GameLayoutDefinitions.avatarPCPlaying;
		}
		if(playerType == PlayerTypes.HUMAN){
			return GameLayoutDefinitions.avatarHumanPlaying;
		}
		if(playerType == PlayerTypes.NETWORK){
			return GameLayoutDefinitions.avatarNetPlaying;
		}
		return null;
	}

	private String currentAvatarSpriteWaiting() {
		if(PlayerTypes.isAiType(playerType)){
			return GameLayoutDefinitions.avatarPCWaiting;
		}
		if(playerType == PlayerTypes.HUMAN){
			return GameLayoutDefinitions.avatarHumanWaiting;
		}
		if(playerType == PlayerTypes.NETWORK){
			return GameLayoutDefinitions.avatarNetWaiting;
		}
		return null;
	}

	private String currentAvatarThoughtSprite() {
		if(isTopPlayer){
			return GameLayoutDefinitions.avatarTopThinking;
		}
		return GameLayoutDefinitions.avatarBottomThinking;
	}

	@Override
	public void doStep(final long delta) {
		if(playing) {
			timeWaitingForPlay+= delta;
			if(timeWaitingForPlay > intervalToStartThinking) {
				thought.setVisible(true);
			}
		}
	}

	@Override
	public void draw(final Graphics g) {
		avatar.draw(g);
		thought.draw(g);
	}

	public void gameTurnAdvanced(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn) {
		playing = isMyTurn(isTopPlayerTurn,isBottomPlayerTurn);
		if(playing) {
			timeWaitingForPlay = 0;
			avatar.setSprite(currentAvatarSpritePlaying());
			return;
		}
		avatar.setSprite(currentAvatarSpriteWaiting());
		thought.setVisible(false);
	}

	private boolean isMyTurn(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn) {
		if(isTopPlayer && isTopPlayerTurn) {
			return true;
		}
		if(!isTopPlayer && !isTopPlayerTurn) {
			return true;
		}
		return false;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return false;
	}

	public boolean pointIsInsideEntity(final java.awt.Point point) {
		return avatar.getRectangle().contains(point);
	}

	public void setPlayerType(final int playerType) {
		this.playerType = playerType;
		if(playing){
			avatar.setSprite(currentAvatarSpritePlaying());
		}else{
			avatar.setSprite(currentAvatarSpriteWaiting());
		}
	}
}
