package gameEngine;

import java.awt.Graphics2D;
import java.util.List;

public interface GameDrawer {
	public void drawElements(Graphics2D g, List<GameElement> elements);
}
