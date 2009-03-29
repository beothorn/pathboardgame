package gui.gameEntities;

import gameLogic.Game;
import gameLogic.Play;
import gameLogic.gameFlow.GameState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NextStageButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Game currentGame;

	public NextStageButton(final Game game) {
		setText("Next Stage");
		currentGame = game;
		addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(final ActionEvent e) {
				final Play play = new Play(Play.NEXT_STATE);
				getCurrentGame().play(play);
			}

		});
	}

	public void gameStateChanged(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn, final GameState gs) {
		setText("Next Stage for"+gs.asStateUniqueName());
		//TODO: mudar sprite
		//				if(isBottomPlayerTurn || isTopPlayerTurn){
		//					final GameState currentState = currentGame.getCurrentState();
		//					if(currentState == null){
		//						setNormal(null);
		//						setPressed(null);
		//						return;
		//					}
		//					setNormal(NextStageButtomSpriteFactory.getNormalSprite(currentState));
		//					setPressed(NextStageButtomSpriteFactory.getPresseSprite(currentState));
		//					return;
		//				}
		//				setNormal(GameLayoutDefinitions.restart);
		//				setPressed(GameLayoutDefinitions.restartPressed);
	}

	public Game getCurrentGame() {
		return currentGame;
	}
}
