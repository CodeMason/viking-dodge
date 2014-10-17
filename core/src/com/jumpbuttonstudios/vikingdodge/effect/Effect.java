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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.gibbo.gameutil.time.TimeConversion;
import com.jumpbuttonstudios.vikingdodge.Animator;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

public class Effect implements Updatable, Drawable {

	EntityHandler entityHandler;

	protected Animator animator = new Animator();
	Vector2 pos;

	double createTime;
	double fadeTime = TimeConversion.secondToNanos(10);

	public boolean canDelete = false;

	public Effect(EntityHandler entityHandler, Vector2 pos) {
		this.entityHandler = entityHandler;
		this.pos = pos;
		createTime = TimeUtils.nanoTime();

	}

	@Override
	public void update(float delta) {
		if (TimeUtils.nanoTime() - createTime > fadeTime) {
			if (fade()) {
				canDelete = true;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		animator.setOrigin(animator.getWidth() / 2, animator.getHeight() / 2);
		animator.setPosition(pos.x - (animator.getWidth() / 2), pos.y
				- (animator.getHeight() / 2));
		animator.draw(batch);
	}

	public boolean fade() {
		if (animator.getColor().a - 0.05f > 0) {
			animator.setColor(animator.getColor().r, animator.getColor().g,
					animator.getColor().b, animator.getColor().a - 0.05f);
		} else {
			return true;
		}
		return false;
	}

}
