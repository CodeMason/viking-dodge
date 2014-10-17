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
import com.jumpbuttonstudios.vikingdodge.entity.Sheep;

public class SheepSaySomethingAction extends EntityAction<Sheep> {

	/* The last time the player said something */
	float lastSpeech;
	/* How often the player can say something */
	float freq = 5;
	
	/* The random number to determine what to say, if anything */
	int random;

	public SheepSaySomethingAction(Sheep entity) {
		super(entity);
	}

	public void update(float delta) {
		lastSpeech += delta;

		execute();
	}

	@Override
	public void execute() {
		if (lastSpeech > freq) {
			random = MathUtils.random(0, 2);
			switch (random) {
			case 1:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_I_COULD_MOVE),
						getEntity(), 3), true);
				break;
			case 2:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_SOFT_N_FLUFFY),
						getEntity(), 3), true);
				break;
			case 3:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_SO_LONEY), getEntity(),
						3), true);
				break;
			case 4:
				saySomething(new SpeechBubble(
						Assets.get(Assets.SPEECHBUBBLE_SIT_HERE), getEntity(),
						3), true);
				break;
			default:
				lastSpeech = 0;
				freq = MathUtils.random(10, 20);
				break;
			}
		}
	}

	public void saySomething(SpeechBubble speechBubble, boolean sayInstantly) {
		if (sayInstantly) {
			getEntity().setSpeechBubble(speechBubble);
		}else{
			random = MathUtils.random(0, 2);
			if(random == 1)
				getEntity().setSpeechBubble(speechBubble);
				
		}
		lastSpeech = 0;
		freq = MathUtils.random(10, 20);
	}

}
