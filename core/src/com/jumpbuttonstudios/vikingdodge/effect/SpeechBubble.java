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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.Mob;

/**
 * A speech bubble that appears above mobs
 * 
 * @author Gibbo
 * 
 */
public class SpeechBubble extends Sprite {

	/* The parent that owns this speech bubble */
	Mob parent;
	/* The text that will be displayed in this speech bubble */
	Sprite text;
	/* The amount we scale by when sizing the bubble up */
	float scaleAmount = 6f;
	/* The percentage we scale by */
	float percentage = 1 / 60f;
	/* The max scale */
	float maxScale = 1;
	/* If this speech bubble can scale down and be removed */
	boolean canRemove = false;

	/* How long this speech bubble as been present on the screen */
	float timePresent;
	/* The max amount of time this speech bubble can stay on the screen */
	float maxTime;

	public SpeechBubble(Texture texture, Mob parent, float maxTime) {
		this(texture);
		text = new Sprite(texture);
		this.parent = parent;
		this.maxTime = maxTime;
		setScale(0);
		setSize(getWidth() * VikingDodge.SCALE, getHeight() * VikingDodge.SCALE);
		setOrigin(getWidth() / 2, getHeight() / 2);
		text.setSize(text.getWidth() * VikingDodge.SCALE, text.getHeight()
				* VikingDodge.SCALE);
	}

	private SpeechBubble(Texture texture) {
		super(Assets.get(Assets.SPEECHBUBBLE_BUBBLE));
	}

	public void update(float delta) {
		timePresent += delta;
	}

	@Override
	public void draw(Batch batch) {
		if (parent != null) {

			if (parent.isFacingLeft() && !isFlipX())
				flip(true, false);
			else if (!parent.isFacingLeft() && isFlipX())
				flip(true, false);
			setPosition(parent.getPhysics().getBody().getPosition().x
					- (getWidth() / 2), parent.getPhysics().getBody()
					.getPosition().y
					+ parent.getAnimator().getHeight() / 2);
		}
		text.setPosition(getX(), getY());
		text.setScale(getScaleX(), getScaleY());
		text.setOrigin(getOriginX(), getOriginY());

		if (timePresent < maxTime)
			if (getScaleX() + scaleAmount * percentage < maxScale)
				setScale(getScaleX() + scaleAmount * percentage, getScaleY()
						+ scaleAmount * percentage);
			else {
				setScale(1);
			}
		else {
			if (getScaleX() - scaleAmount * percentage > 0)
				setScale(getScaleX() - scaleAmount * percentage, getScaleY()
						- scaleAmount * percentage);
			else {
				setScale(0);
				canRemove = true;
			}
		}

		super.draw(batch);
		text.draw(batch);

	}

	/** Instantly makes this speech bubble scale down */
	public void setToScaleDownNow() {
		timePresent = 10000;
	}

	public boolean canRemove() {
		return canRemove;
	}
	
	public Mob getParent() {
		return parent;
	}

}
