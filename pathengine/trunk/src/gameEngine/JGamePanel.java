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


public class JGamePanel extends JPanel implements ImageObserver,GameElementChangedListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SINGLE_UPPER_LEFT = 1;
	private int backgroundLayout;
	private Sprite backgroundSprite;
	private Color color = Color.GRAY;
	private final GameDrawer drawer = new StaticFrameDrawer();
	private final List<GameElement> elements = new ArrayList<GameElement>();
	
	private final List<EntityAction> stepActions = new ArrayList<EntityAction>();
	
	private final GameLoop gameLoop;
	
	private static final boolean DEBUG = false;
	
	
	public JGamePanel() {
		setLayout(null);
		gameLoop = new GameLoop(this);
	}

	public JGamePanel(final Color backgroundColor) {
		this();
		setBackground(backgroundColor);
	}

	public JGamePanel(final String background) {
		this();
		setBackground(background);
	}

	public synchronized void addGameElement(final GameElement element){
		element.addGameElementChangedListener(this);
		elements.add(element);
	}

	public synchronized void addStepAction(final EntityAction action){
		stepActions.add(action);
		changeTrigger();
	}

	public synchronized void clearElements(){
		elements.clear();
	}

	private void doCollisions() {
		// TODO Will be implemented when needed
	}

	private synchronized void doStep(final long delta) {
		for (GameElement gameElement : elements){
			if(!gameElement.markedToBeDestroyed()) {
				gameElement.doStep(delta);
			}
		}
	}

	private synchronized void doStepActions(final long delta) {
		for(EntityAction entityAction : stepActions){			
			entityAction.doAction(delta);
		}	
	}

	private synchronized void drawElements(final Graphics2D g) {
		drawer.drawElements(g,elements);
	}

	public void stepGame(final long delta) {
		if(DEBUG){
			System.out.println("delta: "+delta);
			for (GameElement gameElement : elements) {
				System.out.println(gameElement);
			}
			System.out.println("StepActions:");
			for (final EntityAction entityAction : stepActions) {
				System.out.println("\t"+entityAction);
			}
		}
		doStepActions(delta);
		doCollisions();
		doStep(delta);
		cleanUp();
		repaint();
	}
	
	public synchronized boolean actionsStillProcessing() {
		for(EntityAction entityAction : stepActions){			
			if(!entityAction.actionEnded())
				return true;
		}
		return false;
	}

	public synchronized void cleanUp() {
		final ArrayList<GameElement> elementsToRemove = new ArrayList<GameElement>(elements);
		for (GameElement gameElement : elementsToRemove){
			if(gameElement.markedToBeDestroyed()) {
				elements.remove(gameElement);
			}
		}
		
		final ArrayList<EntityAction> stepActionsToRemove = new ArrayList<EntityAction>(stepActions);
		for(EntityAction entityAction : stepActionsToRemove){			
			if(entityAction.canBeDeleted())
				stepActions.remove(entityAction);
		}
	}

	private void gameLoop(){
		gameLoop.unPause();
	}

	public synchronized ArrayList<GameElement> getElements(){
		return new ArrayList<GameElement>(elements);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		render((Graphics2D) g);
	}

	public synchronized boolean removeStepAction(final EntityAction action){
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
	public synchronized String toString() {
		String toStr = "Elements count: "+elements.size();
		if(stepActions.size() > 0){
			toStr += "\nOn Step Actions:\n";
			for (final EntityAction ea : stepActions) {
				toStr += ea.toString();
			}
		}
		return toStr;
	}

	private void changeTrigger() {
		gameLoop();
	}

	@Override
	public void gameElementChanged() {
		changeTrigger();
	}
}
