package mouseFollow;

import gameEngine.JGamePanel;
import gameEngine.entityClasses.Entity;
import gameEngine.entityClasses.actions.MoveToAndStop;
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

	private final MoveToAndStop action;

	public MouseFollower(final JGamePanel gf) {
		final int pixPerSec = 500;
		final int snappingRadius = 2;
		final boolean killOnSnap = false;
		action = new MoveToAndStop(new Point(),pixPerSec,snappingRadius,killOnSnap, this);
		gf.addStepAction(action);
		gf.addMouseMotionListener(this);
		gf.addGameElement(this);
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
		action.setLocation(e.getX(), e.getY());
	}
}

public class MouseFollowPanel extends JGamePanel {
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

