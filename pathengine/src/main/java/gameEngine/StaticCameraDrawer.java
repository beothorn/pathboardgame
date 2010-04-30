package gameEngine;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class StaticCameraDrawer implements GameDrawer {

	@Override
	public void drawElements(final Graphics2D g, final List<GameElement> elements) {
		final List<GameElement> clone = new ArrayList<GameElement>(elements);
		for (final GameElement gameElement : clone) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gameElement.draw(g);
		}
	}


}
