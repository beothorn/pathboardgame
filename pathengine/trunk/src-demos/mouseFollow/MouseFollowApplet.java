package mouseFollow;

import java.awt.BorderLayout;

import javax.swing.JApplet;

public class MouseFollowApplet extends JApplet {

	@Override
	public void init() {
		setLayout(new BorderLayout());
		add(new MouseFollowPanel(),BorderLayout.CENTER);
	}

}
