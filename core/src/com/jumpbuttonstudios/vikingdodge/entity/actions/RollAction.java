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

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.collision.CollisionFilters;
import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Player;

public class RollAction extends EntityAction<Player> {
	
	double firstTap;
	
	double tapTime = TimeConversion.secondToNanos(0.25f);
	
	Filter oldFilter;

	public RollAction(Player entity) {
		super(entity, TimeConversion.secondToNanos(2f));
	}

	@Override
	public void execute() {
		setLastExecution();
		getEntity()
				.getPhysics()
				.getBody()
				.applyForceToCenter(getEntity().isFacingLeft() ? -90 : 90, 0,
						true);
	}
	
	public void setFirstTapTime(){
		firstTap = TimeUtils.nanoTime();
	}
	
	public boolean tapWithinDoubleTapTime(){
		if(TimeUtils.nanoTime() - firstTap < tapTime)
			return true;
		return false;
	}
	
	public void setHeadToNotCollideWithRocks(){
		oldFilter = getEntity().getPhysics().getBody().getFixtureList().get(0).getFilterData();
		getEntity().setCollisionFilters(1, CollisionFilters.PLAYER, (short)-1);
	}
	
	public void revertToOldCollisionData(){
		getEntity().setCollisionFilters(1, oldFilter.categoryBits, oldFilter.maskBits);
	}
	

}
