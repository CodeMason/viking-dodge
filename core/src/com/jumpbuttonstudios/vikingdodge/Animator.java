package com.jumpbuttonstudios.vikingdodge;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

public class Animator extends Sprite implements Updatable {

	/** All the animations this animator can use */
	HashMap<String, Animation> animations = new HashMap<String, Animation>();

	/** The previous animation, used to compare directions/angles */
	Animation previousAnimation;

	/** The scale of the previous animation */
	float previousScale;

	/** The current animation */
	Animation currentAnimation;

	/** The next animation to change to */
	Animation nextAnimation;
	/* The scale of the next animation */
	float nextScaleX, nextScaleY;
	

	/** The stateTime */
	float stateTime;

	/** If the animation is playing */
	boolean isPlaying = true;
	
	/** If the animation is paused */
	boolean isPaused = false;

	@Override
	public void update(float delta) {
		if (isPlaying && !isPaused)
			setRegion(currentAnimation.getKeyFrame(stateTime += delta));
	}

	@Override
	public void draw(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}

	/**
	 * Flips all the frames in the animation on the X axis
	 */
	public void flipFramesX() {
		TextureRegion[] frames = currentAnimation.getKeyFrames();
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}

	}

	/**
	 * Adds a new animation to this animator, allows for switching of cached
	 * animations without having to recreate them
	 * 
	 * @param name
	 * @param animation
	 */
	public void addAnimation(String name, Animation animation, PlayMode playMode) {
		/* If no animation is currently set, set the one we just added */
		if (currentAnimation == null) {
			this.currentAnimation = animation;
			setRegion(currentAnimation.getKeyFrame(0));
		}
		/*
		 * Set up the region size or we get weird results, like tiny little
		 * fucking things
		 */
		setSize(currentAnimation.getKeyFrame(0).getRegionWidth(),
				currentAnimation.getKeyFrame(0).getRegionHeight());
		/* Scale to the animation to the right size */
		setSize(getWidth() * VikingDodge.SCALE, getHeight() * VikingDodge.SCALE);
		animation.setPlayMode(playMode);
		animations.put(name, animation);
	}

	/**
	 * Flips all the frames of the current animation
	 * 
	 * @param flipX
	 *            flipX axis
	 * @param flipY
	 *            flipY axis
	 */
	public void flipFrames(boolean flipX, boolean flipY) {
		for (float x = 0; x < currentAnimation.animationDuration; x += currentAnimation.frameDuration) {
			TextureRegion frame = currentAnimation.getKeyFrame(x);
			frame.flip(flipX, flipY);
		}
	}

	/**
	 * Changes the current animation
	 * 
	 * @param animation
	 *            the animation to set
	 */
	public void changeAnimation(String name) {
		if (this.animations.get(name) == null)
			throw new VikingDodgeRuntimeException(
					"Animation not found, has it been created?");
		if (this.animations.get(name).equals(currentAnimation))
			return;
		setAlpha(1);
		previousAnimation = currentAnimation;
		previousScale = getScaleX();
		currentAnimation = this.animations.get(name);
		setRegion(currentAnimation.getKeyFrame(0));
		stateTime = 0;
	}

	/**
	 * Changes the current animation and scales it, useful for animations not
	 * fitting int AABB's
	 * 
	 * @param name
	 * @param scale
	 */
	public void changeAnimation(String name, float scale) {
		changeAnimation(name);
		setScale(scale);
		resume();
	}

	/**
	 * Changes the current animation and scales it, useful for animations not
	 * fitting int AABB's
	 * 
	 * @param name
	 * @param scale
	 */
	public void changeAnimation(String name, float scaleX, float scaleY) {
		changeAnimation(name);
		setScale(scaleX, scaleY);
	}
	
	public void setNextAsCurrent(){
		nextAnimation = currentAnimation;
		this.nextScaleX = getScaleX();
		this.nextScaleY = getScaleY();
	}
	
	public void setNextAsCurrent(float scaleX, float scaleY){
		nextAnimation = currentAnimation;
		this.nextScaleX = scaleX;
		this.nextScaleY = scaleY;
	}

	/**
	 * Sets the next animation to play after a given animation
	 * 
	 * @param name
	 */
	public void setNextAnimation(String name) {
		nextAnimation = animations.get(name);
		nextScaleX = 1;
		nextScaleY = 1;
	}

	/**
	 * Sets the next animation to play after a given animation
	 * 
	 * @param name
	 */
	public void setNextAnimation(String name, float scaleX, float scaleY) {
		nextAnimation = animations.get(name);
		this.nextScaleX = scaleX;
		this.nextScaleY = scaleY;
	}

	/**
	 * Sets the next animation to play after a given animation
	 * 
	 * @param name
	 */
	public void setNextAnimation(String name, float scale) {
		nextAnimation = animations.get(name);
		this.nextScaleX = scale;
		this.nextScaleY = 0;
	}

	/**
	 * Changes to the next animation
	 */
	public void next() {
		this.previousAnimation = currentAnimation;
		this.currentAnimation = nextAnimation;
		if (nextScaleY == 0)
			setScale(nextScaleX);
		else
			setScale(nextScaleX, nextScaleY);
	}

	/**
	 * Reverts to the animation before this one was switched, this also puts the
	 * current one back into previous. Do not run in game loop, stack overflow
	 * imminent
	 */
	public void revertPrevious() {
		Animation tmp = currentAnimation;
		this.currentAnimation = previousAnimation;
		this.previousAnimation = tmp;
		setScale(previousScale);
	}

	/**
	 * @return a specific animation, does not change the animation, instead use
	 *         {@link #changeAnimation(String)}
	 */
	public Animation getAnimation(String name) {
		return animations.get(name);
	}
	
	public void setPreviousAnimation(String name) {
		this.previousAnimation = animations.get(name);
	}

	/** @return {@link #previousAnimation} */
	public Animation getPreviousAnimation() {
		return previousAnimation;
	}

	/** @return {@link #currentAnimation} */
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public boolean isFinished() {
		return currentAnimation.isAnimationFinished(stateTime);
	}

	public void resume() {
		isPlaying = true;
		isPaused = false;
	}

	public void pause() {
		isPlaying = false;
		isPaused = true;
	}
	
	public void play(){
		isPlaying = true;
		isPaused = false;
		stateTime = 0;
		setRegion(currentAnimation.getKeyFrame(0));
	}
	
	public void stop(){
		isPlaying = false;
		isPaused = false;
		stateTime = 0;
		setRegion(currentAnimation.getKeyFrame(0));
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public float getStateTime() {
		return stateTime;
	}

}
