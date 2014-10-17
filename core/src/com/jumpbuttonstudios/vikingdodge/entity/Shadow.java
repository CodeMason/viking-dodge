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

package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;

/**
 * Shadow class for entities, all shadows are cast at the same Y coordinate on
 * the ground
 * 
 * @author Gibbo
 * 
 */
public class Shadow extends Sprite {

	protected Entity parent;
	Vector2 pos = new Vector2();

	public Shadow(Entity parent, Texture texture) {
		this(texture);
		this.parent = parent;
	}
	
	public Shadow(Texture texture) {
		super(texture);
		setSize(getWidth() * VikingDodge.SCALE, getHeight() * VikingDodge.SCALE);
		pos.set(pos.x, 2.5f);
	}
	
	public void setParent(Entity parent) {
		this.parent = parent;
	}

	public void setPosition(float x) {
		super.setPosition(parent.getPhysics().getBody().getPosition().x + x - (getWidth() / 2), pos.y);
	}

}
