package gui.gameEntities;

import gameEngine.GameElement;
import gameEngine.JGamePanel;
import gameEngine.entityClasses.Entity;
import gameEngine.entityClasses.actions.MoveToAndStop;
import gameEngine.gameMath.Point;
import gui.gameEntities.piecesBoard.ErrorListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import utils.Printer;

public class ErrorMessage extends Entity implements GameElement, ErrorListener{

	private final static int BOX_MARGIN = 10;
	private final static int SHOW_MESSAGE_MILISECONDS = 5000;
	private final static int SNAP_RADIUS = 5;

	private final static int SPEED = 250;
	private  Point endingPoint;

	private final JGamePanel gamePanel;
	private final MoveToAndStop goingToPoint;

	private String message = "";

	private Point startingPoint = new Point(-100,-100);
	private int timeShown = 0;

	public ErrorMessage(final JGamePanel gF) {
		super(0, 0);
		gamePanel = gF;
		message = "404";
		//		calculateStartAndEndingPositions(message);
		goingToPoint = new MoveToAndStop(new Point(), SPEED, SNAP_RADIUS, this);
		gF.addStepAction(goingToPoint);
		setVisible(false);
	}

	@Override
	protected void afterStep(final long delta){
		if(goingToPoint.isSnapped()){
			timeShown += delta;
		}
		if(timeShown >= SHOW_MESSAGE_MILISECONDS){
			timeShown = 0;
			moveToStartingPoint();
		}
	}

	private void calculateStartAndEndingPositions(final String message) {
		final Dimension size = gamePanel.getSize();
		final Graphics g = gamePanel.getGraphics();
		final int stringWidth = g.getFontMetrics().stringWidth(message);
		final int height = g.getFontMetrics().getHeight();
		final int boxWidth = stringWidth + getErrorBoxMargin()*2;
		final int boxgHeight = height + getErrorBoxMargin()*2;

		final double x = size.getWidth()/2 - boxWidth/2;
		startingPoint = new Point( x , -(boxgHeight + getErrorBoxMargin() * 2));
		this.setPosition(startingPoint);
		endingPoint = new Point(x, 10);
	}

	@Override
	public void draw(final Graphics g) {
		if(isVisible()){
			g.setColor(Color.YELLOW);
			final int x = (int)getX();
			final int y = (int)getY();
			final int stringWidth = g.getFontMetrics().stringWidth(message) + getErrorBoxMargin()*2;
			final int stringHeight = g.getFontMetrics().getHeight() + getErrorBoxMargin()*2;
			g.fillRect(x, y, stringWidth, stringHeight);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, stringWidth, stringHeight);
			g.drawString(message, x+ getErrorBoxMargin(), y + g.getFontMetrics().getHeight() + getErrorBoxMargin());
		}
	}

	public int getErrorBoxMargin() {
		return BOX_MARGIN;
	}

	private void moveToEndingPoint() {
		goingToPoint.setLocation(endingPoint);
	}

	private void moveToStartingPoint() {
		goingToPoint.setLocation(startingPoint);
	}

	@Override
	public void error(final String errorMessage){
		Printer.writeln(errorMessage);
		this.message = errorMessage;
		setVisible(true);
		calculateStartAndEndingPositions(errorMessage);
		moveToEndingPoint();

	}
}
