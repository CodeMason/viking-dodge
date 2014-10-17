package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;

public class ThrownSheep extends Sheep {

	Player player;

	/** How hard the sheep will be thrown */
	float throwForce = 50;

	boolean canAddScore = true;

	public ThrownSheep(Player player, EntityHandler entityHandler) {
		super(player.getPhysics().getBody().getPosition(), entityHandler);
		this.player = player;
		canDraw = false;

	}

	@Override
	public void create() {
		if (player.isFacingLeft)
			setFacingLeft(true);

		physics.createBody(entityHandler.getFactory().getWorld(),
				BodyType.DynamicBody, new Vector2(player.getPhysics().getBody()
						.getPosition().x, player.getPhysics().getBody()
						.getPosition().y + 0.8f), false);
		physics.createCircleFixture(new Vector2(0, 0), animator.getWidth() / 2,
				0, 0.15f, 1.5f, 0.05f, false);
		physics.getBody().setUserData(this);
		physics.getBody().applyForceToCenter(
				player.isFacingLeft ? -throwForce : throwForce, 20, true);
		physics.getBody().applyAngularImpulse(0.05f, true);
		setCollisionFilters(CollisionFilters.SHEEP_THROWN,
				(short) (CollisionFilters.GROUND | CollisionFilters.ROCK));
		setSpeechBubble(new SpeechBubble(
				Assets.get(Assets.SPEECHBUBBLE_I_CAN_FLY), this, 2));
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (physics.getBody() != null)
			if (canAddScore)
				if (physics.getBody().getPosition().x
						- (animator.getWidth() / 2) > VikingDodge.WIDTH
						|| physics.getBody().getPosition().x
								+ (animator.getWidth() / 2) < 0) {
					entityHandler.getPlayer().setScore(
							entityHandler.getPlayer().getScore() + 5);
					canAddScore = false;
					entityHandler.vikingDodge.stats.sheepSaved++;
					if (speechBubble != null)
						speechBubble.setToScaleDownNow();
					canDelete = true;
					entityHandler.setSheepSpawned(false);
					entityHandler.thrownSheep = null;
				}
	}

}
