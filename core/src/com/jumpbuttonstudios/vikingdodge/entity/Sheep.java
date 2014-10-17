package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.actions.BlinkAction;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SaySomething;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SheepSquashAction;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;

public class Sheep extends Mob {

	public enum SheepState {
		FALLING, IDLE, SQUASHED, BURNED, BLINK;
	}

	Vector2 pos;
	Vector2 signPos = new Vector2();

	SheepState currentState;
	SheepState nextState;

	/** The last time a sheep was spawned */
	double lastSpawn;

	/** The spawn frequency */
	double freq = TimeConversion.secondToNanos(1);

	SheepSquashAction sheepSquashAction;

	BlinkAction blinkAction = new BlinkAction(this);

	double burnAnimationPauseTime = TimeConversion.secondToNanos(1);
	double burnAnimationPauseTimeStart;

	public boolean signCreated = false;
	public double timeTouchedGround;
	public double signTimeout = TimeConversion.secondToNanos(3);

	public Sheep(Vector2 pos, EntityHandler entityHandler,
			EventListener... eventListeners) {
		super(0, 0, new Shadow(Assets.get(Assets.SHADOW)), new SaySomething(5,
				10, 20), entityHandler, eventListeners);
		this.pos = pos;

		sheepSquashAction = new SheepSquashAction(this);

		animator.addAnimation("falling",
				AnimationBuilder.create(Assets.SHEEP_ONLY, 1, 1, 1, null),
				PlayMode.LOOP);
		animator.addAnimation(
				"idle",
				AnimationBuilder.create(Assets.SHEEP_STAND, 0.125f, 4, 1, null),
				PlayMode.LOOP);
		animator.addAnimation("blink",
				AnimationBuilder.create(Assets.SHEEP_BLINK, 0.3f, 2, 1, null),
				PlayMode.NORMAL);
		animator.addAnimation("burn",
				AnimationBuilder.create(Assets.SHEEP_ASHES, 0.22f, 5, 1, null),
				PlayMode.NORMAL);

		animator.changeAnimation("falling");
		currentState = SheepState.FALLING;
		isGrounded = false;

		saySomething.addSpeechBubble("canFly",
				Assets.get(Assets.SPEECHBUBBLE_I_CAN_FLY), false);
		saySomething.addSpeechBubble("couldMove",
				Assets.get(Assets.SPEECHBUBBLE_I_COULD_MOVE), true);
		saySomething.addSpeechBubble("lambChops",
				Assets.get(Assets.SPEECHBUBBLE_LAMB_CHOPS), false);
		saySomething.addSpeechBubble("soLonely",
				Assets.get(Assets.SPEECHBUBBLE_SO_LONEY), true);
		saySomething.addSpeechBubble("fluffy",
				Assets.get(Assets.SPEECHBUBBLE_SOFT_N_FLUFFY), true);
		saySomething.addSpeechBubble("soLonely",
				Assets.get(Assets.SPEECHBUBBLE_TASTE_GREAT_STEW), true);
		saySomething.addSpeechBubble("weee",
				Assets.get(Assets.SPEECHBUBBLE_WEEE), false);
	}

	public void create() {
		physics.createBody(entityHandler.getFactory().getWorld(),
				BodyType.DynamicBody, pos, true);
		physics.createPolyFixture(animator.getWidth() / 2,
				animator.getHeight() / 2, 0.15f, 0.3f, 0.05f, false);
		physics.createCircleFixture(new Vector2(0.4f, 0), 0.1f, 0, 0, 0, 0,
				true);
		physics.getBody().setUserData(this);
		setCollisionFilters(
				CollisionFilters.SHEEP,
				(short) (CollisionFilters.GROUND
						| CollisionFilters.BOUNDARY_EDGE
						| CollisionFilters.PLAYER | CollisionFilters.BOUNDARY_EDGE));
	}

	@Override
	public void update(float delta) {
		if (currentState != SheepState.SQUASHED
				&& currentState != SheepState.BLINK
				&& currentState != SheepState.BURNED)
			super.update(delta);
		switch (currentState) {
		case FALLING:
			if (isGrounded) {
				animator.changeAnimation("idle");
				setCollisionFilters(
						CollisionFilters.SHEEP,
						(short) (CollisionFilters.GROUND
								| CollisionFilters.BOUNDARY_EDGE
								| CollisionFilters.PLAYER
								| CollisionFilters.ROCK | CollisionFilters.FIRE_ROCK));
				currentState = SheepState.IDLE;
			}
			origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
			break;
		case SQUASHED:
			if (sheepSquashAction.canDespawn) {
				canDelete = true;
				entityHandler.setSheepSpawned(false);
			} else {
				sheepSquashAction.execute();
			}
			origin.set(0, 0);
			break;
		case BLINK:
			blinkAction.execute();
			if (blinkAction.finished) {
				animator.next();
				currentState = nextState;
				blinkAction.finished = false;
			}
			break;
		case BURNED:
			if (animator.isFinished()) {
				if (TimeUtils.nanoTime() - burnAnimationPauseTimeStart > burnAnimationPauseTime) {
					if (animator.getColor().a - 1.5f * (1 / 60f) <= 0) {
						burnAnimationPauseTimeStart = 0;
						canDelete = true;
						entityHandler.setSheepSpawned(false);
					} else {
						animator.setAlpha(animator.getColor().a - 1.5f
								* (1f / 60f));
					}

				}
			}
			break;
		default:
			break;
		}

	}

	public void saySomething(SpeechBubble speechBubble, boolean chanceBased,
			int chance) {
		saySomething.saySomething(speechBubble, chanceBased, chance);
	}

	public SheepSquashAction getSheepSquashAction() {
		return sheepSquashAction;
	}

	public void setTimeOnGround() {
		timeTouchedGround = TimeUtils.nanoTime();
	}

	public boolean shouldAddSignThing() {
		return TimeUtils.nanoTime() - timeTouchedGround > signTimeout;
	}

	public Vector2 getSignPos() {
		return signPos.add(physics.getBody().getPosition());
	}

	public boolean isGrounded() {
		return isGrounded;
	}

	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}

	public SheepState getCurrentState() {
		return currentState;
	}

}
