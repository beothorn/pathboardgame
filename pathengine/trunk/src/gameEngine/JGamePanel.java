package gameEngine;

import gameEngine.entityClasses.actions.EntityAction;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
	
	private final List<EntityAction> nonUniqueStepActions = new ArrayList<EntityAction>();
	private final Set<EntityAction> uniqueStepActions = new LinkedHashSet<EntityAction>();
	
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
		nonUniqueStepActions.add(action);
		changeTrigger();
	}
	
	public synchronized void addUniqueStepAction(final EntityAction action){
		uniqueStepActions.remove(action);
		uniqueStepActions.add(action);
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
			if(gameElement.markedToBeDestroyed()) {
				elements.remove(gameElement);
			} else {
				gameElement.doStep(delta);
			}
		}
	}

	private synchronized void doActionsFor(final Collection<EntityAction> actions,final long delta) {
		for(EntityAction entityAction : actions ){			
			entityAction.doAction(delta);
		}	
	}
	
	private void doStepActions(final long delta) {
		doActionsFor(nonUniqueStepActions, delta);
		doActionsFor(uniqueStepActions, delta);
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
			System.out.println("uniqueStepActions:");
			for (final EntityAction entityAction : uniqueStepActions) {
				System.out.println("\t"+entityAction);
			}
			System.out.println("nonUniqueStepActions:");
			for (final EntityAction entityAction : nonUniqueStepActions) {
				System.out.println("\t"+entityAction);
			}
		}
		removeEndedActions();
		doStepActions(delta);
		doCollisions();
		doStep(delta);
		repaint();
	}
	
	public synchronized boolean actionsStillProcessing() {
		return (nonUniqueStepActions.size()>0) || (uniqueStepActions.size()>0);
	}

	private synchronized void removeEndedActions() {
		for(EntityAction entityAction : nonUniqueStepActions){			
			if(entityAction.actionEnded())
				nonUniqueStepActions.remove(entityAction);
		}
		
		//TODO: ALGO ERRADO AQUI, fonte do concurrent modification
//		for(EntityAction entityAction : uniqueStepActions){
//			if(entityAction.actionEnded())
//				uniqueStepActions.remove(entityAction);			
//		}
	}

	private void gameLoop(){
		gameLoop.loop();
	}

	public synchronized ArrayList<GameElement> getElements(){
		return new ArrayList<GameElement>(elements);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		render((Graphics2D) g);
	}

	public synchronized boolean removeStepAction(final EntityAction action){
		return nonUniqueStepActions.remove(action);
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
		if(nonUniqueStepActions.size() > 0){
			toStr += "\nOn Step Actions:\n";
			for (final EntityAction ea : nonUniqueStepActions) {
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
