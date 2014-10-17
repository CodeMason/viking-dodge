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

import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Mob;

public class BlinkAction extends EntityAction<Mob> {

	public boolean finished;
	/*
	 * How many times we can blink before we are done and move onto the next
	 * animation
	 */
	int maxBlinks = 2;
	/* How many blinks we have done so far */
	public int blinks = 0;

	public BlinkAction(Mob entity) {
		super(entity);
	}

	@Override
	public void execute() {
		if (getEntity().getAnimator().isFinished()) {
			if (blinks == maxBlinks) {
				finished = true;
			} else {
				getEntity().getAnimator().play();
				blinks++;
			}
		}
	}

}
