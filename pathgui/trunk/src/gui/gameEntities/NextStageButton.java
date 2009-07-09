package gui.gameEntities;

import gameLogic.Game;
import gameLogic.board.InvalidPlayException;
import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gameLogic.gameFlow.gameStates.GameState;

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
				Play play = null;
				try {
					play = new Play(Play.NEXT_STATE);
				} catch (final InvalidPlayStringException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					getCurrentGame().validatePlay(play, true);
				} catch (final InvalidPlayException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	public void gameStateChanged(final boolean isTopPlayerTurn, final boolean isBottomPlayerTurn, final GameState gs) {
		//		setText("Next Stage for"+gs.asStateUniqueName());
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
