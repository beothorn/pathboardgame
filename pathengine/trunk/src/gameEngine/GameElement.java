package gameEngine;

import java.awt.Graphics;

public interface GameElement {

	public void doStep(long delta);
	public void draw(Graphics g);
	public boolean markedToBeDestroyed();
}
