package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.vikingdodge.Animator;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.actions.SaySomething;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

public abstract class Mob extends Entity implements Drawable, Updatable {

	/** If the mobs sprites can be drawn */
	protected boolean canDraw = true;

	/** If this mob can be deleted */
	protected boolean canDelete = false;

	/** If the mob is facing left */
	protected boolean isFacingLeft = false;

	/** If the mob is on the ground */
	protected boolean isGrounded = false;

	/** The animator for the mob */
	protected Animator animator = new Animator();

	/** The draw origin, changes depening on mob */
	protected Vector2 origin = new Vector2();

	/** The draw position */
	protected Vector2 drawOffset = new Vector2(0, 0);

	/** casts a shadow on the ground below the mob */
	protected Shadow shadow;

	// /** How often the mob should say something */

	/** The say something thing */
	protected SaySomething saySomething;
	/** A speech bubble will randomly appear above the mob */
	protected SpeechBubble speechBubble;

	/** Acceleration of the mob */
	protected float acceleration;

	/** The max speed the mob can move */
	protected float speed;

	public Mob(float speed, float acceleration, Shadow shadow,
			SaySomething saySomething, EntityHandler entityHandler,
			EventListener... eventListeners) {
		super(entityHandler, eventListeners);
		this.speed = speed;
		this.acceleration = acceleration;
		this.shadow = shadow;
		if (saySomething != null) {
			this.saySomething = saySomething;
			saySomething.setParent(this);
		}

		createPhysicsComponent();
		shadow.setParent(this);

	}

	@Override
	public void update(float delta) {
		if (saySomething != null)
			saySomething.update(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {
		/* Flip the animations depending on which way the mob is facing */
		if (isFacingLeft && !animator.isFlipX()) {
			animator.flipFrames(true, false);
		} else if (!isFacingLeft && animator.isFlipX()) {
			animator.flipFrames(true, false);
		}

		if (physics.getBody() != null)
			/* Check if the mob can be drawn before trying to draw it */
			if (canDraw) {
				shadow.setPosition(0);
				shadow.draw(batch);

				animator.setPosition(
						(getPhysics().getBody().getPosition().x - (animator
								.getWidth() / 2)) + drawOffset.x,
						(getPhysics().getBody().getPosition().y - (animator
								.getHeight() / 2)) + drawOffset.y);
				animator.setOrigin(origin.x, origin.y);
				animator.setRotation(getPhysics().getBody().getAngle()
						* MathUtils.radDeg);
				animator.draw(batch);
			}

	}

	public void saySomething(SpeechBubble speechBubble, boolean chanceBased,
			int chance) {
		if (saySomething != null)
			saySomething.saySomething(speechBubble, chanceBased, chance);
	}

	public void setSpeechBubble(SpeechBubble speechBubble) {
		if (this.speechBubble != null)
			return;
		this.speechBubble = speechBubble;
		entityHandler.getBubbles().add(speechBubble);
	}

	public void scaleDownSpeechBubbleNow() {
		if (this.speechBubble == null)
			return;
		speechBubble.setToScaleDownNow();
	}

	public void removeSpeechBubble() {
		this.speechBubble = null;
	}

	public Animator getAnimator() {
		return animator;
	}

	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}

	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}

	public boolean isGrounded() {
		return isGrounded;
	}

	public boolean isFacingLeft() {
		return isFacingLeft;
	}

	public void setFacingLeft(boolean isFacingLeft) {
		this.isFacingLeft = isFacingLeft;
	}

}
