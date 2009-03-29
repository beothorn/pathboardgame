package mouseFollow;

import java.awt.BorderLayout;

import javax.swing.JApplet;

public class MouseFollowApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(new MouseFollowPanel(),BorderLayout.CENTER);
	}

}
