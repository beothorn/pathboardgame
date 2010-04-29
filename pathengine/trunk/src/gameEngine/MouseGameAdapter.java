package gameEngine;

import gameEngine.gameMath.Point;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class MouseGameAdapter extends MouseInputAdapter {

	private final JGamePanel gamePanel;
	private Point previousPosition;

	public MouseGameAdapter(final JGamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		previousPosition = new Point(x, y);
		gamePanel.mousePressedAt(previousPosition);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY());
		gamePanel.notifyDrag(getDeltaPointForEvent(point));
		previousPosition = point;
	}

	private Point getDeltaPointForEvent(Point point) {
		return point.substract(previousPosition);
	}
}
