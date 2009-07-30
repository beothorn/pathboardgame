package gameEngine.entityClasses;


import gameEngine.GameElement;
import gameEngine.GameElementChangedListener;
import gameEngine.Sprite;
import gameEngine.SpriteStore;
import gameEngine.gameMath.Point;
import gameEngine.gameMath.Vector;

import java.awt.Graphics;
import java.awt.Rectangle;


public class Entity implements GameElement {

	private final Point center;
	private final Vector direction;
	//	private Rectangle collisionRectangle = new Rectangle();
	private boolean isVisible = true;
	private boolean markedToBeDestroyed = false;
	private double maxSpeed;

	private final Point position;
	private double speedPixPerSec;
	private Sprite sprite;
	private GameElementChangedListener gameElementChangedListener;

	public Entity() {
		this(new Point());
	}

	public Entity(final double x,final double y) {
		this(new Point(x,y));
	}

	public Entity(final Point p) {
		this(p,new Point());
	}

	public Entity(final Point initialPosition,final Point centerPosition) {
		direction = new Vector(0,0);
		position = initialPosition;
		center = centerPosition;
	}

	public Entity(final String sprite,final double x,final double y) {
		this(sprite,new Point(x,y));
	}

	public Entity(final String sprite, final Point thoughtPosition) {
		this(thoughtPosition,new Point());
		this.sprite = SpriteStore.get().getSprite(sprite);
	}

	/**
	 * Check if this entity collided with another.
	 * 
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(final Entity other) {
		//		collisionRectangle.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		//		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());
		//
		//		return collisionRectangle.intersects(him);
		return false;
	}

	public void doMove(final long delta) {
		// update the location of the entity based on move speeds
		final Vector deslocation = direction.multiply(delta * speedPixPerSec/1000);
		position.setLocation(position.getX()+deslocation.x(),position.getY()+deslocation.y());
	}

	@Override
	public final void doStep(final long delta) {
		doMove(delta);
	}

	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g The graphics context on which to draw
	 */
	public void draw(final Graphics g) {
		if(isVisible() && sprite != null) {
			sprite.draw(g,(int) (getX() - getCenterX()),(int) (getY() - getCenterY()));
		}
	}

	public double getCenterX() {
		return center.getX();
	}

	public double getCenterY() {
		return center.getY();
	}

	public Vector getDirection(){
		return direction;
	}

	public Point getPosition(){
		return position.copy();
	}

	public Rectangle getRectangle() {
		return new Rectangle((int)getX(),(int)getY(),sprite.getWidth(),sprite.getHeight());
	}

	public double getSpeed(){
		return speedPixPerSec;
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public boolean isVisible() {
		return isVisible;
	}

	@Override
	public boolean markedToBeDestroyed() {
		return markedToBeDestroyed;
	}

	public void setCenterX(final double x) {
		center.setLocation(x, center.getY());
	}

	public void setCenterY(final double y) {
		center.setLocation(center.getX(),y);
	}

	public void setDirection(final double x, final double y){
		if(x!=0 || y!=0){
			direction.setVector(x, y);
			direction.normalize();
		}
	}

	public void markToBeDestroyed() {
		gameElementChanged();
		this.markedToBeDestroyed = true;
	}

	public void setPosition(final double x, final double y) {
		if(position.getX() != x || position.getY() != y){
			position.setLocation(x, y);
			gameElementChanged();
		}
	}

	public void setPosition(final Point p) {
		position.setLocation(p.getX(), p.getY());
	}

	public void setSpeed(final double pixPerSecond){
		if(maxSpeed == 0){
			speedPixPerSec = pixPerSecond;
			return;
		}

		if(Math.abs(pixPerSecond) <= maxSpeed){
			speedPixPerSec = pixPerSecond;
		}else{
			speedPixPerSec = pixPerSecond>=0?maxSpeed:-maxSpeed;
		}
	}

	public void setSprite(final String sprite){
		this.sprite = SpriteStore.get().getSprite(sprite);
		gameElementChanged();
	}

	private void gameElementChanged() {
		if(gameElementChangedListener!=null)
			gameElementChangedListener.gameElementChanged();
	}

	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other The entity with which this entity collided.
	 */
	//	public abstract void collidedWith(Entity other);

	public void setVisible(final boolean isVisible) {
		this.isVisible = isVisible;
		gameElementChanged();
	}

	public void setX(final double x) {
		position.setLocation(x, position.getY());
	}

	public void setY(final double y) {
		position.setLocation(position.getX(),y);
	}

	@Override
	public void addGameElementChangedListener(final GameElementChangedListener gameElementChangedListener) {
		this.gameElementChangedListener = gameElementChangedListener;
	}
}