package main;

import gui.GameLayoutDefinitions;
import gui.gameEntities.BoardGamePanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import utils.Logger;

public class MainGameFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BoardGamePanel mainGameCanvas;
	private final Logger logger = Logger.getLogger(MainGameFrame.class);

	public MainGameFrame() {
		setTitle(GameLayoutDefinitions.gameName);
		setLayout(new BorderLayout());
		mainGameCanvas = new BoardGamePanel();

		setResizable(false);
		setPreferredSize(GameLayoutDefinitions.screenSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainGameCanvas,BorderLayout.CENTER);
		pack();
	}

	public void setBottomPlayerType(final int type) {
		mainGameCanvas.setBottomPlayerType(type);
	}

	public void setTopPlayerType(final int type) {
		mainGameCanvas.setTopPlayerType(type);
	}

	@Override
	public void setVisible(final boolean b) {
		super.setVisible(b);
		logger.debug("Starting game frame");
		logger.debug("Frame size: "+getSize());
		logger.debug("Main Game Canvas: "+mainGameCanvas.getSize());
	}
}
