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

package com.jumpbuttonstudios.vikingdodge.entity.spawn;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

/**
 * 
 * @author Gibbo
 * @param <T>
 * 
 */
public class Spawner implements Updatable{

	/** Entity handler instance */
	EntityHandler entityHandler;
	
	/** The spawn frequency of the spawner, in nano seconds */
	protected double spawnFreq;
	
	/** The last time something was spawned from this spawner */
	protected double lastSpawn;

	/** The position of this spawner in the world */
	protected Vector2 pos = new Vector2();
	

	/** If this spawner can spawn an entity */
	protected boolean canSpawn = true;


	public Spawner(EntityHandler entityHandler, float x, float y, double spawnFreq) {
		this.entityHandler = entityHandler;
		pos.set(x, y);
		this.spawnFreq = spawnFreq;
		this.lastSpawn = TimeUtils.nanoTime();
	}
	
	
	@Override
	public void update(float delta) {
		lastSpawn += delta;
		
		if(!canSpawn && lastSpawn > spawnFreq)
			canSpawn = true;
		
	}
	
	
	public void setLastSpawnTime(){
		this.lastSpawn = TimeUtils.nanoTime();
	}

	public void setCanSpawn(boolean canSpawn, float freq) {
		this.canSpawn = canSpawn;
		this.spawnFreq = freq;
	}
	
	public boolean isCanSpawn() {
		return canSpawn;
	}


}
