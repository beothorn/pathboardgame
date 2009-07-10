package main;

import gui.GameLayoutDefinitions;
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
		Printer.debug("Starting game frame");
		Printer.debug("Frame size: "+getSize());
		Printer.debug("Main Game Canvas: "+mainGameCanvas.getSize());
	}
}
