package com.jumpbuttonstudios.vikingdodge.event;

import com.jumpbuttonstudios.vikingdodge.entity.Entity;

public interface EventTriggerer {

	/**
	 * Notify all the {@link EventListener}s that something has happened
	 * 
	 * @param entity
	 * @param event
	 */
	void notify(Entity entity, Event event);
	
	/**
	 * Add a given {@link EventListener} to an array
	 * 
	 * @param eventTriggerer
	 */
	void addEventListener(EventListener eventListener);

	/**
	 * Remove a given {@link EventListener} to an array
	 * 
	 * @param eventTriggerer
	 */
	void removeEventListener(EventListener eventListener);

}
