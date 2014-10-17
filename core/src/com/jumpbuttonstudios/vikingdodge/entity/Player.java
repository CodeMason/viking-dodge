package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.entity.actions.BlinkAction;
import com.jumpbuttonstudios.vikingdodge.entity.actions.JumpAction;
import com.jumpbuttonstudios.vikingdodge.entity.actions.RollAction;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SaySomething;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SheepInteractionAction;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SquashAction;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.ui.HeartUI;

/**
 * The player controller by the user
 * 
 * @author Gibbo
 * 
 */
public class Player extends Mob implements EventListener {

	/** All states that the player can be in */
	public enum PlayerState {
		IDLE, RUNNING_LEFT, RUNNING_RIGHT, JUMPING, ROLLING, SQUASHED, BURN, BLINK, THROWING;

		Animation animation;

		private PlayerState() {
		}

		private PlayerState(Animation animation) {
			this.animation = animation;
		}

		public Animation getAnimation() {
			return animation;
		}
	}

	/** The players current state */
	PlayerState currentState = PlayerState.IDLE;
	PlayerState prevState = PlayerState.IDLE;
	public PlayerState nextState = PlayerState.IDLE;

	/** The players jump action */
	private JumpAction jumpAction = new JumpAction(this);
	/** Used to pick up sheeps */
	private SheepInteractionAction sheepInteraction = new SheepInteractionAction(
			this);
	private RollAction rollAction = new RollAction(this);
	/** Used to squash the player after being hit by a rock */
	private SquashAction squashAction = new SquashAction(this,
			TimeConversion.secondToNanos(2));
	/**
	 * Use to blink the character x amount of times upon being hit by the
	 * dragons fireball
	 */
	BlinkAction blinkAction = new BlinkAction(this);
	
	double burnAnimationPauseTime = TimeConversion.secondToNanos(1);
	double burnAnimationPauseTimeStart;

	double lastScoreIncrease = TimeUtils.nanoTime();
	double scoreIncreaseFreq = TimeConversion.secondToNanos(1);
	private int score = 1;

	boolean canCreateCollisionPlane = false;

	/* If the player is dead or not */
	boolean isDead = false;

	public Player(EntityHandler entityHandler, EventListener... eventListeners) {
		super(5, 8, new Shadow(Assets.get(Assets.SHADOW)), new SaySomething(5, 10, 20), entityHandler,
				eventListeners);

		drawOffset.y = 0.425f;

		animator.addAnimation(
				"idle",
				AnimationBuilder.create(Assets.PLAYER_STAND, 0.20f, 4, 1, null),
				PlayMode.LOOP);
		animator.addAnimation("walk",
				AnimationBuilder.create(Assets.PLAYER_RUN, 0.06f, 8, 1, null),
				PlayMode.LOOP);
		animator.addAnimation("jump",
				AnimationBuilder.create(Assets.PLAYER_JUMP, 0.25f, 2, 1, null),
				PlayMode.NORMAL);
		animator.addAnimation("roll",
				AnimationBuilder.create(Assets.PLAYER_LEAP, 0.1f, 5, 1, null),
				PlayMode.NORMAL);
		animator.addAnimation("idleSheep", AnimationBuilder.create(
				Assets.PLAYER_STAND_SHEEP, 0.2f, 4, 1, null), PlayMode.LOOP);
		animator.addAnimation("walkSheep", AnimationBuilder.create(
				Assets.PLAYER_RUN_SHEEP, 0.13f, 9, 1, null), PlayMode.LOOP);
		animator.addAnimation("throwSheep", AnimationBuilder.create(
				Assets.PLAYER_THROW_SHEEP, 0.15f, 4, 1, null), PlayMode.NORMAL);
		animator.addAnimation("blink",
				AnimationBuilder.create(Assets.PLAYER_BLINK, 0.4f, 2, 1, null),
				PlayMode.NORMAL);
		animator.addAnimation("burn",
				AnimationBuilder.create(Assets.PLAYER_BURN, 0.3f, 5, 2, null),
				PlayMode.NORMAL);

		animator.changeAnimation("jump", 1.08f, 1);
		currentState = PlayerState.JUMPING;
		animator.setNextAnimation("idle");
		
		saySomething.addSpeechBubble("manlyBeard", Assets.get(Assets.SPEECHBUBBLE_BEARD_MANLY), true);
		saySomething.addSpeechBubble("crushSomething", Assets.get(Assets.SPEECHBUBBLE_CRUSH_SOMETHING), true);
		saySomething.addSpeechBubble("dragon", Assets.get(Assets.SPEECHBUBBLE_DRAGON), false);
		saySomething.addSpeechBubble("dragonQ", Assets.get(Assets.SPEECHBUBBLE_DRAGON_QUESTION), false);
		saySomething.addSpeechBubble("grrr", Assets.get(Assets.SPEECHBUBBLE_GRRR), true);
		saySomething.addSpeechBubble("noFear", Assets.get(Assets.SPEECHBUBBLE_NO_FEAR), true);
		saySomething.addSpeechBubble("shit", Assets.get(Assets.SPEECHBUBBLE_SHIT), false);
		saySomething.addSpeechBubble("soShort", Assets.get(Assets.SPEECHBUBBLE_SO_SHORT), true);
		saySomething.addSpeechBubble("stupidSheep", Assets.get(Assets.SPEECHBUBBLE_STUPID_SHEEP), false);
		saySomething.addSpeechBubble("valhalla", Assets.get(Assets.SPEECHBUBBLE_VALHALLA), false);
		saySomething.addSpeechBubble("whereHammer", Assets.get(Assets.SPEECHBUBBLE_WHERE_HAMMMER), true);

		getPhysics().createBody(
				getEntityHandler().getFactory().getWorld(),
				BodyType.DynamicBody,
				new Vector2(VikingDodge.WIDTH / 2 - (animator.getWidth() / 2),
						15), true);
		getPhysics().createCircleFixture(new Vector2(0, 0), 0.4f, 0, 0.1f,
				0.5f, 0, false);
		getPhysics().createCircleFixture(new Vector2(0, .65f), 0.4f, 0, 0.15f,
				0.5f, 0, false);
		getPhysics().createPolyFixture(0.225f, 0.05f, new Vector2(0, -.4f), 0,
				0, 0, 0, true);
		physics.getBody().getFixtureList().get(2).setUserData(this);
		getPhysics().getBody().setUserData(this);
		physics.getBody().setSleepingAllowed(false);
		physics.getBody().setGravityScale(2);
		setCollisionFilters(CollisionFilters.PLAYER,
				(short) (CollisionFilters.BOUNDARY_EDGE
						| CollisionFilters.GROUND | CollisionFilters.ROCK
						| CollisionFilters.SHEEP | CollisionFilters.FIRE_ROCK));
		setCollisionFilters(2, (short) 1, (short) 1);

	}

	@Override
	public void update(float delta) {
		if(!isDead)
			super.update(delta);
		switch (currentState) {
		case RUNNING_LEFT:

			/*
			 * Apply acceleration to the player, the acceleration amount will be
			 * nerfed if the player is holding a sheep
			 */
			if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <= (sheepInteraction
					.hasSheep() ? speed * sheepInteraction.getSlowDownFactor()
					: speed)) {
				getPhysics().getBody().applyForceToCenter(
						jumpAction.isGrounded() ? -acceleration
								: -acceleration * 0.4f, 0, true);
			}

			/*
			 * If the player rolls, he tends to go over his max speed, here we
			 * quickly correct it
			 */
			if (Math.abs(getPhysics().getBody().getLinearVelocity().x) > speed) {
				physics.getBody().setLinearDamping(5f);
			}

			origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
			break;
		case RUNNING_RIGHT:

			/*
			 * Apply acceleration to the player, the acceleration amount will be
			 * nerfed if the player is holding a sheep
			 */
			if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <= (sheepInteraction
					.hasSheep() ? speed * sheepInteraction.getSlowDownFactor()
					: speed)) {
				getPhysics().getBody().applyForceToCenter(
						jumpAction.isGrounded() ? acceleration
								: acceleration * 0.4f, 0, true);
			}

			/*
			 * If the player rolls, he tends to go over his max speed, here we
			 * quickly correct it
			 */
			if (Math.abs(getPhysics().getBody().getLinearVelocity().x) > speed) {
				physics.getBody().setLinearDamping(5f);
			}

			origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
			break;
		case IDLE:

			break;
		case JUMPING:

			/*
			 * Apply acceleration to the player in the air, this is reduced from
			 * normal ground movement speed
			 */
			if (nextState == PlayerState.RUNNING_LEFT) {
				if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <= speed
						* sheepInteraction.getSlowDownFactor()) {
					getPhysics().getBody().applyForceToCenter(
							-acceleration * 0.4f, 0, true);
				}
			}
			if (nextState == PlayerState.RUNNING_RIGHT) {
				if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <= speed
						* sheepInteraction.getSlowDownFactor()) {
					getPhysics().getBody().applyForceToCenter(
							acceleration * 0.4f, 0, true);
				}
			}

			break;
		case SQUASHED:
			/*
			 * If the player is in a squashed state, as in he has been hit my a
			 * rock, we scale his sprite down to simulate a squash like effect
			 * and set a timer for him to respawn
			 */
			isDead = true;
			squashAction.execute();
			origin.set(0, 0);
			break;
		case THROWING:
			if (animator.isFinished()) {
				currentState = nextState;
				animator.next();
				((ThrownSheep) entityHandler.thrownSheep).create();
				sheepInteraction.setHasSheep(false);
				entityHandler.thrownSheep.canDraw = true;
			}
			break;
		case ROLLING:
			if (animator.isFinished()) {
				currentState = nextState;
				animator.next();
				physics.getBody().setLinearDamping(8);
				rollAction.revertToOldCollisionData();
			}
			break;
		case BLINK:
			blinkAction.execute();
			if (blinkAction.finished) {
				currentState = nextState;
				animator.next();
				blinkAction.finished = false;
				
			}
			break;
		case BURN:
			if (animator.isFinished()) {
				if (burnAnimationPauseTimeStart == 0)
					burnAnimationPauseTimeStart = TimeUtils.nanoTime();
				if (TimeUtils.nanoTime() - burnAnimationPauseTimeStart > burnAnimationPauseTime) {
					if (HeartUI.lifes != 0) {
						if (animator.getColor().a - 1.5f * (1 / 60f) <= 0) {
							burnAnimationPauseTimeStart = 0;
							respawn();
						} else {
							animator.setAlpha(animator.getColor().a - 1.5f
									* (1f / 60f));
						}
					} else {
						notify(null, Event.GAMEOVER);
					}

				}
			}
			break;
		default:
			break;
		}

		/* Give the player points */
		if (currentState != PlayerState.SQUASHED
				&& currentState != PlayerState.BURN)
			if (TimeUtils.nanoTime() - lastScoreIncrease > scoreIncreaseFreq) {
				score += sheepInteraction.hasSheep() ? 2 : 1;
				lastScoreIncrease = TimeUtils.nanoTime();
			}

		// /*
		// * If we are grounded but our animation is still jumping, we need to
		// * change to appropriate animation to match state
		// */
		// if (getJumpAction().isGrounded()
		// && getAnimator().getCurrentAnimation().equals(
		// animator.getAnimation("jump"))) {
		// if (currentState == PlayerState.IDLE) {
		// animator.changeAnimation("idle", 1);
		// } else if (currentState == PlayerState.RUNNING_LEFT
		// || currentState == PlayerState.RUNNING_RIGHT) {
		// animator.changeAnimation("walk", 1.17f);
		// } else if (currentState == PlayerState.JUMPING) {
		// animator.changeAnimation("idle", 1);
		// }
		// }
		//
		// /** Check if we are in a throw animation and if it is finished */
		// if (animator.getCurrentAnimation().equals(
		// animator.getAnimation("throwSheep"))) {
		// if (animator.isFinished()) {
		// currentState = prevState;
		// if (animator.getPreviousAnimation().equals(
		// animator.getAnimation("walk"))) {
		// animator.changeAnimation("walk", 1.17f);
		//
		// } else if (animator.getPreviousAnimation().equals(
		// animator.getAnimation("idleSheep"))) {
		// animator.changeAnimation("idle", 1);
		// } else if (animator.getPreviousAnimation().equals(
		// animator.getAnimation("walkSheep"))) {
		// animator.changeAnimation("walk", 1.17f);
		// }
		// ((ThrownSheep) entityHandler.thrownSheep).create();
		// entityHandler.thrownSheep.canDraw = true;
		// }
		// }
		//
		// if (animator.getCurrentAnimation()
		// .equals(animator.getAnimation("burn"))) {
		// if (animator.isFinished()) {
		// if (burnAnimationPauseTimeStart == 0)
		// burnAnimationPauseTimeStart = TimeUtils.nanoTime();
		// if (TimeUtils.nanoTime() - burnAnimationPauseTimeStart >
		// burnAnimationPauseTime) {
		// if (HeartUI.lifes != 0) {
		// if (animator.getColor().a - 1.5f * (1 / 60f) <= 0) {
		// burnAnimationPauseTimeStart = 0;
		// respawn();
		// } else {
		// animator.setAlpha(animator.getColor().a - 1.5f
		// * (1f / 60f));
		// }
		// } else {
		// notify(null, Event.GAMEOVER);
		// }
		//
		// }
		// }
		//
		// }
		//
		// if (animator.getCurrentAnimation()
		// .equals(animator.getAnimation("roll"))) {
		// if (animator.isFinished()) {
		// animator.next();
		// physics.getBody().setLinearDamping(8);
		// rollAction.revertToOldCollisionData();
		// }
		// }
		//
		/* Update the animator and keep the state time counting */
		animator.update(delta);
		//
		// /* Check state and move the character */
		// switch (currentState) {
		// case RUNNING_LEFT:
		// /* Check if the player is grounded and remove dampening */
		// if (jumpAction.isGrounded())
		// physics.getBody().setLinearDamping(0);
		//
		// /*
		// * Apply acceleration to the player, the acceleration amount will be
		// * nerfed if the player is holding a sheep
		// */
		// if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <=
		// (sheepInteraction
		// .hasSheep() ? speed * sheepInteraction.getSlowDownFactor()
		// : speed)) {
		// getPhysics().getBody().applyForceToCenter(
		// jumpAction.isGrounded() ? -acceleration
		// : -acceleration * 0.4f, 0, true);
		// }
		//
		// if (Math.abs(getPhysics().getBody().getLinearVelocity().x) > speed) {
		// physics.getBody().setLinearDamping(1f);
		// }
		//
		// origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
		// break;
		// case RUNNING_RIGHT:
		// /* Check if the player is grounded and remove dampening */
		// if (jumpAction.isGrounded())
		// physics.getBody().setLinearDamping(0);
		//
		// /*
		// * Apply acceleration to the player, the acceleration amount will be
		// * nerfed if the player is holding a sheep
		// */
		// if (Math.abs(getPhysics().getBody().getLinearVelocity().x) <=
		// (sheepInteraction
		// .hasSheep() ? speed * sheepInteraction.getSlowDownFactor()
		// : speed)) {
		// getPhysics().getBody().applyForceToCenter(
		// jumpAction.isGrounded() ? acceleration
		// : acceleration * 0.4f, 0, true);
		// }
		//
		// if (Math.abs(getPhysics().getBody().getLinearVelocity().x) > speed) {
		// physics.getBody().setLinearDamping(1f);
		// }
		//
		// origin.set(animator.getWidth() / 2, animator.getHeight() / 2);
		// break;
		// case SQUASHED:
		// /*
		// * If the player is in a squashed state, as in he has been hit my a
		// * rock, we scale his sprite down to simulate a squash like effect
		// * and set a timer for him to respawn
		// */
		// squashAction.execute();
		// origin.set(0, 0);
		// break;
		// case BURN:
		// blinkAction.execute();
		// break;
		// default:
		// break;
		// }

	}

	@Override
	public void onNotify(Entity entity, Event event) {
		System.out.println(event);
		switch (event) {
		case PLAYER_MOVE_LEFT:
			if (!isDead) {
				if (currentState == PlayerState.ROLLING
						|| currentState == PlayerState.JUMPING
						|| currentState == PlayerState.THROWING) {
					if (sheepInteraction.hasSheep()) {
						nextState = PlayerState.RUNNING_LEFT;
						animator.setNextAnimation("walkSheep", 1.05f, 1.147f);

					} else {
						nextState = PlayerState.RUNNING_LEFT;
						animator.setNextAnimation("walk", 1.17f);
					}
				} else {
					if (!sheepInteraction.hasSheep()) {
						if (rollAction.canExecute()) {
							if (rollAction.tapWithinDoubleTapTime()) {
								rollAction.execute();
								rollAction.setHeadToNotCollideWithRocks();
								nextState = PlayerState.RUNNING_LEFT;
								currentState = PlayerState.ROLLING;
								animator.changeAnimation("roll", 1.17f);
								animator.setNextAnimation("walk", 1.17f);
							} else {
								rollAction.setFirstTapTime();
								currentState = PlayerState.RUNNING_LEFT;
								animator.changeAnimation("walk", 1.17f);
							}
						} else {
							currentState = PlayerState.RUNNING_LEFT;
							animator.changeAnimation("walk", 1.17f);

						}
					} else {
						animator.changeAnimation("walkSheep", 1.05f, 1.147f);
						currentState = PlayerState.RUNNING_LEFT;
					}
				}
				isFacingLeft = true;
				physics.getBody().setLinearDamping(0);
			}
			break;
		case PLAYER_MOVE_RIGHT:
			if (!isDead) {
				if (currentState == PlayerState.ROLLING
						|| currentState == PlayerState.JUMPING
						|| currentState == PlayerState.THROWING) {
					if (sheepInteraction.hasSheep()) {
						nextState = PlayerState.RUNNING_RIGHT;
						animator.setNextAnimation("walkSheep", 1.05f, 1.147f);

					} else {
						nextState = PlayerState.RUNNING_RIGHT;
						animator.setNextAnimation("walk", 1.17f);
					}
				} else {
					if (!sheepInteraction.hasSheep()) {
						if (rollAction.canExecute()) {
							if (rollAction.tapWithinDoubleTapTime()) {
								rollAction.execute();
								rollAction.setHeadToNotCollideWithRocks();
								nextState = PlayerState.RUNNING_RIGHT;
								currentState = PlayerState.ROLLING;
								animator.changeAnimation("roll", 1.17f);
								animator.setNextAnimation("walk", 1.17f);
							} else {
								rollAction.setFirstTapTime();
								currentState = PlayerState.RUNNING_RIGHT;
								animator.changeAnimation("walk", 1.17f);
							}
						} else {
							currentState = PlayerState.RUNNING_RIGHT;
							animator.changeAnimation("walk", 1.17f);

						}
					} else {
						animator.changeAnimation("walkSheep", 1.05f, 1.147f);
						currentState = PlayerState.RUNNING_RIGHT;
					}
				}
				isFacingLeft = false;
				physics.getBody().setLinearDamping(0);
				break;
			}
		case PLAYER_STOPPED:
			if (!isDead) {
				if (currentState == PlayerState.ROLLING
						|| currentState == PlayerState.JUMPING
						|| currentState == PlayerState.THROWING) {
					if (sheepInteraction.hasSheep()) {
						nextState = PlayerState.IDLE;
						animator.setNextAnimation("idleSheep", 0.85f, 1);

					} else {
						nextState = PlayerState.IDLE;
						animator.setNextAnimation("idle", 1);
					}
				} else {
					if (sheepInteraction.hasSheep()) {
						animator.changeAnimation("idleSheep", 0.85f, 1f);
						currentState = PlayerState.IDLE;
					} else {
						animator.changeAnimation("idle", 1);
						currentState = PlayerState.IDLE;
					}
					physics.getBody().setLinearDamping(8);
				}
			}
			break;
		case PLAYER_JUMP:
			if (!isDead) {
				if (currentState != PlayerState.JUMPING
						&& currentState != PlayerState.ROLLING
						&& currentState != PlayerState.THROWING
						&& !sheepInteraction.hasSheep()) {
					nextState = currentState;
					currentState = PlayerState.JUMPING;
					jumpAction.execute();
					physics.getBody().setLinearDamping(0);
					animator.setNextAsCurrent(animator.getScaleX(),
							animator.getScaleY());
					animator.changeAnimation("jump", 1.08f, 1f);
				}
				if (!animator.getCurrentAnimation().equals(
						animator.getAnimation("throwSheep")))
					/*
					 * If the player is squashed, we don't want to process any
					 * input
					 */
					if ((currentState != PlayerState.SQUASHED)
							&& (currentState != PlayerState.BURN)) {
						if (!sheepInteraction.hasSheep()) {
							animator.changeAnimation("jump", 1.08f, 1f);
							physics.getBody().setLinearDamping(0);
							jumpAction.execute();
							prevState = currentState;
							currentState = PlayerState.JUMPING;
						}
					}
			}
			break;
		case PLAYER_ON_TOP_OF_SOMETHING:
			if (currentState != PlayerState.SQUASHED
					&& currentState != PlayerState.BURN
					&& currentState != PlayerState.BLINK) {
				jumpAction.setGrounded(true);
				animator.next();
				currentState = nextState;
			}
			break;
		case PLAYER_FELL:
			if (currentState != PlayerState.JUMPING
					&& currentState != PlayerState.BURN
					&& currentState != PlayerState.BLINK) {
				if (!isDead) {
					jumpAction.setGrounded(false);
					nextState = currentState;
					if(sheepInteraction.hasSheep()){
						currentState = PlayerState.JUMPING;
						animator.setNextAsCurrent(animator.getScaleX(),
								animator.getScaleY());
						animator.changeAnimation("idleSheep", 0.85f, 1f);
					}else{
						currentState = PlayerState.JUMPING;
						animator.setNextAsCurrent(animator.getScaleX(),
								animator.getScaleY());
						animator.changeAnimation("jump", 1.08f, 1f);
					}
				}
				physics.getBody().setLinearDamping(0);
			}
			break;
		case PLAYER_COLLIDED_WITH_SHEEP:
			if (!isDead) {
				if (currentState != PlayerState.SQUASHED) {
					if (currentState == PlayerState.IDLE
							|| currentState == PlayerState.JUMPING) {
						animator.changeAnimation("idleSheep", 0.85f, 1f);
						animator.setNextAnimation("idleSheep", 0.85f, 1f);
					} else if (currentState == PlayerState.RUNNING_LEFT
							|| currentState == PlayerState.RUNNING_RIGHT) {
						animator.changeAnimation("walkSheep", 1.05f, 1.147f);
						currentState = isFacingLeft ? PlayerState.RUNNING_LEFT
								: PlayerState.RUNNING_RIGHT;
					} else if (currentState == PlayerState.ROLLING) {
						animator.changeAnimation("walkSheep", 1.05f, 1.147f);
						currentState = isFacingLeft ? PlayerState.RUNNING_LEFT
								: PlayerState.RUNNING_RIGHT;
					}

					sheepInteraction.setHasSheep(true);

				}
			}
			break;
		case PLAYER_THROW:
			if (currentState != PlayerState.SQUASHED
					&& currentState != PlayerState.THROWING) {
				physics.getBody().setLinearDamping(1);

				if (currentState == PlayerState.IDLE)
					animator.setNextAnimation("idle", 1);
				else if (currentState == PlayerState.RUNNING_LEFT
						|| currentState == PlayerState.RUNNING_RIGHT)
					animator.setNextAnimation("walk", 1.17f);

				animator.changeAnimation("throwSheep", 1.55f, 1.15f);
				nextState = currentState;
				currentState = PlayerState.THROWING;
			}
			break;
		case PLAYER_COLLIDED_WITH_ROCK:
			Rock rock = (Rock) entity;
			if (!isDead) {
				removeSpeechBubble();
				currentState = PlayerState.SQUASHED;
				squashAction.setLastExecution();
				rollAction.setLastExecution();
				animator.pause();
				entityHandler.spawnHandler.setCanSpawn(false, false);
				if (jumpAction.isGrounded()) {
					getPhysics().getBody().setLinearDamping(50);
				} else {
					getPhysics().getBody().applyForceToCenter(0, -150, true);
					getPhysics().getBody().setLinearVelocity(0,
							getPhysics().getBody().getLinearVelocity().y);
				}
				setCollisionFilters(
						CollisionFilters.PLAYER,
						(short) (CollisionFilters.BOUNDARY_EDGE | CollisionFilters.GROUND));
				rock.setCollisionFilters(
						1,
						CollisionFilters.ROCK,
						(short) (CollisionFilters.PLAYER
								| CollisionFilters.SHEEP_THROWN
								| CollisionFilters.GROUND | CollisionFilters.ROCK));
				canCreateCollisionPlane = true;
				isDead = true;
			}
			break;
		case PLAYER_COLLIDED_WITH_FIREBALL:
			if (!isDead) {
				if (jumpAction.isGrounded()) {
					getPhysics().getBody().setLinearDamping(50);
				} else {
					getPhysics().getBody().applyForceToCenter(0, -150, true);
					getPhysics().getBody().setLinearVelocity(0,
							getPhysics().getBody().getLinearVelocity().y);
				}
				removeSpeechBubble();
				saySomething.saySomething("valhalla", false, 0);
				isDead = true;
				entityHandler.spawnHandler.setCanSpawn(false, false);
				sheepInteraction.setHasSheep(false);
				currentState = PlayerState.BLINK;
				nextState = PlayerState.BURN;
				animator.changeAnimation("blink", 1);
				animator.setNextAnimation("burn", 1.6f);
				origin.set(animator.getWidth() / 2,
						animator.getHeight() / 2 - 0.25f);
			}
			break;
		default:
			break;
		}

	}

	/** Respawns the player back at the top of the screen and in the centre */
	public void respawn() {
		physics.getBody().setTransform(
				VikingDodge.WIDTH / 2 - (animator.getWidth() / 2), 15,
				physics.getBody().getAngle());
		physics.getBody().setLinearDamping(0);
		physics.getBody().setAwake(true);
		physics.getBody().setActive(true);
		setCollisionFilters(CollisionFilters.PLAYER,
				(short) (CollisionFilters.BOUNDARY_EDGE
						| CollisionFilters.GROUND | CollisionFilters.ROCK
						| CollisionFilters.SHEEP | CollisionFilters.FIRE_ROCK));
		// if (physics.getBody().getFixtureList().size > 1) {
		// for (int x = 1; x < physics.getBody().getFixtureList().size; x++) {
		// entityHandler.getFactory().deleteFixture(
		// physics.getBody().getFixtureList().get(x));
		// }
		// }
		isDead = false;
		jumpAction.setGrounded(false);
		sheepInteraction.setHasSheep(false);
		squashAction.canSpawn = false;
		blinkAction.blinks = 0;
		currentState = PlayerState.JUMPING;
		nextState = PlayerState.IDLE;
		animator.setNextAnimation("idle", 1);
		animator.changeAnimation("jump", 1);
		animator.resume();
		entityHandler.spawnHandler.setCanSpawn(false, true);
		entityHandler.spawnHandler.setLastRockSpawn();
		lastScoreIncrease = TimeUtils.nanoTime();
	}

	public PlayerState getCurrentState() {
		return currentState;
	}

	public PlayerState getPrevState() {
		return prevState;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	/** @return {@link #sheepInteraction} */
	public SheepInteractionAction getSheepInteraction() {
		return sheepInteraction;
	}

	/** @return {@link #jumpAction} */
	public JumpAction getJumpAction() {
		return jumpAction;
	}

}
