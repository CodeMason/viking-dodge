/**
 * Copyright 2014 Stephen Gibson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;

public class Dragon extends Mob implements EventListener {

	float defaultScale = 1.2f;

	public enum DragonState {
		MOVING, FIRING, POST_FIRE, DESPAWNING;
	}

	DragonState currentState;

	boolean canFire = true;
	double fireFreq = TimeConversion.secondToNanos(1);
	double lastFire = TimeUtils.nanoTime();

	boolean canDespawn = false;

	double spawnTime = TimeUtils.nanoTime();
	public double lifeTime;

	/**
	 * The point relative to the dragons sprite that the fireball will be
	 * created
	 */
	Vector2 fireBallAnchor = new Vector2();

	public Dragon(float x, EntityHandler entityHandler,
			EventListener... eventListeners) {
		super(4, 25, new Shadow(Assets.get(Assets.SHADOW)), null,
				entityHandler, eventListeners);

		lifeTime = TimeConversion.secondToNanos(MathUtils.random(20, 60));

		animator.addAnimation("fly",
				AnimationBuilder.create(Assets.DRAGON_FLY, 0.06f, 9, 1, null),
				PlayMode.LOOP);
		animator.addAnimation("fire",
				AnimationBuilder.create(Assets.DRAGON_FIRE, 0.1f, 4, 1, null),
				PlayMode.NORMAL);
		animator.addAnimation("postFire", AnimationBuilder.create(
				Assets.DRAGON_POST_FIRE, 0.1f, 5, 1, null), PlayMode.NORMAL);

		animator.changeAnimation("fly", defaultScale);
		currentState = DragonState.MOVING;

		origin.set(animator.getWidth() / 2, animator.getHeight() / 2);

		physics.createBody(entityHandler.getFactory().getWorld(),
				BodyType.DynamicBody, new Vector2(x, 9.5f), true);
		physics.createPolyFixture(animator.getWidth() / 3,
				animator.getHeight() / 3, 0.9f, 0.5f, 0.05f, true);
		physics.getBody().setGravityScale(0.5f);

	}

	@Override
	public void update(float delta) {

		// Have the dragon bob up and down a little to simulate flight
		if (physics.getBody().getPosition().y < 9.2f) {
			physics.getBody().applyForceToCenter(0, 100, true);
		}

		// Check if we can despawn the dragon
		if (TimeUtils.nanoTime() - spawnTime > lifeTime) {
			currentState = DragonState.DESPAWNING;
		} else if (canFire && TimeUtils.nanoTime() - lastFire > fireFreq) {
			if (entityHandler.getPlayer().getPhysics().getBody().getPosition().y < 7
					&& !entityHandler.getPlayer().isDead) {
				currentState = DragonState.FIRING;
				canFire = false;
				animator.changeAnimation("fire", defaultScale);
				animator.setNextAnimation("postFire", defaultScale);
			} else {
				lastFire = TimeUtils.nanoTime();
			}
		}

		switch (currentState) {
		case MOVING:
			if (Math.abs(physics.getBody().getLinearVelocity().x) <= speed) {
				physics.getBody().applyForceToCenter(
						isFacingLeft ? -acceleration : acceleration, 0, true);
			}

			if (physics.getBody().getPosition().x >= VikingDodge.WIDTH
					&& !isFacingLeft) {
				isFacingLeft = true;
				physics.getBody().setLinearVelocity(0,
						physics.getBody().getLinearVelocity().y);
			} else if (physics.getBody().getPosition().x <= 0 && isFacingLeft) {
				physics.getBody().setLinearVelocity(0,
						physics.getBody().getLinearVelocity().y);
				isFacingLeft = false;
			}

			break;
		case POST_FIRE:
			if (animator.isFinished()) {
				animator.next();
				currentState = DragonState.MOVING;
				canFire = true;
			}
			break;
		case FIRING:
			if (animator.isFinished()) {
				animator.next();
				currentState = DragonState.POST_FIRE;
				animator.setNextAnimation("fly", defaultScale);
				setFireBallAnchor();
				lastFire = TimeUtils.nanoTime();
				fireFreq = TimeConversion.secondToNanos(MathUtils.random(3f,
						10f));
				notify(this, Event.DRAGON_FIRE);
			}

			if (entityHandler.getPlayer().getPhysics().getBody().getPosition().x < physics
					.getBody().getPosition().x) {
				isFacingLeft = true;
			} else if (entityHandler.getPlayer().getPhysics().getBody()
					.getPosition().x > physics.getBody().getPosition().x) {
				isFacingLeft = false;
			}
			physics.getBody().setLinearVelocity(0,
					physics.getBody().getLinearVelocity().y);
			break;
		case DESPAWNING:
			if (currentState == DragonState.FIRING
					|| currentState == DragonState.POST_FIRE)
				animator.changeAnimation("fly", defaultScale);

			if (Math.abs(physics.getBody().getLinearVelocity().x) <= speed) {
				physics.getBody().applyForceToCenter(
						isFacingLeft ? -acceleration : acceleration, 0, true);
			}

			if (physics.getBody().getPosition().x < -2
					|| physics.getBody().getPosition().x > 18) {
				canDelete = true;
				animator.pause();
				physics.getBody().setActive(false);
				entityHandler.setDragonSpawned(false);
				entityHandler.spawnHandler.setLastDragonSpawn();
			}

			break;
		default:
			break;
		}

	}

	public void setFireBallAnchor() {
		fireBallAnchor.set(physics.getBody().getPosition().x
				+ (isFacingLeft ? -1.875f : 1.875f), physics.getBody()
				.getPosition().y + -1.225f);
	}

	public Vector2 getFireBallAnchor() {
		return fireBallAnchor;
	}

	@Override
	public void onNotify(Entity entity, Event event) {
		switch (event) {
		default:
			break;
		}
	}

}
