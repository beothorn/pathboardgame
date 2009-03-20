package main;

import gui.gameEntities.BoardGamePanel;

import java.awt.BorderLayout;

import javax.swing.JApplet;

import playerTypes.PlayerTypes;

public class MainGameApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		final BoardGamePanel mainGamePanel = new BoardGamePanel();
		mainGamePanel.setTopPlayerType(PlayerTypes.AI);
		add(mainGamePanel,BorderLayout.CENTER);
	}
}

