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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class PlayerAvatarPopup extends Popup {

	/* The name of the avatar box in the scene2d stage, used to find and change */
	public static final String AVATAR_NAME = "avatarBox";
	/*
	 * The name of the username label in the scene2d stage, used to find and
	 * change
	 */
	public static final String USERNAME_NAME = "usernameName";

	/* If the avatar should be updated */
	public boolean updateAvatar = true;

	/* If we can move back into the visible screen */
	boolean canMoveBack = false;

	/* If the avatar is loaded in */
	boolean isAvatarReady = false;

	/* If the avatar has been changed while off the screen */
	boolean canChangeAvatar = false;

	Table avatarTable;
	private Image avatarBox;
	Image avatar;
	Label name;

	public PlayerAvatarPopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, new Image(Assets.skin.get("avatarBox",
				TextureRegionDrawable.class)), 1, true, true);

	}

	public PlayerAvatarPopup(VikingDodge vikingDodge, Layout parent,
			boolean addOverlay, boolean overlayVisible) {
		super(vikingDodge, parent, new Image(Assets.skin.get("avatarBox",
				TextureRegionDrawable.class)), 1, addOverlay, overlayVisible);

	}

	@Override
	public void create() {
		avatarTable = new Table();
		avatar = new Image();
		if (Network.isLoggedIn()) {
			avatar.setDrawable(Assets.skin.get("avatar",
					TextureRegionDrawable.class));
			avatarBox = new Image(Assets.skin.get("avatarBoxLogged",
					TextureRegionDrawable.class));
		} else {
			avatarBox = new Image(Assets.skin.get("avatarBox",
					TextureRegionDrawable.class));
		}
		name = new Label(Network.getUsername(), Assets.skin.get("label",
				LabelStyle.class));

		setScreenPositions(-400, vikingDodge.getUi().getHeight()
				- (getAvatarBox().getHeight() + 10), 10, vikingDodge.getUi()
				.getHeight() - (getAvatarBox().getHeight() + 10));

		avatarBox.setName(AVATAR_NAME);
		name.setName(USERNAME_NAME);
	}

	@Override
	public void addActors() {
		name.setPosition(avatarBox.getRight() + 10,
				avatarBox.getTop() - (name.getTextBounds().height) - 10);
		avatarTable.addActor(avatarBox);
		addActor(avatarTable);
		addActor(name);
	}

	@Override
	public void registerListeners() {
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (updateAvatar && isMovedOut()) {
			if (Network.isLoggedIn() && Network.isAvatarDownloaded()
					&& !canChangeAvatar) {
				avatarBox.setDrawable(Assets.skin.get("avatarBoxLogged",
						TextureRegionDrawable.class));
				name.setText(Network.getUsername());
				avatar.setDrawable(new TextureRegionDrawable(new TextureRegion(
						Network.getAvatarAsTexture())));
				avatar.setSize(99, 99);
				avatar.setPosition(10, 10);
				avatarTable.addActor(avatar);
				avatarBox.toFront();
				canChangeAvatar = true;
				updateAvatar = false;
				Log.info("Player Avatar", "Off screen, changing avatar to "
						+ Network.getUsername() + "'s avatar");
				canChangeAvatar = true;
				updateAvatar = false;
			} else if (!Network.isLoggedIn() && !canChangeAvatar) {
				avatarBox.setDrawable(Assets.skin.get("avatarBox",
						TextureRegionDrawable.class));
				setUsername("GUEST");
				removeActor(avatar);
				canChangeAvatar = true;
				updateAvatar = false;
				Log.info("Player Avatar", "Updated player avatar to Guest");
			}
		}

		if ((isMovedOut() && canChangeAvatar && canMoveBack)) {
			Log.info("Avatar Popup", "Avatar changed, moving back in");
			moveIn(1, Interpolation.pow5);
			canMoveBack = false;
			canChangeAvatar = false;

		}
		

	}

	public void removeAvatar() {
		isAvatarReady = false;

	}

	public void setAvatarChanged(boolean avatarChanged) {
		this.canChangeAvatar = avatarChanged;
	}

	public boolean isAvatarChanged() {
		return canChangeAvatar;
	}

	public void setCanMoveBack(boolean canMoveBack) {
		this.canMoveBack = canMoveBack;
	}

	public void setUsername(String newName) {
		this.name.setText(newName);
	}

	public Image getAvatarBox() {
		return avatarBox;
	}

	public Vector2 getOffScreenPos() {
		return offScreenPos;
	}

	public Vector2 getOnScreenPos() {
		return onScreenPos;
	}

}
