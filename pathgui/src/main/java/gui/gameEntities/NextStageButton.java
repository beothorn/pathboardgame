package gui.gameEntities;

import gameLogic.board.InvalidPlayStringException;
import gameLogic.board.Play;
import gui.gameEntities.piecesBoard.BoardPlayInputDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NextStageButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NextStageButton(final BoardPlayInputDecoder eventsProcessor) {

		setText("Next Stage");
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					eventsProcessor.tryToPlay(new Play(Play.NEXT_STATE));
				} catch (final InvalidPlayStringException shouldNotHappen) {
					throw new RuntimeException("Something very weird happened here....");
				}
			}
		});
	}
}
