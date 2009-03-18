package mouseFollow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MouseFollowApplication extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		new MouseFollowApplication();
	}

	public MouseFollowApplication() {
		setLayout(new BorderLayout());
		add(new MouseFollowPanel(),BorderLayout.CENTER);
		setPreferredSize(new Dimension(400,400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}