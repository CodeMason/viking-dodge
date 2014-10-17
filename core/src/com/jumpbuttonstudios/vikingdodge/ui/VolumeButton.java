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

package com.jumpbuttonstudios.vikingdodge.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class VolumeButton extends Button {

	ButtonStyle soundOn;
	ButtonStyle soundOff;

	public VolumeButton(VikingDodge vikingDodge, final Layout parent) {
		soundOn = Assets.skin.get("soundOnButton", ButtonStyle.class);
		soundOff = Assets.skin.get("soundOffButton", ButtonStyle.class);
		setStyle(soundOn);
		addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				parent.notify(null, Event.SOUND_STATE_CHANGE);
				setStyle(getStyle().equals(soundOn) ? soundOff : soundOn);
				return false;
			}

		});
	}

}
