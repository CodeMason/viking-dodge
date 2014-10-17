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

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.Mob;

/**
 * Allows mobs to say things randomly
 * 
 * @author Gibbo
 * 
 */
public class SaySomething {

	/** The entity that is trying to say something */
	Mob parent;

	/** All the things that can be said by the given entity */
	HashMap<String, Texture> speechBubbles = new HashMap<String, Texture>();
	HashMap<Integer, String> indexes = new HashMap<Integer, String>();
	HashMap<Integer, Boolean> randomly = new HashMap<Integer, Boolean>();

	/** How often the entity says something */
	float freq;

	/** The last time an entity said something */
	float lastTimeSpoke;

	/** A random number we use to determine what to say, if anything */
	int random;

	/** The max time we can wait until we can say something else */
	float max;

	/** The min time we can wait until we say something else */
	float min;

	/**
	 * 
	 * @param parent
	 *            the entity saying something
	 * @param freq
	 *            how often something can be said
	 * @param min
	 *            the max amount of time in seconds that can be between speeches
	 * @param max
	 *            min amount of time in seconds that can be between speec
	 */
	public SaySomething(float freq, float min, float max) {
		this.freq = freq;
		this.max = max;
		this.min = min;
	}

	/**
	 * The default mix/max time between speeches is 15/25 by default, use
	 * {@link #SaySomething(Entity, float, float, float)} intead to specify a
	 * range
	 * 
	 * @param parent
	 *            the entity saying something
	 * @param freq
	 *            how often something can be said
	 */
	public SaySomething(float freq) {
		this(freq, 15, 25);
	}

	public void update(float delta) {
		lastTimeSpoke += delta;
		if (lastTimeSpoke > freq) {
			random = MathUtils.random(0, speechBubbles.size() * 2);
			if (random < speechBubbles.size())
				if (randomly.get(random).booleanValue())
					saySomething(
							new SpeechBubble(speechBubbles.get(indexes
									.get(random)), parent, 3), false, 0);
			randomizeNextSpeechTime();
		}
	}

	/**
	 * Makes the entity say something
	 * 
	 * @param speechBubble
	 *            the thing to say
	 * @param chanceBased
	 *            if the thing being said should be said based on chance
	 * @param chance
	 *            if chance based, this is set to whatever you want the chance
	 *            to be, a value of 1 would be 50/50, 2 would be 33/77 and so
	 *            on, use maths
	 */
	public void saySomething(SpeechBubble speechBubble, boolean chanceBased,
			int chance) {
		if (chanceBased) {
			random = MathUtils.random(0, chance);
			if (random == chance / 2)
				parent.setSpeechBubble(speechBubble);
		} else
			parent.setSpeechBubble(speechBubble);
		randomizeNextSpeechTime();
	}
	/**
	 * Makes the entity say something
	 * 
	 * @param speechBubble
	 *            the thing to say
	 * @param chanceBased
	 *            if the thing being said should be said based on chance
	 * @param chance
	 *            if chance based, this is set to whatever you want the chance
	 *            to be, a value of 1 would be 50/50, 2 would be 33/77 and so
	 *            on, use maths
	 */
	public void saySomething(String name, boolean chanceBased,
			int chance) {
		if (chanceBased) {
			random = MathUtils.random(0, chance);
			if (random == chance / 2)
				parent.setSpeechBubble(new SpeechBubble(speechBubbles.get(name), parent, 3));
		} else
			parent.setSpeechBubble(new SpeechBubble(speechBubbles.get(name), parent, 3));
		randomizeNextSpeechTime();
	}

	public void randomizeNextSpeechTime() {
		lastTimeSpoke = 0;
		freq = MathUtils.random(min, max);
	}

	/**
	 * Adds a speech bubble, this is the text texture that is inside the bubble
	 * itself
	 * 
	 * @param texture
	 */
	public void addSpeechBubble(String name, Texture texture,
			boolean canPlayRandomly) {
		speechBubbles.put(name, texture);
		indexes.put(speechBubbles.size() - 1, name);
		randomly.put(speechBubbles.size() - 1, canPlayRandomly);
	}

	public void setParent(Mob parent) {
		this.parent = parent;
	}

}
