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

package com.jumpbuttonstudios.vikingdodge.ui.layouts;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.ui.popup.LoginPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.LogoutConfirmationPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.NoConnectionPopup;

public class LoginButtonListener extends ActorGestureListener {

	VikingDodge vikingDodge;
	MainMenuLayout layout;

	public LoginButtonListener(VikingDodge vikingDodge, MainMenuLayout layout) {
		this.vikingDodge = vikingDodge;
		this.layout = layout;
	}

	@Override
	public void tap(InputEvent event, float x, float y, int count, int button) {
		layout.notify(null, Event.BUTTON_PRESSED);
		if (Network.isConnected()) {
			if (!Network.isLoggedIn()) {
				LoginPopup lp = new LoginPopup(vikingDodge, layout);
				layout.addActor(lp);
			} else {
				LogoutConfirmationPopup lcp = new LogoutConfirmationPopup(
						vikingDodge, layout);
				layout.addActor(lcp);
			}
		} else {
			NoConnectionPopup ncp = new NoConnectionPopup(vikingDodge, layout);
			layout.addActor(ncp);
		}
	}
}
