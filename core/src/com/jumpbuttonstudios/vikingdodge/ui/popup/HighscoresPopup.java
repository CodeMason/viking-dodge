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

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudio.Friend;
import com.jumpbuttonstudio.HighscoreResult;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.IndividualHighscore;
import com.jumpbuttonstudios.vikingdodge.ui.StatLabel;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class HighscoresPopup extends Popup {

	ArrayList<HighscoreResult> globalHighscores;
	ArrayList<HighscoreResult> friendsHighscores;

	/* Each panel has a table of its own that it can store information */
	Table personal;
	Table global;
	Table globalScores;
	Table friends;
	/* Button that goes back to the main menu */
	Button back;
	ScrollPane scrollPane;

	/*
	 * This label will be displayed on the friends panel when the user has not
	 * logged in
	 */
	Label notLoggedIn;

	/*
	 * This label will be displayed on the highscores screen if no internet
	 * connection could be established
	 */
	Label noConnection;
	String noConnectionText = "NO CONNECTION";

	/** The position of the back button */
	Vector2 backButtonPos;

	public HighscoresPopup(final VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, new Image(Assets.skin.get("personalPanel",
				TextureRegionDrawable.class)), 1, true, true);

		personal.debug();
		global.debug();
		friends.debug();

		debug();
		if (fetchHighscores())
			addHighscores();
		else {
			addNoConnectionLabel(friends);
			addNoConnectionLabel(global);
		}

		addPersonalStats();

	}

	public void addHighscores() {

		int rank = 1;
		IndividualHighscore prevHighscore = null;

		if (!Network.isLoggedIn()) {
			addNotLoggedInLabel(friends);
		} else {
			ArrayList<Friend> friends = Network.getFriendsHighscore();
			for (Friend f : friends) {
				if (rank > 5)
					break;
				HighscoreResult result = new HighscoreResult();
				result.score = Network.getUserHighscore(f.user_id);
				result.username = f.username;
				IndividualHighscore hs = new IndividualHighscore(vikingDodge,
						rank, result, prevHighscore);
				this.friends.addActor(hs);
				rank++;
				prevHighscore = hs;
			}

		}
		rank = 1;
		/* If we are not in debug mode, grab the real highscores */
		if (!VikingDodge.DEBUG) {
			for (HighscoreResult result : globalHighscores) {
				if (rank > 5)
					break;
				IndividualHighscore ihs = new IndividualHighscore(vikingDodge,
						rank, result, prevHighscore);
				globalScores.addActor(ihs);
				rank++;
				prevHighscore = ihs;
			}
		} else {
			/* If we are in debug mode, just make up some */
			HighscoreResult hsr;
			int score = 23525;
			for (int x = 0; x < 100; x++) {
				if (rank > 5)
					break;
				hsr = new HighscoreResult();
				hsr.username = "User" + x;
				hsr.score = score;
				IndividualHighscore hs = new IndividualHighscore(vikingDodge,
						rank, hsr, prevHighscore);
				globalScores.addActor(hs);
				prevHighscore = hs;
				score -= 234;
				rank++;
			}
		}

	}

	/**
	 * Fetchs the global highscores from the server
	 * 
	 * @return true if the highscores were fetched
	 */
	public boolean fetchHighscores() {
		if (Network.isConnected()) {
			globalHighscores = Network.getGlobalHighscores();
			return true;
		}
		return false;
	}

	public void addNotLoggedInLabel(Table panel) {
		if (!Network.isLoggedIn() && Network.isConnected()) {
			notLoggedIn.setPosition(
					background.getWidth() / 2 - (notLoggedIn.getWidth() / 2),
					background.getHeight() / 2 - (notLoggedIn.getHeight() / 2));
			panel.addActor(notLoggedIn);
		} else {
			addNoConnectionLabel(panel);
		}
	}

	public void addNoConnectionLabel(Table panel) {
		noConnection = new Label(noConnectionText, Assets.skin.get("label",
				LabelStyle.class));
		noConnection.setPosition(
				background.getWidth() / 2 - (noConnection.getWidth() / 2),
				background.getHeight() / 2 - (noConnection.getHeight() / 2));
		panel.addActor(noConnection);
	}

	public void addPersonalStats() {
		float y = 395;
		float yIncrement = 72.5f;
		float x = 505;
		float xIncrement = 515;
		String style = "smallLabel";
		personal.addActor(new StatLabel(vikingDodge.stats.gamesPlayed,
				Assets.skin.get(style, LabelStyle.class), 505, y));
		personal.addActor(new StatLabel(vikingDodge.stats.bouldersDodges,
				Assets.skin.get(style, LabelStyle.class), 505, y - (yIncrement)));
		personal.addActor(new StatLabel(vikingDodge.stats.timesSqaushed,
				Assets.skin.get(style, LabelStyle.class), 505, y
						- (yIncrement * 2)));
		personal.addActor(new StatLabel(vikingDodge.stats.sheepSaved,
				Assets.skin.get(style, LabelStyle.class), 505, y
						- (yIncrement * 3)));
		personal.addActor(new StatLabel(vikingDodge.stats.sheepKilled,
				Assets.skin.get(style, LabelStyle.class), 505, y
						- (yIncrement * 4)));

		personal.addActor(new StatLabel(vikingDodge.stats.highScore,
				Assets.skin.get(style, LabelStyle.class), 505 + xIncrement, y));
		personal.addActor(new StatLabel(vikingDodge.stats.dragonsSpawned,
				Assets.skin.get(style, LabelStyle.class), 505 + xIncrement, y
						- (yIncrement)));
		personal.addActor(new StatLabel(vikingDodge.stats.deathByFireball,
				Assets.skin.get(style, LabelStyle.class), 505 + xIncrement, y
						- (yIncrement * 2)));
	}

	/**
	 * Brings a specific panel to the front, as well as the buttons
	 * 
	 * @param panel
	 */
	public void bringPanelToFront(Table panel) {
		panel.toFront();
		back.toFront();
	}

	public Table createTabButtons() {
		Table table = new Table();
		Button[] buttons = new Button[3];
		for (int x = 0; x < 3; x++) {
			buttons[x] = new Button(Assets.skin.get("emptyButton",
					ButtonStyle.class));
			buttons[x].setName("button" + x);
		}
		buttons[0].addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {

				back.setPosition(background.getWidth()
						- (background.getWidth() / 3) + (back.getWidth() / 2),
						backButtonPos.y);
				bringPanelToFront(personal);
			}
		});
		buttons[1].addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {

				back.setPosition(backButtonPos.x, backButtonPos.y);
				bringPanelToFront(global);
			}
		});
		buttons[2].addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {

				back.setPosition(backButtonPos.x, backButtonPos.y);
				bringPanelToFront(friends);
			}
		});
		for (Button button : buttons) {
			table.add(button);
		}

		table.setPosition(575, 625);
		return table;
	}

	@Override
	public void create() {
		setScreenPositions(0, 1000, 0, 0);
		personal = new Table();
		global = new Table();
		friends = new Table();
		globalScores = new Table();
		setFillParent(true);
		backButtonPos = new Vector2(background.getWidth() / 2, 75);
		back = new Button(Assets.skin.get("backButton", ButtonStyle.class));
		notLoggedIn = new Label("LOGIN TO SEE FRIENDS HIGHSCORES",
				Assets.skin.get("label", LabelStyle.class));
		scrollPane = new ScrollPane(globalScores);

	}

	@Override
	public void addActors() {
		personal.setPosition(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), vikingDodge.getUi()
						.getHeight() / 2 - (background.getHeight() / 2));
		global.setPosition(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), vikingDodge.getUi()
						.getHeight() / 2 - (background.getHeight() / 2));
		friends.setPosition(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), vikingDodge.getUi()
						.getHeight() / 2 - (background.getHeight() / 2));

		personal.addActor(new Image(Assets.skin.get("personalPanel",
				TextureRegionDrawable.class)));
		global.addActor(new Image(Assets.skin.get("globalPanel",
				TextureRegionDrawable.class)));
		friends.addActor(new Image(Assets.skin.get("friendsPanel",
				TextureRegionDrawable.class)));

		personal.addActor(createTabButtons());
		global.addActor(createTabButtons());
		global.addActor(scrollPane);
		friends.addActor(createTabButtons());

		globalScores.setFillParent(true);
		// scrollPane.setOrigin(scrollPane.getRight(), scrollPane.getTop());
		scrollPane.setForceScroll(false, true);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setSize(background.getWidth(), 600);

		back.setPosition(backButtonPos.x, backButtonPos.y);
		addActor(personal);
		addActor(friends);
		addActor(global);
		addActor(back);

	}

	@Override
	public void registerListeners() {
		back.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				moveOut(1, true);
			}

		});
	}

}
