package gui.gameEntities;

import gameEngine.GameElement;
import gameEngine.entityClasses.Entity;
import gameEngine.gameMath.Point;
import gui.GameLayoutDefinitions;

import java.awt.Graphics;

public class Avatar implements GameElement {

	private final Entity avatar;
	private boolean playing = false;
	private final boolean isTopPlayer;
	private final String playingSprite;
	private final String waitingSprite;

	public Avatar(final Point position, final boolean isAi, final boolean isTopPlayer) {
		if(isAi){
			playingSprite = GameLayoutDefinitions.avatarPCPlaying;
			waitingSprite = GameLayoutDefinitions.avatarPCWaiting;
		}else{
			playingSprite = GameLayoutDefinitions.avatarHumanPlaying;
			waitingSprite = GameLayoutDefinitions.avatarHumanPlaying;
		}

		this.isTopPlayer = isTopPlayer;
		final Point avatarPosition = position.copy();
		avatar = new Entity(currentAvatarSpritePlaying(),avatarPosition);
	}

	private String currentAvatarSpritePlaying() {
		return playingSprite;
	}

	private String currentAvatarSpriteWaiting() {
		return waitingSprite;
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
}
