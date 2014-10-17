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
import com.jumpbuttonstudios.vikingdodge.entity.Mob;

public class RockDustEffect extends Effect {

	public RockDustEffect(EntityHandler entityHandler, Mob parent) {
		super(entityHandler, new Vector2(parent.getPhysics().getBody()
				.getPosition().x, parent.getPhysics().getBody().getPosition().y
				- (parent.getAnimator().getHeight() / 2 - 0.25f)));

		animator.addAnimation("dust", AnimationBuilder.create(
				Assets.EFFECT_ROCK_DUST, 0.05f, 3, 2, null), PlayMode.NORMAL);
		animator.setScale(0.75f);

	}

	@Override
	public void update(float delta) {
		if (animator.isFinished())
			canDelete = true;
	}

}
