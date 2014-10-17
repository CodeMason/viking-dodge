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

import com.badlogic.gdx.Net;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.MainMenuLayout;

public class FacebookOrJBSPopup extends Popup {

	Button facebook;
	Button JBS;
	Image text;

	public FacebookOrJBSPopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent,
				new Image(Assets.get(Assets.UI_WINDOW_SMALL)), 1, true, true);

//		moveIn(1, Interpolation.elastic);

	}

	@Override
	public void create() {
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), 850, vikingDodge.getUi()
						.getWidth() / 2 - (background.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2
						- (background.getHeight() / 2));

		facebook = new Button(Assets.skin.get("facebookButton",
				ButtonStyle.class));
		JBS = new Button(Assets.skin.get("jbsButton", ButtonStyle.class));
		text = new Image(Assets.get(Assets.UI_WIDGET_TEXT));

		// JBS.setSize(JBS.getWidth() / 4, JBS.getHeight() / 4);
		// facebook.setSize(facebook.getWidth() / 2, facebook.getHeight() /2);
		facebook.setPosition(background.getWidth() / 2
				- (facebook.getWidth() / 2 - 100), background.getHeight() / 2
				- (facebook.getHeight() / 2 + 30));
		JBS.setPosition(background.getWidth() / 2
				- (facebook.getWidth() / 2 + 100), background.getHeight() / 2
				- (facebook.getHeight() / 2 + 30));
		text.setPosition(background.getWidth() / 2 - (text.getWidth() / 2),
				background.getHeight() - (text.getHeight() + 30));

	}

	@Override
	public void addActors() {
		addActor(background);
		addActor(text);
		addActor(facebook);
		addActor(JBS);

	}

	@Override
	public void registerListeners() {
		facebook.addListener(new ActorGestureListener(){
			
			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				Network.login();
				super.tap(event, x, y, count, button);
			}
			
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (Network.userAcceptedFacebookLoginRequest()) {
			System.out.println("User has accepted login request");
			if (Network.userFetched()) {
				moveOut(1, true);
				System.out.println("User has been fetched");
				if (Network.canGameStateChange()) {
					System.out.println("Updating UI");
					MainMenuLayout mainMenuLayout = (MainMenuLayout) parent;
					mainMenuLayout.getLogin().setStyle(
							Assets.skin.get("logout", ButtonStyle.class));
					moveOut(1, true);
					mainMenuLayout.getPlayerAvatarPopup().moveOut(1, false,
							Interpolation.exp10);
					parent.addActor(new WelcomeMessagePopup(vikingDodge, parent));
				}
			}
		} else if (!Network.isLoggedIn()
				&& Network.userAcceptedFacebookLoginRequest()) {
			// parent.addActor(new WrongPasswordPopup(vikingDodge, this,
			// parent));
		}
	}

}
