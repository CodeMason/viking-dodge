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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;

public class FireBall extends Mob {
	
	public enum FireBallState{
		TRAVELING, EXPLODING;
	}
	
	FireBallState currentState;

	Vector2 direction = new Vector2();

	public FireBall(Dragon dragon, EntityHandler entityHandler) {
		super(15, 15, new Shadow(Assets.get(Assets.SHADOW)), null, entityHandler);

		animator.addAnimation("flame", AnimationBuilder.create(
				Assets.DRAGON_FIRE_ROCK, 0.06f, 5, 1, null), PlayMode.LOOP);
		animator.addAnimation("explode", AnimationBuilder.create(
				Assets.EFFECT_DRAGON_FIRE_HIT, 0.04f, 3, 3, new int[]{8}), PlayMode.NORMAL);
		animator.setScale(0.5f);
		
		origin.set(animator.getWidth() / 2, animator.getHeight() / 2);

		physics.createBody(entityHandler.getFactory().getWorld(),
				BodyType.DynamicBody, dragon.getFireBallAnchor(), false);
		physics.createCircleFixture(new Vector2(0, 0), animator.getWidth() / 8,
				0, 0.85f, 1, 0, false);
		physics.createCircleFixture(new Vector2(0, 0), animator.getWidth() / 8,
				0, 0, 0, 0, true);
		physics.getBody().setGravityScale(0);
		physics.getBody().setUserData(this);
		
		currentState = FireBallState.TRAVELING;

		setCollisionFilters(0,
				CollisionFilters.FIRE_ROCK,
				(short) (CollisionFilters.GROUND
						| CollisionFilters.BOUNDARY_EDGE));
		setCollisionFilters(1,
				CollisionFilters.FIRE_ROCK,
				(short) (CollisionFilters.PLAYER | CollisionFilters.SHEEP));

		direction.set(dragon.getFireBallAnchor().x
				- entityHandler.getPlayer().getPhysics().getBody()
						.getPosition().x, dragon.getFireBallAnchor().y
				- entityHandler.getPlayer().getPhysics().getBody()
						.getPosition().y);
		direction.nor();

	}

	@Override
	public void update(float delta) {
		switch (currentState) {
		case TRAVELING:
			if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <= speed) {
				physics.getBody().applyForceToCenter(-direction.x * acceleration,
						-direction.y * acceleration, true);
			}
			break;
		case EXPLODING:
			physics.getBody().setGravityScale(0);
			physics.getBody().setLinearVelocity(0, 0);
			if(animator.isFinished())
				canDelete = true;
			break;
		default:
			break;
		}

	}
	
	
}
