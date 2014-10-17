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
import com.jumpbuttonstudios.vikingdodge.entity.Player;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.ui.HeartUI;

public class SquashAction extends EntityAction<Player> {

	/** If the player can spawn, is true when the squash action is finished */
	public boolean canSpawn = false;

	public SquashAction(Player entity) {
		super(entity);

	}

	public SquashAction(Player entity, double executionFreq) {
		super(entity, executionFreq);
	}

	@Override
	public void execute() {
		getEntity().getAnimator().setScale(
				1,
				getEntity().getAnimator().getScaleY() > 0.1f ? getEntity()
						.getAnimator().getScaleY() - 1.25f * 0.10f : 0.1f);
		if (canExecute() && !canSpawn) {
			if (HeartUI.lifes != 0) {
				canSpawn = true;
				getEntity().respawn();
			}else{
				getEntity().notify(null, Event.GAMEOVER);
			}
		}
	}

}
