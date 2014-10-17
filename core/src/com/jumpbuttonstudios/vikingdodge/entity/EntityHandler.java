package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.box2d.Box2DFactory;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionResolver;
import com.jumpbuttonstudios.vikingdodge.effect.BurnEffect;
import com.jumpbuttonstudios.vikingdodge.effect.Effect;
import com.jumpbuttonstudios.vikingdodge.effect.RockDustEffect;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.FireBall.FireBallState;
import com.jumpbuttonstudios.vikingdodge.entity.Player.PlayerState;
import com.jumpbuttonstudios.vikingdodge.entity.Sheep.SheepState;
import com.jumpbuttonstudios.vikingdodge.entity.spawn.SpawnHandler;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;
import com.jumpbuttonstudios.vikingdodge.screens.GameScreen;
import com.jumpbuttonstudios.vikingdodge.ui.HeartUI;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.GameScreenLayout;
import com.jumpbuttonstudios.vikingdodge.ui.popup.GameOverPopup;

/**
 * Handles entity spawning and behavour, holds a Box2DFactory instance to clean
 * up Box2D Objects
 * 
 * @author Gibbo
 * 
 */
public class EntityHandler implements Updatable, EventListener {

	/** Game instnce */
	VikingDodge vikingDodge;

	/** Box2D factory for removing Box2D objects safely */
	private Box2DFactory factory = new Box2DFactory(0, -9.81f, true);

	/** All the entities present in the game */
	private Array<Mob> mobs = new Array<Mob>();

	/** Da sheep */
	public Mob sheep;

	public Mob thrownSheep;

	/** TODO put somewhere else */
	private Array<Effect> effects = new Array<Effect>(true, 30);

	/** TODO put somewhere else */
	private Array<SpeechBubble> bubbles = new Array<SpeechBubble>();

	/** If the sheep is currently spawned */
	private boolean sheepSpawned = false;

	/** If the dragon is spawned, only 1 at a time */
	private boolean dragonSpawned = false;

	/** Handles spawning of mobs */
	SpawnHandler spawnHandler;

	/** The player */
	private Player player;

	/**
	 * Creates a new EntityHandler and initialises a new player instance
	 */
	public EntityHandler(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;
		player = new Player(this, this, vikingDodge);
		spawnHandler = new SpawnHandler(vikingDodge, this);
		spawnHandler.setCanSpawn(false, true);
		/* Setup contact listener */
		factory.getWorld().setContactListener(
				new CollisionResolver(vikingDodge));

	}

	@Override
	public void update(float delta) {
		factory.update();
		player.update(delta);
		spawnHandler.update(delta);

		for (Mob mob : mobs) {
			if (mob.canDelete) {
				factory.deleteBody(mob.getPhysics().getBody());
				mobs.removeValue(mob, true);
				continue;
			}
			mob.update(delta);
		}

		for (Effect effect : effects) {
			if (effect.canDelete) {
				effects.removeValue(effect, true);
				continue;
			}
			effect.update(delta);
		}

		for (SpeechBubble bubble : bubbles) {
			bubble.update(delta);
			if (bubble.canRemove()) {
				bubble.getParent().removeSpeechBubble();
				bubbles.removeValue(bubble, true);
			}
		}

		if (sheep != null) {
			sheep.update(delta);
			if (sheep.canDelete) {
				factory.deleteBody(sheep.getPhysics().getBody());
				sheep = null;
			}
		}

		if (thrownSheep != null)
			thrownSheep.update(delta);

	}

	@Override
	public void onNotify(Entity entity, Event event) {
		if (VikingDodge.DEBUG)
			Log.info("EVENT", event);
		Rock rock;
		ThrownSheep thrownSheep;
		Sheep sheep;
		Dragon dragon;
		FireBall fb;
		/*
		 * 
		 * TODO CLEAN THIS SHIT UP
		 */
		switch (event) {
		case PLAYER_ON_TOP_OF_SOMETHING:
			player.onNotify(entity, event);
			break;
		case PLAYER_FELL:
			player.onNotify(entity, event);
			break;
		case SHEEP_ON_GROUND:
			sheep = (Sheep) this.sheep;
			if (sheep != null)
				if (sheep.currentState != SheepState.BLINK
						&& sheep.currentState != SheepState.BURNED) {
					sheep.setGrounded(true);
				}
			break;
		case PLAYER_MOVE_LEFT:
			player.onNotify(entity, event);
			break;
		case PLAYER_MOVE_RIGHT:
			player.onNotify(entity, event);
			break;
		case PLAYER_STOPPED:
			player.onNotify(entity, event);
			break;
		case PLAYER_JUMP:
			player.onNotify(entity, event);
			break;
		case GAMEOVER:
			GameOverPopup gop = ((GameScreenLayout) vikingDodge.getUi()
					.getCurrentLayout()).getGameOverPopup();
			spawnHandler.setCanSpawn(false, false);
			if (!vikingDodge.gameOver) {
				player.getPhysics().getBody().setActive(false);
				spawnHandler.despawnDragon();
				gop.moveIn(1, Interpolation.exp10);
				vikingDodge.gameOver = true;
			}
			break;
		case PLAYER_COLLIDED_WITH_SHEEP:
			sheep = (Sheep) entity;
			if (sheep.currentState != SheepState.SQUASHED
					&& sheep.currentState != SheepState.BURNED
					&& sheep.currentState != SheepState.BLINK) {
				sheep.canDelete = true;
				sheep.scaleDownSpeechBubbleNow();
				player.onNotify(entity, event);
			}
			break;
		case PLAYER_THROW:
			if (player.getSheepInteraction().hasSheep()) {
				setThrowSheep(new ThrownSheep(player, this));
				player.onNotify(entity, event);
			}
			break;
		case ROCK_COLLIDED_WITH_GROUND:
			rock = (Rock) entity;
			if (!rock.canFade)
				rock.startFadeTime = TimeUtils.nanoTime();
			rock.canFade = true;
			rock.hasHitGround = true;
			effects.add(new RockDustEffect(this, rock));
			GameScreen.cam.startShake(0.5f, 0.1f, 0.8f);
			vikingDodge.stats.bouldersDodges++;

			rock.setCollisionFilters(
					0,
					CollisionFilters.ROCK,
					(short) (CollisionFilters.PLAYER
							| CollisionFilters.SHEEP_THROWN
							| CollisionFilters.GROUND | CollisionFilters.ROCK | CollisionFilters.SHEEP));
			rock.setCollisionFilters(1, CollisionFilters.ROCK,
					(short) (CollisionFilters.PLAYER | CollisionFilters.GROUND));
			break;
		case ROCK_COLLIDED_WITH_SHEEP:
			if (entity instanceof Rock)
				entity.setCollisionFilters(
						0,
						CollisionFilters.ROCK,
						(short) (CollisionFilters.PLAYER
								| CollisionFilters.SHEEP_THROWN
								| CollisionFilters.GROUND | CollisionFilters.ROCK));
			else {
				sheep = (Sheep) entity;

				if (sheep.currentState != SheepState.SQUASHED
						&& sheep.currentState != SheepState.BURNED) {
					sheep.currentState = SheepState.SQUASHED;
					sheep.setCollisionFilters(CollisionFilters.SHEEP,
							CollisionFilters.GROUND);
					vikingDodge.stats.sheepKilled++;
				}
			}
			spawnHandler.setLastSheepSpawn();
			break;
		case SHEEP_COLLIDED_WITH_FIREBALL:
			sheep = (Sheep) entity;
			Log.info("COLLISION", "Fireball collided with sheep");
			if (sheep.getCurrentState() != SheepState.SQUASHED) {
				sheep.currentState = SheepState.BLINK;
				sheep.nextState = SheepState.BURNED;
				sheep.getAnimator().changeAnimation("blink");
				sheep.getAnimator().setNextAnimation("burn");
				sheep.setSpeechBubble(new SpeechBubble(Assets
						.get(Assets.SPEECHBUBBLE_LAMB_CHOPS), sheep, 2));
				vikingDodge.stats.sheepKilled++;
				spawnHandler.setLastSheepSpawn();
			}
			break;
		case PLAYER_COLLIDED_WITH_ROCK:
			rock = (Rock) entity;
			if (!rock.canDamageMob)
				break;
			if (player.getCurrentState() == PlayerState.BURN)
				break;
			if ((Math.abs(rock.getPhysics().getBody().getLinearVelocity().x) > 1f)
					&& rock.hasHitGround
					&& player.getPhysics().getBody().getPosition().y < rock
							.getPhysics().getBody().getPosition().y
					|| (rock.getPhysics().getBody().getLinearVelocity().x == 0 && !rock.hasHitGround)) {
				rock.canDamageMob = false;
				vikingDodge.stats.timesSqaushed++;
				damagePlayer();
				spawnHandler.setCanSpawn(false, true);
				if (player.getSheepInteraction().hasSheep()) {
					setSheepSpawned(false);
					spawnHandler.setLastSheepSpawn();
					vikingDodge.stats.sheepKilled++;
				}
				player.onNotify(entity, event);
			}
			break;
		case PLAYER_COLLIDED_WITH_FIREBALL:
			if (player.getCurrentState() != PlayerState.BURN)
				damagePlayer();
			if (player.getSheepInteraction().hasSheep())
				setSheepSpawned(false);
			player.onNotify(entity, event);
			vikingDodge.stats.deathByFireball++;
			break;
		case THROWN_SHEEP_COLLISION:
			thrownSheep = (ThrownSheep) entity;
			spawnHandler.setLastSheepSpawn();
			sheepSpawned = false;
			thrownSheep.canDelete = true;
			thrownSheep.scaleDownSpeechBubbleNow();
			break;
		case DRAGON_FIRE:
			dragon = (Dragon) entity;
			mobs.add(new FireBall(dragon, this));
			break;
		case FIREBALL_GROUND_COLLISION:
			fb = (FireBall) entity;
			effects.add(new BurnEffect(this, fb.getPhysics().getBody()
					.getPosition()));
			GameScreen.cam.startShake(1, 0.15f, 0.7f);
		case FIREBALL_COLLISION:
			fb = (FireBall) entity;
			fb.currentState = FireBallState.EXPLODING;
			fb.animator.changeAnimation("explode");
			fb.animator.setScale(1.75f);
			fb.setCollisionFilters(CollisionFilters.FIRE_ROCK, (short) 0x0000);
			break;
		case RESUMED:
			resume();
			break;
		case PAUSED:
			pause();
			break;

		default:
			break;
		}
	}

	/*
	 * Iterates over the hearts on the UI and removes one from the right hand
	 * side
	 */
	public void damagePlayer() {
		Array<HeartUI> hearts = ((GameScreenLayout) vikingDodge.getUi()
				.getCurrentLayout()).getHud().getHearts();
		for (int h = 2; h >= 0; h--) {
			HeartUI heart = hearts.get(h);
			if (heart.isFull()) {
				heart.hurt();
				break;
			}
		}
	}

	/* Pauses all the mobs animations */
	public void pause() {
		for (Mob mob : mobs) {
			mob.getAnimator().pause();
		}
	}

	/* Resumes all the mobs animations */
	public void resume() {
		for (Mob mob : mobs) {
			mob.getAnimator().resume();
		}

	}

	public Array<SpeechBubble> getBubbles() {
		return bubbles;
	}

	public SpawnHandler getSpawnHandler() {
		return spawnHandler;
	}

	public void setSheepSpawned(boolean sheepSpawned) {
		this.sheepSpawned = sheepSpawned;
	}

	public void setSheep(Mob sheep) {
		this.sheep = sheep;
	}

	public void setThrowSheep(Mob thrownSheep) {
		mobs.add(thrownSheep);
		this.thrownSheep = thrownSheep;
	}

	public boolean isSheepSpawned() {
		return sheepSpawned;
	}

	public boolean isDragonSpawned() {
		return dragonSpawned;
	}

	public void setDragonSpawned(boolean dragonSpawned) {
		this.dragonSpawned = dragonSpawned;
	}

	/** @return {@link #factory} */
	public Box2DFactory getFactory() {
		return factory;
	}

	/** @return {@link #entities} */
	public Array<Mob> getMobs() {
		return mobs;
	}

	public Array<Effect> getEffects() {
		return effects;
	}

	/** @return {@link #player} */
	public Player getPlayer() {
		return player;
	}

}
