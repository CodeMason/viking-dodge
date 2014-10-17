package com.jumpbuttonstudios.vikingdodge.entity.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;

/**
 * The border class holds simple collision bodies/fixtures to put an invisible
 * boundary wherever needed
 * 
 * @author Gibbo
 * 
 */
public class Border extends Entity {

	public Border(float x, float y, EntityHandler entityHandler,
			EventListener... eventListeners) {
		super(entityHandler, eventListeners);

		/* Enable physics for this object */
		createPhysicsComponent();

		getPhysics().createBody(entityHandler.getFactory().getWorld(),
				BodyType.StaticBody, new Vector2(x, y), true);
		getPhysics().createPolyFixture(0.1f, VikingDodge.HEIGHT / 2, 1, 0, 0,
				false);
		setCollisionFilters(
				CollisionFilters.BOUNDARY_EDGE,
				(short) (CollisionFilters.PLAYER | CollisionFilters.SHEEP | CollisionFilters.SHEEP_THROWN | CollisionFilters.FIRE_ROCK));
	}

	@Override
	public void update(float delta) {

	}

}
