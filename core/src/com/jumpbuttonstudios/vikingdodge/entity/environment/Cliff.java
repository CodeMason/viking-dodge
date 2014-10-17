package com.jumpbuttonstudios.vikingdodge.entity.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;

public class Cliff extends Entity implements Drawable {

	/** The order this cliff should be rendered */
	public int z;

	/** X Coordinate */
	float x;
	/** Y Coordinate */
	float y;

	/**
	 * 
	 * @param z
	 *            the order this cliff should be rendered
	 * @param texture
	 *            texture for the cliff
	 * @param entityHandler
	 * @param eventListeners
	 */
	public Cliff(Texture texture, float x, float y,
			EntityHandler entityHandler, EventListener... eventListeners) {
		super(entityHandler, eventListeners);
		sprite = new Sprite(texture);
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(x, y);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.draw(batch);
	}

}
