package gameEngine;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	final private Image image;
	
	public Sprite(final Image image) {
		this.image = image;
	}
	
	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}
	
	public void draw(Graphics g,int x,int y) {
		g.drawImage(image,x,y,null);
	}
	
	public void draw(Graphics g,int dx1,int dy1,int dx2,int dy2,int sx1,int sy1,int sx2,int sy2) {
		g.drawImage(image,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
	}
}