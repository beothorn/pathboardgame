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
	private boolean continuousGameLoop = false;
	private boolean alreadyOnLoop = false;
	private boolean actionAddedOnMidLoop = false;
	private static final boolean DEBUG = true;
	
	
	public JGamePanel() {
		setLayout(null);
	}

	public JGamePanel(final Color backgroundColor) {
		this();
		setBackground(backgroundColor);
	}

	public JGamePanel(final String background) {
		this();
		setBackground(background);
	}

	public void addGameElement(final GameElement element){
		element.addGameElementChangedListener(this);
		elements.add(element);
	}

	public boolean addStepAction(final EntityAction action){
		actionAdded();
		return nonUniqueStepActions.add(action);
	}
	
	public boolean addUniqueStepAction(final EntityAction action){
		actionAdded();
		uniqueStepActions.remove(action);
		return uniqueStepActions.add(action);
	}

	public void clearElements(){
		elements.clear();
	}


	private void doCollisions() {
		// TODO Will be implemented when needed
	}


	private void doStep(final long delta) {
		final ArrayList<GameElement> toRemove = new ArrayList<GameElement>();
		for (GameElement gameElement : elements){
			if(gameElement.markedToBeDestroyed()) {
				toRemove.add(gameElement);
			} else {
				gameElement.doStep(delta);
			}
		}
		for (final GameElement gameElement : toRemove) {
			elements.remove(gameElement);
		}
	}

	private void doActionsFor(final Collection<EntityAction> actions,final long delta) {
		final ArrayList<EntityAction> toRemove = new ArrayList<EntityAction>();
		for (final EntityAction entityAction : actions) {
			if(entityAction.actionEnded()) {
				toRemove.add(entityAction);
			} else {
				entityAction.doAction(delta);
			}
		}
		for (final EntityAction entityAction : toRemove) {
			actions.remove(entityAction);
		}		
	}
	
	private void doStepActions(final long delta) {
		doActionsFor(nonUniqueStepActions, delta);
		doActionsFor(uniqueStepActions, delta);
	}

	private void drawElements(final Graphics2D g) {
		drawer.drawElements(g,elements);
	}

	private void stepGame(final long delta) {
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
		doStepActions(delta);
		doCollisions();
		doStep(delta);
		repaint();
	}
	
	public void continuousGameLoop() {
		continuousGameLoop = true;
		gameLoop();
	}
	
	private void gameLoop(){
		if(alreadyOnLoop)
			return;
		alreadyOnLoop = true;
		new Thread(){
			@Override
			public void run() {
				super.run();
				long lastLoopTime = System.currentTimeMillis();
				boolean processAgain = false;
				while (continuousGameLoop || processAgain || actionAddedOnMidLoop) {
					actionAddedOnMidLoop = false;
					final long delta = System.currentTimeMillis() - lastLoopTime;
					lastLoopTime = System.currentTimeMillis();
					stepGame(delta);
					try { Thread.sleep(10); } catch (final Exception e) {}
					processAgain = false;
					if(actionsStillProcessing()){
						processAgain = true;
					}
				}
				alreadyOnLoop = false;
			}

			private boolean actionsStillProcessing() {
				for (EntityAction action : nonUniqueStepActions) {
					if(!action.actionEnded()) return true;
				}
				for (EntityAction action : uniqueStepActions) {
					if(!action.actionEnded()) return true;
				}
				return false;
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
	public String toString() {
		String toStr = "Elements count: "+elements.size();
		if(nonUniqueStepActions.size() > 0){
			toStr += "\nOn Step Actions:\n";
			for (final EntityAction ea : nonUniqueStepActions) {
				toStr += ea.toString();
			}
		}
		return toStr;
	}

	private void actionAdded() {
		changeTrigger();
	}

	private void changeTrigger() {
		actionAddedOnMidLoop = true;
		gameLoop();
	}

	@Override
	public void gameElementChanged() {
		changeTrigger();
	}
}
