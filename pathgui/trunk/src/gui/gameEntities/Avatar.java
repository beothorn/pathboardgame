package gui.gameEntities;

import gameEngine.GameElement;
import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;
import gui.GameLayoutDefinitions;

import java.awt.Graphics;

import playerTypes.PlayerTypes;

public class Avatar implements GameElement {

	private final Entity avatar;
	private boolean playing = false;
	private final boolean isTopPlayer;
	private int playerType = PlayerTypes.HUMAN;

	public Avatar(final Point position, final Point thoughtPosition, final boolean isTopPlayer) {
		this.isTopPlayer = isTopPlayer;
		final Point avatarPosition = position.copy();
		avatar = new Entity(currentAvatarSpritePlaying(),avatarPosition);
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

	@Override
	public void doStep(final long delta) {
		//Do nothing
	}

	@Override
	public void draw(final Graphics g) {
		avatar.draw(g);
	}

	public void gameTurnAdvanced(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn) {
		playing = isMyTurn(isTopPlayerTurn,isBottomPlayerTurn);
		if(playing) {
			avatar.setSprite(currentAvatarSpritePlaying());
			return;
		}
		avatar.setSprite(currentAvatarSpriteWaiting());
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
