package gameEngine;

import gameEngine.entityClasses.actions.EntityAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class JGamePanel extends JPanel implements ImageObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SINGLE_UPPER_LEFT = 1;
	private int backgroundLayout;
	private Sprite backgroundSprite;
	private Color color = Color.GRAY;
	private final GameDrawer drawer = new StaticFrameDrawer();
	private List<GameElement> elements = new ArrayList<GameElement>();
	private boolean gameRunning = false;
	private List<EntityAction> stepActions;

	public JGamePanel() {
		runGameLoop();
	}
	
	public void runGameLoop(){
		gameRunning = true;
		gameLoop();
	}

	public JGamePanel(final Color backgroundColor) {
		this();
		setBackground(backgroundColor);
	}

	public JGamePanel(final String background) {
		this();
		setBackground(background);
	}

	public void addGameElement(final GameElement ent){
		elements.add(ent);
	}

	public boolean addStepAction(final EntityAction action){
		if(stepActions == null){
			stepActions = new ArrayList<EntityAction>();
		}
		return stepActions.add(action);
	}

	public void clearElements(){
		elements = new ArrayList<GameElement>();
	}


	private void doCollisions() {
		// TODO Will be implemented when needed
	}


	private void doStep(final long delta) {
		for (int i=0;i<elements.size();i++) {
			final GameElement ge = elements.get(i);
			if(ge.markedToBeDestroyed()) {
				elements.remove(ge);
			} else {
				ge.doStep(delta);
			}
		}
	}


	private void doStepActions(final long delta) {
		if(stepActions==null) {
			return;
		}
		for (int i=0;i<stepActions.size();i++) {
			final EntityAction sa = stepActions.get(i);
			if(sa.markedToBeDestroyed()) {
				stepActions.remove(sa);
			} else {
				sa.doAction(delta);
			}
		}
	}

	private void drawElements(final Graphics2D g) {
		drawer.drawElements(g,elements);
	}

	private void gameLoop() {
		new Thread(){
			@Override
			public void run() {
				super.run();
				long lastLoopTime = System.currentTimeMillis();
				while (gameRunning) {
					final long delta = System.currentTimeMillis() - lastLoopTime;
					lastLoopTime = System.currentTimeMillis();
					// cycle round drawing all the entities we have in the game
					doStepActions(delta);
					doCollisions();
					doStep(delta);
					repaint();
					try { Thread.sleep(30); } catch (final Exception e) {}
				}
			}

		}.start();
	}

	public ArrayList<GameElement> getElements(){
		return new ArrayList<GameElement>(elements);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		render((Graphics2D) g);
	}

	public boolean removeStepAction(final EntityAction action){
		if(stepActions == null){
			return false;
		}
		return stepActions.remove(action);
	}

	private void render(final Graphics2D g) {

		final Dimension size = getSize();
		final int width = size.width;
		final int height = size.height;

		if(backgroundSprite != null){
			if(backgroundLayout == SINGLE_UPPER_LEFT){
				backgroundSprite.draw(g, 0, 0);
			}else{
				backgroundSprite.draw(g, 0, 0);
			}
		}else{
			g.setColor(color);
			g.fillRect(0,0,width,height);
		};
		drawElements(g);
		paintComponents(g);
		g.dispose();
	}

	@Override
	public void setBackground(final Color color){
		this.color = color;
	}

	public void setBackground(final String image){
		backgroundSprite = SpriteStore.get().getSprite(image);
	}

	public void setBackground(final String image, final int layout){
		backgroundLayout = layout;
		backgroundSprite = SpriteStore.get().getSprite(image);
	}

	@Override
	public String toString() {
		String toStr = "Elements count: "+elements.size();
		if(stepActions != null){
			toStr += "\nOn Step Actions:\n";
			for (final EntityAction ea : stepActions) {
				toStr += ea.toString();
			}
		}
		return toStr;
	}

}
