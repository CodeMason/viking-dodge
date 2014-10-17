package com.jumpbuttonstudios.vikingdodge.interfaces;

/**
 * Any object that implements this interface can be updated in the main render
 * thread
 * 
 * @author Gibbo
 * 
 */
public interface Updatable {

	/**
	 * Updates a given object each frame
	 * 
	 * @param delta
	 *            time passed since last frame
	 */
	void update(float delta);

}
