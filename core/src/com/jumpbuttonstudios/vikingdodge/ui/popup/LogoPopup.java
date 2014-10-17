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

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class LogoPopup extends Popup {

	public LogoPopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, new Image(Assets.skin.get("logo",
				TextureRegionDrawable.class)), 1, false, false);
	}

	@Override
	public void create() {
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2 - (background.getWidth() / 2),
				vikingDodge.getUi().getHeight(), 
				vikingDodge.getUi().getWidth() / 2 - (background.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2);
	}

	@Override
	public void addActors() {
		addActor(background);
	}

	@Override
	public void registerListeners() {

	}

}
