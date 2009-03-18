package gameEngine;


import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * A resource manager for sprites in the game. Its often quite important
 * how and where you get your game resources from. In most cases
 * it makes sense to have a central resource loader that goes away, gets
 * your resources and caches them for future use.
 * <p>
 * [singleton]
 * <p>
 * @author Kevin Glass
 */
public class SpriteStore {
	/** The single instance of this class */
	private static SpriteStore single = new SpriteStore();

	private static int tranparencyPolicy = Transparency.TRANSLUCENT;

	/**
	 * Get the single instance of this class
	 * 
	 * @return The single instance of this class
	 */
	public static SpriteStore get() {
		return single;
	}

	public static int getTranparencyPolicy() {
		return tranparencyPolicy;
	}

	public static void setTranparencyPolicy(final int tranparencyPolicy) {
		SpriteStore.tranparencyPolicy = tranparencyPolicy;
	}

	/** The cached sprite map, from reference to sprite instance */
	private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	/**
	 * Utility method to handle resource loading failure
	 * 
	 * @param message The message to display on failure
	 */
	private void fail(final String message) {
		// we're pretty dramatic here, if a resource isn't available
		// we dump the message and exit the game
		throw new RuntimeException(message);
	}

	/**
	 * Retrieve a sprite from the store
	 * 
	 * @param ref The reference to the image to use for the sprite
	 * @return A sprite instance containing an accelerate image of the request reference
	 */
	public Sprite getSprite(final String ref) {
		// if we've already got the sprite in the cache
		// then just return the existing version
		if (sprites.get(ref) != null) {
			return sprites.get(ref);
		}

		// otherwise, go away and grab the sprite from the resource
		// loader
		BufferedImage sourceImage = null;

		try {
			final URL url = this.getClass().getClassLoader().getResource(ref);

			if (url == null) {
				fail("Can't find ref: "+ref);
			}

			// use ImageIO to read the image in
			sourceImage = ImageIO.read(url);
		} catch (final IOException e) {
			fail("Failed to load: "+ref);
		}

		// create an accelerated image of the right size to store our sprite in
		final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		final Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),getTranparencyPolicy());

		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage,0,0,null);

		// create a sprite, add it the cache then return it
		final Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);

		return sprite;
	}
}