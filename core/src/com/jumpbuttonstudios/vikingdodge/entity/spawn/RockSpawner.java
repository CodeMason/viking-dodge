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

import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.entity.Rock;
import com.jumpbuttonstudios.vikingdodge.interfaces.Spawn;

public class RockSpawner extends Spawner implements Spawn<Rock> {		

	public RockSpawner(EntityHandler entityHandler, float x, float y) {
		super(entityHandler, x, y, 5);
	}

	@Override
	public Rock spawn() {
		return new Rock(pos, entityHandler);
	}
	
	



}
