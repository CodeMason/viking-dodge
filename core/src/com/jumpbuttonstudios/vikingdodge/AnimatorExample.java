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

package com.jumpbuttonstudios.vikingdodge;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AnimatorExample {

	// All the textures we want to use, depending on what library you use this
	// will be different, e.g BufferedImage
	Texture[] keyFrames;
	// The actual frame we are on
	Texture currentFrame;
	// The current frame number we are on
	int currentFrameNumber;
	// This is the total time the animation has been in the current frame
	float stateTime;
	// The total time in milliseconds each frame lasts for
	float frameDuration;
	// The total duration of the entire animation, this can be used to check if
	// the animation is finished
	float animDuration;

	/**
	 * We basically state the animation duration and pass in an array of frames,
	 * from this data we can setup the rest of the class
	 * 
	 * @param frameDuration
	 * @param frames
	 */
	public AnimatorExample(float frameDuration, Texture... frames) {
		frames = new Texture[frames.length];
		for (int i = 0; i < frames.length; i++) {
			keyFrames[i] = frames[i];
		}
		currentFrameNumber = 0;
		currentFrame = keyFrames[currentFrameNumber];
		this.frameDuration = frameDuration;
		animDuration = frameDuration * keyFrames.length;
	}

	/**
	 * This method is a little specific to LibGDX but you might know what a
	 * sprite batch is anyway. In Java2D this would be Graphics2D I think, the
	 * delta time is just the time since the last frame.
	 * <p>
	 * 
	 * Usually you want to separate the updating from the drawing code but I put
	 * it in one place for convenience.
	 * <p>
	 * Now all you have to do is call this is a loop (which you have to anyway
	 * to get it to draw) and pass in the delta time, you can just pass 1f/60f
	 * if you don't have a loop that calculates delta
	 * 
	 * @param batch
	 * @param delta
	 */
	public void draw(SpriteBatch batch, float delta) {
		// Here we increase the state time so we can decide when to switch frame
		stateTime += delta;

		// Check if the state time is more than the frame duration
		if (stateTime > frameDuration) {
			// Make sure we stay within the bounds of the array, ideally we use
			// a
			// method for this but whatever
			if (currentFrameNumber != keyFrames.length) {
				// Increase the current frame number and set the current frame
				// to the correct one
				currentFrameNumber++;
				currentFrame = keyFrames[currentFrameNumber];
			} else {
				// The animation is finished, reset it. We can do something
				// different here if you want, like stop it, repeat etc. For now
				// I just repeat
				currentFrameNumber = 0;
				currentFrame = keyFrames[currentFrameNumber];
			}
			stateTime = 0;
		}
		// Just draw the current frame
		batch.draw(currentFrame, 50, 50);
	}

}
