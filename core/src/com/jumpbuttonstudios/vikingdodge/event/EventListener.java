package com.jumpbuttonstudios.vikingdodge.event;

import com.jumpbuttonstudios.vikingdodge.entity.Entity;

/**
 * Any object that implements this interface can listen for in-game events, this
 * can be used to trigger sounds effects and animations/game state changes
 * 
 * @author Gibbo
 * 
 */
public interface EventListener {

	/**
	 * This method is called whenever an
	 * 
	 * @param entity
	 * @param event
	 */
	void onNotify(Entity entity, Event event);



}
