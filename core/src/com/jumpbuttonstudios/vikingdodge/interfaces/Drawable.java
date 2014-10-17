package com.jumpbuttonstudios.vikingdodge.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Any object that implements this interface can be drawn to the screen using
 * some sort of sprite/texture/font etc etc
 * 
 * @author Gibbo
 * 
 */
public interface Drawable {

	/**
	 * Draw this object
	 * 
	 * @param batch
	 */
	void draw(SpriteBatch batch);
}
