package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * 
 * @author Gibbo
 * 
 */
public abstract class EntityAction<T> {

	/** The last time this action was performed */
	protected double lastExecution;

	/** How often this action can be executed */
	protected double executionFreq = 0;

	/** The entity this action belongs to */
	T entity;

	/**
	 * 
	 * @param entity
	 *            entity this action belongs to
	 */
	public EntityAction(T entity) {
		this(entity, 0);
	}

	/**
	 * 
	 * @param entity
	 *            entity this action belongs to
	 * @param executionFreq
	 *            how often this action can occur
	 */
	public EntityAction(T entity, double executionFreq) {
		this.entity = entity;
		this.executionFreq = executionFreq;
	}

	/** Executes this action */
	public abstract void execute();
	
	/** Sets the time of the last exectution */
	public void setLastExecution() {
		this.lastExecution = TimeUtils.nanoTime();
	}

	/**
	 * Checks if this action can be executed again by comparing the last time it
	 * occured to the current time
	 * 
	 * @return
	 */
	public boolean canExecute() {
		return (TimeUtils.nanoTime() - lastExecution > executionFreq);
	}

	/** @return {@link #entity} */
	public T getEntity() {
		return entity;
	}

}
