package gui.gameEntities;

import gameLogic.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RestartButton extends JButton{

	private final Game currentGame;

	public RestartButton(final Game game) {
		setText("Restart");
		currentGame = game;
		addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(final ActionEvent e) {
				currentGame.restartGame();
			}

		});
	}
}
