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

package com.jumpbuttonstudios.vikingdodge.ui.popup;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.SoundManager;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public abstract class Popup extends Table {

	/* Game instance */
	protected VikingDodge vikingDodge;

	/** The parent layout this popup is being added to */
	protected Layout parent;

	/** The time it takes in seconds to tween the popup in */
	protected float time = 1;

	/** The position off screen the popup will start at and return to */
	protected Vector2 offScreenPos = new Vector2();

	/** The position on sceen the popup will move to */
	protected Vector2 onScreenPos = new Vector2();

	/**
	 * If this popup should be removed from the stage once it has been moved
	 * out, default is true
	 */
	protected boolean removeFromStage = true;

	/** If this popup as moved in towards the {@link #onScreenPos} */
	protected boolean movedIn = false;

	/** If this popup as moved in towards the {@link #offScreenPos} */
	protected boolean movedOut = false;

	/**
	 * The default interpolation method that will be used to tween the popup in
	 * and out
	 */
	protected Interpolation defaultInterpolation = Interpolation.exp10;

	/** The background image for this popup */
	protected Image background;

	/** The dark overlay for the background */
	protected Image overlay;

	/** The fade time for the overlay in seconds */
	protected float overlayFadeTime = 0.25f;

	/**
	 * Creates a new popup. This will automatically call {@link #create()}, then
	 * {@link #addActors()} and finally {@link #registerListeners()}
	 * 
	 * @param vikingDodge
	 * @param parent
	 *            the layout this is being added to
	 * @param background
	 *            the background image
	 * @param time
	 *            total time in seconds to finished tween
	 * @param addOverlay
	 *            if the background overlay should be added
	 * @param visibleOverlay
	 *            if the overlay should be visible
	 */
	public Popup(VikingDodge vikingDodge, Layout parent, Image background,
			float time, boolean addOverlay, boolean visibleOverlay) {
		this.vikingDodge = vikingDodge;
		this.parent = parent;
		this.background = background;
		this.time = time;

		if (addOverlay)
			createOverlay(visibleOverlay);
		create();
		addActors();
		registerListeners();

	}

	/**
	 * 
	 * @param vikingDodge
	 * @param parent
	 *            the layout this is being added to
	 * @param background
	 *            the background image
	 * @param the
	 *            default interpolation method for tweening
	 * @param time
	 *            total time in seconds to finished tween
	 */
	public Popup(VikingDodge vikingDodge, Layout parent, Image background,
			float time, boolean addOverlay, boolean visibleOverlay,
			Interpolation defaultInterpolation) {
		this(vikingDodge, parent, background, time, addOverlay, visibleOverlay);
		this.defaultInterpolation = defaultInterpolation;

	}

	/** Initialises the popup, creates the actors, this is called automatically */
	public abstract void create();

	/** Adds all actors to the stage, this is called automatically */
	public abstract void addActors();

	/** Adds listeners to all the actors, this is called automatically */
	public abstract void registerListeners();

	public void setScreenPositions(float offX, float offY, float onX, float onY) {
		offScreenPos.set(offX, offY);
		onScreenPos.set(onX, onY);
		setPosition(offScreenPos.x, offScreenPos.y);

	}

	/**
	 * Moves the popup towards the {@link #onScreenPos}
	 * 
	 * @param time
	 *            in seconds to finish tween
	 */
	public void moveIn(float time) {
		movedOut = false;
		addAction(Actions.sequence(Actions.moveTo(onScreenPos.x, onScreenPos.y,
				time, defaultInterpolation), Actions.run(new Runnable() {

			@Override
			public void run() {
				movedIn = true;
			}
		})));
	}

	/**
	 * Moves the popup towards the {@link #onScreenPos}
	 * 
	 * @param time
	 *            in seconds to finish tween
	 * @param interpolation
	 *            the interpolation to use for the tween
	 */
	public void moveIn(float time, Interpolation interpolation) {
		movedOut = false;
		addAction(Actions.sequence(Actions.moveTo(onScreenPos.x, onScreenPos.y,
				time, interpolation), Actions.run(new Runnable() {

			@Override
			public void run() {
				movedIn = true;
			}
		})));
	}

	/**
	 * Moves the pop towards the {@link #offScreenPos}
	 * 
	 * @param time
	 *            time in seconds to finish tween
	 * @param removeFromStage
	 *            if the popup should be removed from the stage when it has
	 *            finished the tween
	 */
	public void moveOut(float time, boolean removeFromStage) {
		movedIn = false;
		if (removeFromStage)
			addAction(Actions.sequence(Actions.moveTo(offScreenPos.x,
					offScreenPos.y, time, defaultInterpolation), Actions
					.run(new Runnable() {

						@Override
						public void run() {
							movedOut = true;

						}
					}), Actions.removeActor()));
		else
			addAction(Actions.sequence(Actions.moveTo(offScreenPos.x,
					offScreenPos.y, time, defaultInterpolation), Actions
					.run(new Runnable() {

						@Override
						public void run() {
							movedOut = true;
						}
					})));
		fadeOverlay();
	}

	/**
	 * Moves the pop towards the {@link #offScreenPos}
	 * 
	 * @param time
	 *            time in seconds to finish tween
	 * @param removeFromStage
	 *            if the popup should be removed from the stage when it has
	 *            finished the tween
	 * @param interpolation
	 *            the interpolation to use for the tween
	 */
	public void moveOut(float time, boolean removeFromStage,
			Interpolation interpolation) {
		movedIn = false;
		if (removeFromStage)
			addAction(Actions.sequence(Actions.moveTo(offScreenPos.x,
					offScreenPos.y, time, interpolation), Actions
					.run(new Runnable() {

						@Override
						public void run() {
							movedOut = true;
						}
					}), Actions.removeActor()));
		else
			addAction(Actions.sequence(Actions.moveTo(offScreenPos.x,
					offScreenPos.y, time, defaultInterpolation), Actions
					.run(new Runnable() {

						@Override
						public void run() {
							movedOut = true;
						}
					})));
		fadeOverlay();
	}

	/**
	 * Creates the overlay image, sets its alpha to 0, fades it in. This is
	 * called automatically if pass true in the constructor but may be called
	 * manually
	 */
	public void createOverlay(boolean visible) {
		overlay = new Image(Assets.skin.get("darkBG",
				TextureRegionDrawable.class));
		overlay.setColor(overlay.getColor().r, overlay.getColor().g,
				overlay.getColor().b, 0);
		if (visible)
			overlay.addAction(Actions.fadeIn(overlayFadeTime));
		overlay.addListener(new OverlayActorGestureListener());
		overlay.setName("darkBG");
		parent.addActor(overlay);

	}

	public void fadeOverlay() {
		if (overlay != null)
			overlay.addAction(Actions.sequence(Actions.sequence(
					Actions.fadeOut(overlayFadeTime), Actions.removeActor())));

	}

	public boolean isMovedIn() {
		return movedIn;
	}

	public boolean isMovedOut() {
		return movedOut;
	}
	
	public Image getOverlay() {
		return overlay;
	}

	private class OverlayActorGestureListener extends ActorGestureListener {

		@Override
		public void tap(InputEvent event, float x, float y, int count,
				int button) {
			fadeOverlay();
			moveOut(time, removeFromStage, defaultInterpolation);
		}

	}

}
