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

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jumpbuttonstudios.vikingdodge.entity.Player;

public class ScoreCounterLabel extends Label {

	Player player;

	public ScoreCounterLabel(Player player, LabelStyle style) {
		super(String.format("%06d", player.getScore()), style);
		this.player = player;
	}

	@Override
	public void act(float delta) {
		setText(String.format("%06d", player.getScore()));
		super.act(delta);
	}

}
