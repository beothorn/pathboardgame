package gui.gameEntities;

import gui.gameEntities.piecesBoard.BoardPlayInputDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RestartButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestartButton(final BoardPlayInputDecoder eventsProcessor) {
		setText("Restart");
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				eventsProcessor.restartGame();
			}
		});
	}
}
