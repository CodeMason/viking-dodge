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

import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.Animator;
import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Sheep;

public class SheepSquashAction extends EntityAction<Sheep> {

	public boolean canDespawn = false;

	double despawnTime = TimeConversion.secondToNanos(5);
	double despawnStartTime;

	public SheepSquashAction(Sheep entity) {
		super(entity);
	}

	@Override
	public void execute() {
		Animator a = getEntity().getAnimator();
		if (a.getScaleY() - 1.25f * 0.10f > 0.1f) {
			a.setScale(1, a.getScaleY() - 1.25f * 0.10f);
			despawnStartTime = TimeUtils.nanoTime();
		} else {
			a.setScale(1, 0.1f);
			if (TimeUtils.nanoTime() - despawnStartTime > despawnTime)
				canDespawn = true;
		}

	}

}
