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

import java.util.zip.Adler32;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.interfaces.AdListener;
import com.jumpbuttonstudios.vikingdodge.screens.GameScreen;
import com.jumpbuttonstudios.vikingdodge.ui.ScoreCounterLabel;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.GameScreenLayout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class GameOverPopup extends Popup {

	Label scoreCounterLabel;

	Button mainmenu;
	Button playagain;
	Button highscores;

	Button facebook;
	Button twitter;

	public GameOverPopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, new Image(
				Assets.get(Assets.UI_WINDOW_GAMEOVER)), 1, false, false);

		toFront();
		VikingDodge.adListener.requestAd(AdListener.FULL);
	}

	@Override
	public void create() {
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), vikingDodge.getUi()
						.getHeight() * 1.25f, vikingDodge.getUi().getWidth()
						/ 2 - (background.getWidth() / 2), vikingDodge.getUi()
						.getHeight() / 2 - (background.getHeight() / 2.5f));

		mainmenu = new Button(Assets.skin.get("mainmenuGoButton",
				ButtonStyle.class));
		playagain = new Button(Assets.skin.get("playagainButton",
				ButtonStyle.class));
		highscores = new Button(
				Assets.skin.get("highscores", ButtonStyle.class));

		facebook = new Button(Assets.skin.get("facebookButton",
				ButtonStyle.class));
		twitter = new Button(
				Assets.skin.get("twitterButton", ButtonStyle.class));

		scoreCounterLabel = new ScoreCounterLabel(vikingDodge
				.getEntityHandler().getPlayer(), Assets.skin.get("largeLabel",
				LabelStyle.class));
		scoreCounterLabel.setPosition(background.getWidth() / 2
				- (scoreCounterLabel.getTextBounds().width / 2),
				background.getHeight() - scoreCounterLabel.getHeight() * 2f);

		playagain.setPosition(background.getWidth() / 2
				- (playagain.getWidth() / 2), 0 + (playagain.getHeight() / 4));
		mainmenu.setPosition(background.getWidth() / 2
				- (mainmenu.getWidth() / 2) - 160,
				0 + (mainmenu.getHeight() / 10));
		highscores.setPosition(
				background.getWidth() / 2 - (highscores.getWidth() / 2) + 160,
				0 + (mainmenu.getHeight() / 10));

		twitter.setSize(twitter.getWidth() / 2, twitter.getHeight() / 2);
		twitter.setPosition(background.getWidth() / 2
				- (twitter.getWidth() / 2) + 60, playagain.getTop());
		facebook.setSize(facebook.getWidth() / 2, facebook.getHeight() / 2);
		facebook.setPosition(background.getWidth() / 2
				- (facebook.getWidth() / 2) - 60, playagain.getTop());

	}

	@Override
	public void addActors() {
		addActor(background);
		addActor(scoreCounterLabel);
		addActor(playagain);
		addActor(mainmenu);
		addActor(highscores);
		// addActor(facebook);
		// addActor(twitter);
	}

	@Override
	public void registerListeners() {
		final GameScreenLayout gs = (GameScreenLayout) parent;
		playagain.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				moveOut(1, false, Interpolation.exp10);
				gs.getOverlay().addAction(
						Actions.sequence(Actions.moveTo(0, 0, 1,
								Interpolation.bounceOut), Actions
								.run(new Runnable() {
									@Override
									public void run() {
										vikingDodge.setScreen(new GameScreen(
												vikingDodge));
									}
								}), Actions.moveTo(0, gs.getOverlay()
								.getHeight(), 1, Interpolation.exp10)));
			}

		});

		mainmenu.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				fadeOverlay();
				moveOut(1, false);
				parent.notify(null, Event.CHANGED_TO_MENU);
				gs.returnToMenu();
				return false;
			}

		});
		;

		highscores.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				HighscoresPopup hsp = new HighscoresPopup(vikingDodge, parent);
				hsp.moveIn(1, Interpolation.elastic);
				gs.addActor(hsp);
			}

		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		// if(VikingDodge.adListener.isFullScreenAdLoaded()){
		// VikingDodge.adListener.loadFullScreen();
		// }
	}

	@Override
	public void moveIn(float time, Interpolation interpolation) {
		super.moveIn(time, interpolation);
		createOverlay(true);
		overlay.clearListeners();
		toFront();
		((GameScreenLayout) parent).getOverlay().toFront();
		vikingDodge.stats.highScore = vikingDodge.getEntityHandler()
				.getPlayer().getScore() > vikingDodge.stats.highScore ? vikingDodge
				.getEntityHandler().getPlayer().getScore()
				: vikingDodge.stats.highScore;
	}

}
