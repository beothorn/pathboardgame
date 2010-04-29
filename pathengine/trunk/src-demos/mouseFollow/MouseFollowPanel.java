package mouseFollow;

import gameEngine.JGamePanel;
import gameEngine.entityClasses.Entity;
import gameEngine.entityClasses.onStepActions.MoveToAndStop;
import gameEngine.entityClasses.onStepActions.MutableOnStepAction;
import gameEngine.gameMath.Point;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

class MouseFollower extends Entity implements MouseMotionListener{

	private boolean isSquare = false;
	private final JGamePanel gamePanel;
	private final MutableOnStepAction mutableEntityAction;
	private static final int pixPerSec = 500;
	private static final int snappingRadius = 2;


	public MouseFollower(final JGamePanel gamePanel) {
		this.gamePanel = gamePanel;
		final Point destination = new Point();
		mutableEntityAction = new MutableOnStepAction(new MoveToAndStop(destination,pixPerSec,snappingRadius, this),gamePanel);
		gamePanel.addMouseMotionListener(this);
		gamePanel.addGameElement(this);
		this.gamePanel.addStepAction(mutableEntityAction);
	}

	private void moveTo(final Point destination) {
		mutableEntityAction.setOnStepAction(new MoveToAndStop(destination,pixPerSec,snappingRadius, this));
	}

	public void changeShape(){
		isSquare = !isSquare;
	}

	@Override
	public void draw(final Graphics g) {
		final Color color = g.getColor();
		g.setColor(Color.WHITE);
		final int radius = 10;
		if(isSquare){
			g.drawRect((int)getX(),(int)getY(), radius, radius);
		}else{
			g.drawOval((int)getX(),(int)getY(), radius, radius);
		}
		g.setColor(color);
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		moveTo(new Point(e.getX(), e.getY()));
	}
}

public class MouseFollowPanel extends JGamePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MouseFollowPanel() {
		super(Color.BLACK);
		setLayout(new BorderLayout());
		final MouseFollower mouseFollower = new MouseFollower(this);
		final JButton changeShapeButton = new JButton("Change Shape");
		changeShapeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent e) {
				mouseFollower.changeShape();
			}
		});
		add(changeShapeButton,BorderLayout.NORTH);
	}
}

