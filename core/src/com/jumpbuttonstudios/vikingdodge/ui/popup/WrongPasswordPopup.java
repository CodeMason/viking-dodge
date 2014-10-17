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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class WrongPasswordPopup extends Popup {

	LoginPopup loginPopup;

	Table buttonsTable;
	Button ok;

	public WrongPasswordPopup(final VikingDodge vikingDodge,
			final LoginPopup loginPopup, Layout layout) {
		super(vikingDodge, layout, new Image(Assets.skin.get("wrongInfo",
				TextureRegionDrawable.class)), 1, true, false);

		moveIn(1, Interpolation.elastic);

	}

	@Override
	public void create() {
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2), 800, vikingDodge.getUi()
						.getWidth() / 2 - (background.getWidth() / 2), 150);
		buttonsTable = new Table();
		ok = new Button(Assets.skin.get("okButton", ButtonStyle.class));

	}

	@Override
	public void addActors() {
		ok.setPosition(background.getWidth() / 2 - (ok.getWidth() / 2), 50);
		addActor(background);
		buttonsTable.addActor(ok);
		addActor(buttonsTable);

	}

	@Override
	public void registerListeners() {
		ok.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				moveOut(1, true);
			}

		});

	}

}
