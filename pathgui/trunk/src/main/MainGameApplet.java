package main;

import gui.gameEntities.BoardGamePanel;

import java.awt.BorderLayout;

import javax.swing.JApplet;

public class MainGameApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		final boolean isTopAi = true;
		final boolean isBottomAi = false;
		final BoardGamePanel mainGamePanel = new BoardGamePanel(isTopAi,isBottomAi);
		add(mainGamePanel,BorderLayout.CENTER);
	}
}

