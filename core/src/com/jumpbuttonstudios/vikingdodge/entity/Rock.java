package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;

public class Rock extends Mob {

	/**
	 * Used to check if the rock has hit the ground, this will stop the game
	 * from doing double events due to rock bouncing
	 */
	public boolean hasHitGround = false;

	public boolean canFade = false;

	/** If the rock can collide with the player and sheep */
	boolean canDamageMob = true;

	/** Fade time */
	double fadeTime = TimeConversion.secondToNanos(2);
	/** Time it was spawned */
	double startFadeTime;

	public Rock(Vector2 pos, EntityHandler entityHandler) {
		super(0, 0, new Shadow(Assets.get(Assets.SHADOW)), null, entityHandler);

		animator.addAnimation("idle",
				AnimationBuilder.create(Assets.ROCK, 60, 1, 1, null),
				PlayMode.LOOP);

		createPhysicsComponent();
		physics.createBody(entityHandler.getFactory().getWorld(),
				BodyType.DynamicBody, pos, false);
		physics.createCircleFixture(new Vector2(0, 0), animator.getWidth() / 2,
				MathUtils.random(0f, 360f) * MathUtils.degRad, 2.5f, 0.8f,
				0f, false);
		physics.createCircleFixture(new Vector2(0, -0.325f),
				animator.getWidth() / 2.5f, MathUtils.random(0f, 360f)
						* MathUtils.degRad, 0f, 0f, 0.1f, true);
		setCollisionFilters(CollisionFilters.ROCK,
				(short) (CollisionFilters.GROUND | CollisionFilters.ROCK));
		setCollisionFilters(1, CollisionFilters.ROCK,
				(short) (CollisionFilters.PLAYER | CollisionFilters.SHEEP));
		physics.getBody().setUserData(this);
		
		origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
	}

	@Override
	public void update(float delta) {
		if (canFade && (TimeUtils.nanoTime() - startFadeTime > fadeTime)) {
			if (fade()) {
				canDelete = true;
			}
		}
	}


	public boolean fade() {
		if (animator.getColor().a - 0.05f > 0) {
			animator.setColor(animator.getColor().r, animator.getColor().g,
					animator.getColor().b, animator.getColor().a - 0.05f);
		} else {
			return true;
		}
		return false;
	}
}
