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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class TutorialPopup extends Popup {

	/* All the slides in this popup */
	Array<TutorialSlide> slides;
	
	/* The current slide */
	TutorialSlide currentSlide;

	public TutorialPopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, null, 1, true, true);
//		setFillParent(true);
		setScreenPositions(0, 0, 0, 0);
		
		nextSlide();
		
	}

	@Override
	public void create() {

		slides = new Array<TutorialPopup.TutorialSlide>();
		
		slides.add(new TutorialSlide(this, vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_TUTORIAL0))));
		slides.add(new TutorialSlide(this, vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_TUTORIAL1))));
		slides.add(new TutorialSlide(this, vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_TUTORIAL2))));
		slides.add(new TutorialSlide(this, vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_TUTORIAL3))));
		slides.add(new TutorialSlide(this, vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_TUTORIAL4))));
		slides.reverse();
		
//		createOverlay(true);
//		overlay.toFront();
		overlay.addListener(new ActorGestureListener(){
			
			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				if(currentSlide != null)
					currentSlide.addAction(Actions.moveTo(currentSlide.getX(), -600, 1, Interpolation.exp10));
				}
			
		});
	}

	@Override
	public void addActors() {
//		for(TutorialSlide slide : slides)
//			addActor(slide);
	}

	@Override
	public void registerListeners() {

	}
	
	

	public void nextSlide() {
		if (slides.size > 0) {
			final TutorialSlide toMoveIn = slides.pop();
			currentSlide = toMoveIn;
			addActor(toMoveIn);
//			toMoveIn.toFront();
			toMoveIn.moveIn(1, Interpolation.exp10Out);
			toMoveIn.addAction(Actions
					.run(new Runnable() {

						@Override
						public void run() {
							toMoveIn.setScreenPositions(toMoveIn.getX(),
									-1000, 0, 0);
						}
					}));
		} else {
			fadeOverlay();
		}
	}

	public class TutorialSlide extends Popup {

		TutorialPopup tutorialPopup;

		public TutorialSlide(TutorialPopup tutorialPopup,
				VikingDodge vikingDodge, Layout parent, Image background) {
			super(vikingDodge, parent, background, 1, false, false);
			this.tutorialPopup = tutorialPopup;
		}

		@Override
		public void create() {
			setScreenPositions(
					vikingDodge.getUi().getWidth() / 2
							- (background.getWidth() / 2),
					vikingDodge.getUi().getHeight() * 1.25f,
					vikingDodge.getUi().getWidth() / 2
							- (background.getWidth() / 2),
					vikingDodge.getUi().getHeight() / 2
							- (background.getHeight() / 2));
		}

		@Override
		public void addActors() {
			addActor(background);

		}

		@Override
		public void registerListeners() {
			background.addListener(new ActorGestureListener() {

				@Override
				public void tap(InputEvent event, float x, float y, int count,
						int button) {
					addAction(Actions.sequence(Actions.moveTo(offScreenPos.x,
							offScreenPos.y, 1, Interpolation.exp10In), Actions.run(new Runnable() {
								
								@Override
								public void run() {
									tutorialPopup.nextSlide();
									// TODO Auto-generated method stub
									
								}
							}), Actions.removeActor()));
				}

			});
		}

	}

}
