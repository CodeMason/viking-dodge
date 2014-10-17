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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.MainMenuLayout;

public class LoginPopup extends Popup {

	/* The url to open for registering */
	final String REGISTER_URL = "https://www.jumpbuttonstudio.com/register/";

	boolean packetRecieved = false;

	Table usernameAndPasswordTable;
	TextFieldStyle style;
	TextField username;
	TextField password;

	Table buttonsTable;
	Button help;
	Button ok;
	Button register;

	public LoginPopup(final VikingDodge vikingDodge, Layout layout) {
		super(vikingDodge, layout, new Image(Assets.skin.get("loginWindow",
				TextureRegionDrawable.class)), 1f, true, true);

		moveIn(1, Interpolation.elastic);

	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (Network.isLoggedIn() && packetRecieved) {
			MainMenuLayout mainMenuLayout = (MainMenuLayout) parent;
			mainMenuLayout.getLogin().setStyle(
					Assets.skin.get("logout", ButtonStyle.class));
			packetRecieved = false;
			moveOut(1, true);
			mainMenuLayout.getPlayerAvatarPopup().moveOut(1, false, Interpolation.exp10);
			parent.addActor(new WelcomeMessagePopup(vikingDodge, parent));
		} else if (!Network.isLoggedIn() && packetRecieved) {
			parent.addActor(new WrongPasswordPopup(vikingDodge, this, parent));
			packetRecieved = false;
		}

	}

	@Override
	public void create() {
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), 850, vikingDodge.getUi()
						.getWidth() / 2 - (background.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2
						- (background.getHeight() / 2));

		usernameAndPasswordTable = new Table();
		style = new TextFieldStyle();

		style.font = Assets.get(Assets.UI_FONT_TEXT_FIELD);
		style.fontColor = Color.WHITE;
		style.disabledFontColor = Color.ORANGE;

		username = new TextField("", style);
		password = new TextField("", style);
		password.setPasswordCharacter('*');
		password.setPasswordMode(true);

		buttonsTable = new Table();

		help = new Button(Assets.skin.get("helpButton", ButtonStyle.class));
		ok = new Button(Assets.skin.get("okButton", ButtonStyle.class));
		register = new Button(Assets.skin.get("registerButton",
				ButtonStyle.class));

		// TODO Remove later, testing
//		username.setText("gibbo");
//		password.setText("tycoon");
	}

	@Override
	public void addActors() {

		addActor(background);

		usernameAndPasswordTable.setFillParent(true);

		username.setPosition(60, 350);
		username.setWidth(525);
		password.setPosition(60, 210);
		password.setWidth(525);
		usernameAndPasswordTable.addActor(username);
		usernameAndPasswordTable.addActor(password);

		addActor(usernameAndPasswordTable);
		buttonsTable.add(help);
		buttonsTable.add(ok).padRight(80).padLeft(80);
		buttonsTable.add(register);
		buttonsTable.setPosition(background.getWidth() / 2, 115);
		addActor(buttonsTable);

	}

	@Override
	public void registerListeners() {
		ok.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				Network.login(username.getText(), password.getText());
				packetRecieved = true;

			}
		});

		register.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				Gdx.net.openURI(REGISTER_URL);
			}

		});
	}

}
