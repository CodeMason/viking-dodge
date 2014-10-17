package com.jumpbuttonstudios.vikingdodge.entity.environment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;

public class Ground extends Entity implements Drawable {

	public Ground(EntityHandler entityHandler, EventListener... eventListeners) {
		super(entityHandler, eventListeners);

		/* Enable physics for this object */
		createPhysicsComponent();

		/* Setup the ground sprite */
		sprite = new Sprite(Assets.get(Assets.GROUND));
		sprite.setSize(sprite.getWidth() * VikingDodge.SCALE,
				sprite.getHeight() * VikingDodge.SCALE);
		

		/* Create fixture */
		getPhysics().createBody(entityHandler.getFactory().getWorld(),
				BodyType.StaticBody, new Vector2(VikingDodge.WIDTH / 2, -0.5f),
				true);
		getPhysics().createPolyFixture(sprite.getWidth() / 2,
				sprite.getHeight() / 2, 1, 0.75f, 0, false);
		getPhysics().getBody().setUserData(this);
		setCollisionFilters(CollisionFilters.GROUND, (short)(CollisionFilters.PLAYER
				| CollisionFilters.ROCK | CollisionFilters.SHEEP
				| CollisionFilters.SHEEP_THROWN | CollisionFilters.FIRE_ROCK));
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(
				getPhysics().getBody().getPosition().x
						- (sprite.getWidth() / 2), getPhysics().getBody()
						.getPosition().y - (sprite.getHeight() / 2) + 0.88f);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setRotation(getPhysics().getBody().getAngle() * MathUtils.radDeg);
		sprite.draw(batch);
		
	}

}
