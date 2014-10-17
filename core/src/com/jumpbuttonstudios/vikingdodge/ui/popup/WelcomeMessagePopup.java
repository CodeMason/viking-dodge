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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.MainMenuLayout;

public class WelcomeMessagePopup extends Popup {

	boolean moving = false;
	double timeMoved;
	double moveBackTime = TimeConversion.secondToNanos(3);

	Table avatarTable;
	Image avatarBox;
	Image avatar;
	Label welcomeMessage;

	public WelcomeMessagePopup(VikingDodge vikingDodge, Layout layout) {
		super(vikingDodge, layout, new Image(Assets.skin.get("welcomeBar",
				TextureRegionDrawable.class)), 1, false, false);

		moveIn(1, Interpolation.pow2);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (isMovedIn()) {
			if (!moving && TimeUtils.nanoTime() - timeMoved > moveBackTime) {
				moveOut(1, false);
				((MainMenuLayout) parent).getPlayerAvatarPopup().setCanMoveBack(true);
				moving = true;
			}
		}

		if (isMovedIn() && !((MainMenuLayout) parent).getPlayerAvatarPopup().updateAvatar){
			Log.info("Popup", "Welcome message on screen");
			((MainMenuLayout) parent).getPlayerAvatarPopup().updateAvatar = true;
		}
			

	}

	@Override
	public void create() {
		setScreenPositions(0, vikingDodge.getUi().getHeight(), 0, vikingDodge
				.getUi().getHeight() - background.getHeight());
		avatarTable = new Table();
		avatar = new Image(Network.getAvatarAsTexture());
		avatarBox = new Image(Assets.skin.get("avatarBoxLogged",
				TextureRegionDrawable.class));
		welcomeMessage = new Label("WELCOME BACK, " + Network.getUsername(),
				Assets.skin.get("label", LabelStyle.class));
	}

	@Override
	public void addActors() {
		addActor(background);
		avatar.setSize(99, 99);
		avatar.setPosition(10, 10);
		avatarTable.addActor(avatar);
		avatarTable.addActor(avatarBox);
		avatarTable.setPosition(
				background.getWidth() / 5 - (avatarBox.getWidth() / 2), 65f);
		addActor(avatarTable);
		welcomeMessage.setPosition(
				(background.getWidth() / 5) + 100,
				avatarTable.getY()
						+ ((avatar.getHeight() / 2) - welcomeMessage
								.getTextBounds().height / 2));
		addActor(welcomeMessage);
	}

	@Override
	public void registerListeners() {

	}

	@Override
	public void moveIn(float time, Interpolation interpolation) {
		super.moveIn(time, interpolation);
		timeMoved = TimeUtils.nanoTime();
	}

}
