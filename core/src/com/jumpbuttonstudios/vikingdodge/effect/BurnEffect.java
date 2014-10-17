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

package com.jumpbuttonstudios.vikingdodge.effect;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.vikingdodge.AnimationBuilder;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;

public class BurnEffect extends Effect {

	public BurnEffect(EntityHandler entityHandler, Vector2 pos) {
		super(entityHandler, new Vector2(pos.x, 2.5f));

		animator.addAnimation("burn", AnimationBuilder.create(
				Assets.EFFECT_GROUND_BURN, 60, 1, 1, null), PlayMode.NORMAL);
		animator.setScale(0.50f);

	}

}
