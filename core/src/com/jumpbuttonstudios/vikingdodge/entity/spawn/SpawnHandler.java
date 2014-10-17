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

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.Dragon;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.entity.Mob;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;
import com.jumpbuttonstudios.vikingdodge.interfaces.Spawn;

public class SpawnHandler implements EventTriggerer {

	/** Game instance */
	VikingDodge vikingDodge;

	/** If things can be spawned */
	private boolean canSpawn = false;
	
	/** Tis is the time that spawning was turned off */
	double time;
	
	/** How long the spawn handler waits before it can turn back on spawning */
	public double turnSpawningBackOnTime = TimeConversion.secondToNanos(4);

	/** Entity handler instance */
	EntityHandler entityHandler;

	/** The last time spawned some entity */
	private double lastRockSpawn = 0;

	/** The frequency this spawner can spawn an entity */
	private double spawnRockFreq = 0.65f;

	/** The last time spawned some entity */
	private double lastSheepSpawn = 0;

	/** The frequency this spawner can spawn an entity */
	private double spawnSheepFreq = 15;

	/** The last time a dragon was spawned */
	private double lastDragonSpawn = 0;

	/** The frequency a dragon can spawn */
	private double spawnDragonFreq = 75;

	/** All rock spawners */
	RockSpawner[] rockSpawners = new RockSpawner[16];
	/** All rock spawners */
	SheepSpawner[] sheepSpawners = new SheepSpawner[14];
	DragonSpawner[] dragonSpawners = new DragonSpawner[2];

	/** The spawner currently selected to spawn a mob/entity */
	Spawn<? extends Mob> selectedSpawner;

	public SpawnHandler(VikingDodge vikingDodge, EntityHandler entityHandler) {
		this.vikingDodge = vikingDodge;
		this.entityHandler = entityHandler;

		/* Fill the arrays with spawners */
		for (int x = 0; x < rockSpawners.length; x++) {
			rockSpawners[x] = new RockSpawner(entityHandler, x, 15);
		}
		for (int x = 0; x < sheepSpawners.length; x++) {
			sheepSpawners[x] = new SheepSpawner(entityHandler, x + 1, 15);
		}
		int x = -2;
		for (int index = 0; index < dragonSpawners.length; index++) {
			dragonSpawners[index] = new DragonSpawner(entityHandler, x, 7);
			x += 20;
		}
		
//		RockSpawner s = rockSpawners[5];
//		s.setCanSpawn(false, 1);
//		
//		selectedSpawner = s;
//		entityHandler.getMobs().add(selectedSpawner.spawn());

	}

	public void update(float delta) {
		lastRockSpawn += delta;
		lastSheepSpawn += delta;
		lastDragonSpawn += delta;
		
		/* Index and new spawn time for the given spawner */
		float nextSpawnTime;
		int index;
		/* Check if spawning is on before trying to spawn anything */
		if (canSpawn) {
			/*
			 * Check if we can spawn a rock, if so pick a random spawner and
			 * spawn it
			 */
			if (spawnRockFreqPassed()) {
				nextSpawnTime = MathUtils.random(1f, 1.5f);
				index = MathUtils.random(0, rockSpawners.length - 1);
				
				if(!rockSpawners[index].canSpawn) return;
				
				RockSpawner s = rockSpawners[index];
				s.setCanSpawn(false, nextSpawnTime);
				
				selectedSpawner = s;
				entityHandler.getMobs().add(selectedSpawner.spawn());
				setLastRockSpawn();
				spawnRockFreq = nextSpawnTime;
			}
			/*
			 * Check if we can spawn a sheep , if so pick a random spawner and
			 * spawn it
			 */
			if (!entityHandler.isSheepSpawned() && spawnSheepFreqPassed()) {
				
				nextSpawnTime = MathUtils.random(15f, 25f);
				index = MathUtils.random(0, sheepSpawners.length - 1);
				
				if(!sheepSpawners[index].canSpawn && !rockSpawners[index].canSpawn) return;
				
				SheepSpawner s = sheepSpawners[index];
				s.setCanSpawn(false, nextSpawnTime);
				
				selectedSpawner = s;
				entityHandler.setSheep(selectedSpawner.spawn());
				entityHandler.setSheepSpawned(true);
//				spawnSheepFreq = nextSpawnTime;
			}

			if (!entityHandler.isDragonSpawned() && spawnDragonFreqPassed()) {
				selectedSpawner = dragonSpawners[MathUtils.random(0, 1)];
				entityHandler.getMobs().add(selectedSpawner.spawn());
				entityHandler.setDragonSpawned(true);
				spawnDragonFreq = MathUtils.random(75f, 120f);
				notify(null, Event.DRAGON_SPAWN);
			}

		}
		
		/* Updates all the spawners */
		for(Spawner r : rockSpawners)
			r.update(delta);
		for(Spawner r : sheepSpawners)
			r.update(delta);
		for(Spawner r : dragonSpawners)
			r.update(delta);
		
		if(!canSpawn && (TimeUtils.nanoTime() - time > turnSpawningBackOnTime)){
			canSpawn = true;
		}
	}

	public void despawnDragon() {
		for (Mob mob : entityHandler.getMobs()) {
			if (mob instanceof Dragon) {
				Dragon dragon = (Dragon) mob;
				dragon.lifeTime = 0;
			}
		}
	}

	public void setLastSheepSpawn() {
		lastSheepSpawn = 0;
	}

	public void setLastRockSpawn() {
		lastRockSpawn = 0;
	}

	public void setLastDragonSpawn() {
		lastDragonSpawn = 0;
	}

	/**
	 * 
	 * @return true if the spawners last spawn was more than the spawn frequency
	 */
	public boolean spawnRockFreqPassed() {
		return lastRockSpawn > spawnRockFreq;
	}

	public boolean spawnSheepFreqPassed() {
		return lastSheepSpawn > spawnSheepFreq;
	}

	public boolean spawnDragonFreqPassed() {
		return lastDragonSpawn > spawnDragonFreq;
	}

	@Override
	public void notify(Entity entity, Event event) {
		vikingDodge.onNotify(entity, event);
	}
	
	public void setCanSpawn(boolean canSpawn, boolean useTimer){
		if(useTimer)
			time = TimeUtils.nanoTime();
		else
			this.canSpawn = canSpawn;
	}

	@Override
	public void addEventListener(EventListener eventListener) {
	}

	@Override
	public void removeEventListener(EventListener eventListener) {
		// TODO Auto-generated method stub

	}

}
