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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudio.HighscoreResult;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;

public class IndividualHighscore extends Table {

	HighscoreResult result;
	IndividualHighscore prevHighscore;

	Image helmet;
	Image background;
	Label rank;
	Label username;
	Label highscore;

	/** The offset to position the score relative to the previous one */
	float offsetY = 70f;

	/** The position of things on the Y axis */
	float yPos = 10;

	/**
	 * The helmet offset on the X axis, helmet sprites vary in size so require
	 * tweaking
	 */
	Vector2 helmeOffset = new Vector2(0, 0);

	public IndividualHighscore(VikingDodge vikingDodge, int rank,
			HighscoreResult highscoreResult, IndividualHighscore prevHighscore) {
		this.result = highscoreResult;
		this.prevHighscore = prevHighscore;
		addHelmet(rank);

		background = new Image(Assets.skin.get("playerBar",
				TextureRegionDrawable.class));
		this.rank = new Label(String.valueOf(rank) + ".", Assets.skin.get(
				"smallLabel", LabelStyle.class));
		username = new Label(highscoreResult.username.toUpperCase(),
				Assets.skin.get("smallLabel", LabelStyle.class));
		highscore = new Label(String.format("%09d", highscoreResult.score),
				Assets.skin.get("smallLabel", LabelStyle.class));

		setPosition(27.5f, prevHighscore == null ? 500 : prevHighscore.getY()
				- offsetY);

		this.rank.setPosition(25, yPos);
		this.username.setPosition(250, yPos);
		highscore.setPosition(750, yPos);
		addActor(background);
		addActor(this.rank);
		addActor(username);
		addActor(highscore);
		if (helmet != null) {
			helmet.setPosition(135 + helmeOffset.x, 25 + helmeOffset.y);
			addActor(helmet);
		}
	}

	public void addHelmet(int rank) {
		if (result.score != 0)
			switch (rank) {
			case 1:
				helmet = new Image(Assets.skin.get("goldHelmet",
						TextureRegionDrawable.class));
				break;
			case 2:
				helmet = new Image(Assets.skin.get("silverHelmet",
						TextureRegionDrawable.class));
				helmeOffset.set(5, 5);
				break;
			case 3:
				helmet = new Image(Assets.skin.get("bronzeHelmet",
						TextureRegionDrawable.class));
				helmeOffset.set(5, 5);
				break;
			default:
				break;
			}
	}

	public float getHelmetOffsetX() {
		return helmeOffset.x;
	}

	public float getHelmetOffsetY() {
		return helmeOffset.y;
	}

	public IndividualHighscore(Skin skin) {
		super(skin);
	}

}
