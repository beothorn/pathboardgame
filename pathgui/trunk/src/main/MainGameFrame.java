package main;

import gui.GameDefinitions;
import gui.gameEntities.BoardGamePanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import utils.Printer;

public class MainGameFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BoardGamePanel mainGameCanvas;

	public MainGameFrame(final boolean isTopAi, final boolean isBottomAi) {
		final GameDefinitions gameDefinitions = new GameDefinitions();
		setTitle(gameDefinitions.getGameName());
		setLayout(new BorderLayout());
		mainGameCanvas = new BoardGamePanel(isTopAi,isBottomAi,gameDefinitions);

		setResizable(false);
		setPreferredSize(gameDefinitions.getScreenSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainGameCanvas,BorderLayout.CENTER);
		pack();
	}

	@Override
	public void setVisible(final boolean b) {
		super.setVisible(b);
		Printer.debug("Starting game frame");
		Printer.debug("Frame size: "+getSize());
		Printer.debug("Main Game Canvas: "+mainGameCanvas.getSize());
	}
}
