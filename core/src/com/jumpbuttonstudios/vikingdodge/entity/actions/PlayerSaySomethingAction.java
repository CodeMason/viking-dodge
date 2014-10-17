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

package com.jumpbuttonstudios.vikingdodge.entity.actions;

import com.badlogic.gdx.math.MathUtils;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Player;

public class PlayerSaySomethingAction extends EntityAction<Player> {

	/* The last time the player said something */
	float lastSpeech;
	/* How often the player can say something */
	float freq = 5;

	public PlayerSaySomethingAction(Player entity) {
		super(entity);
	}

	public void update(float delta) {
		lastSpeech += delta;

		execute();
	}

	@Override
	public void execute() {
		if (lastSpeech > freq) {
			int random = MathUtils.random(0, 10);
			System.out.println(random);
			switch (random) {
			case 1:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_NO_FEAR), getEntity(), 3));
				break;
			case 3:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_BEARD_MANLY),
						getEntity(), 3));
				break;
			case 4:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_CRUSH_SOMETHING),
						getEntity(), 3));
				break;
			case 5:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_DRAGON_QUESTION),
						getEntity(), 3));
				break;
			case 6:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_SO_SHORT), getEntity(),
						3));
				break;
			case 7:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_WHERE_HAMMMER),
						getEntity(), 3));
				break;
			default:
				lastSpeech = 0;
				freq = MathUtils.random(15, 25);
				break;
			}
		}
	}

	public void saySomething(SpeechBubble speechBubble) {
		getEntity().setSpeechBubble(speechBubble);
		lastSpeech = 0;
		freq = MathUtils.random(15, 25);
	}

}
