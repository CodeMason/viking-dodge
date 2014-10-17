/**
 * Copyright 2014 Jump Button Studios
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

package com.jumpbuttonstudios.vikingdodge.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;

/**
 * A simple loading screen, here we can load all the assets ready to be used in
 * the game
 * 
 * @author Stephen Gibson
 */
public class LoadingScreen implements Screen {

	/** Instance of the game */
	VikingDodge vikingDodge;

	/** The total percentage of loading */
	public static float percentageLoaded;

	/** Time to wait after loading before starting move out animation */
	double waitTime = TimeConversion.secondToNanos(0.5f);
	
	/** If the game can actually load assets, this is true when the widgets have animated into position*/
	public boolean canLoad = false;

	/** The time we finished loading assets at */
	double finishTime;

	/**
	 * If we can change the screen, set when the animation as finished, assets
	 * are loaded and we have waited the allocated time
	 */
	boolean canChangeScreen = false;

	public LoadingScreen(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;

		/*
		 * Load the BG, logo and font in first so we can display a loading
		 * screen
		 */
		Assets.manager.load(Assets.MUSIC_MENU);
		Assets.manager.load(Assets.UI_MENU_BG);
		Assets.manager.load(Assets.UI_WIDGET_LOGO);
		Assets.manager.load(Assets.UI_FONT);
		/* Force manager to load assets before continuing */
		Assets.manager.finishLoading();
		
		/* Play the initial track */
		vikingDodge.getSoundManager().playTrack(Assets.get(Assets.MUSIC_MENU));
		/* Load loading screen ui */
		Assets.loadingScreenUIDependencies();

		/* Load all the assets */
		Assets.load();

		/* Change to loading screen UI */
		vikingDodge.getUi().changeLayout(
				vikingDodge.getUi().getLoadingScreenLayout());

	}

	@Override
	public void render(float delta) {

		/* Get the progress and multiple it by 100 to get a value of 0-100% */
		percentageLoaded = Assets.manager.getProgress() * 100;

		/* Update the UI */
		vikingDodge.getUi().act(delta);
		/* Draw the UI */
		vikingDodge.getUi().draw();

		/* Load all the stuffz */
		if (canLoad)
			if (Assets.manager.update()) {
				/* Load the UI using loaded assets */
				if (!Assets.uiLoaded)
					Assets.loadUI();
					if (!vikingDodge.getUi().getLoadingScreenLayout().actorsMovingOut) {
						if (TimeUtils.nanoTime() - finishTime > waitTime) {
							vikingDodge.getUi().getLoadingScreenLayout()
									.addMoveOutAction();
							canChangeScreen = true;
						}
						/* Change to a new GameScreen once loading as finished */
					}
					if (vikingDodge.getUi().getLoadingScreenLayout().movedOut()
							&& canChangeScreen)
						vikingDodge.setScreen(new MainMenuScreen(vikingDodge));

			} else {
				finishTime = TimeUtils.nanoTime();
			}

	}

	@Override
	public void resize(int width, int height) {
		// cam.setToOrtho(false, width, height);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
