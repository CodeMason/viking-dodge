package com.jumpbuttonstudios.vikingdodge.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.gibbo.gameutil.box2d.Box2DObject;
import com.jumpbuttonstudios.vikingdodge.VikingDodgeRuntimeException;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

public abstract class Entity implements Updatable, EventTriggerer {

	/** Entity handler instance */
	protected EntityHandler entityHandler;

	/**
	 * All the EventListeners that this entity will notify when it does
	 * something
	 */
	protected Array<EventListener> listeners = new Array<EventListener>();

	/** The entities physics properties */
	protected Box2DObject physics;

	/** The entities sprite */
	protected Sprite sprite;

	/**
	 * Creates an entity and adds EventListeners
	 * 
	 * @param eventListeners
	 */
	public Entity(EntityHandler entityHandler, EventListener... eventListeners) {
		this.listeners.addAll(eventListeners);
		this.entityHandler = entityHandler;
	}

	/**
	 * Creates a new Box2DObject component for this entity, allows it to have
	 * physical properties
	 */
	public void createPhysicsComponent() {
		physics = new Box2DObject();
	}

	/** @return {@link #entityHandler} */
	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public void setCollisionFilters(int fixtureIndex, short category, short mask) {
		Filter filter = new Filter();
		filter.categoryBits = category;
		filter.maskBits = mask;
		physics.getBody().getFixtureList().get(fixtureIndex).setFilterData(filter);
	}
	public void setCollisionFilters(short category, short mask) {
		Filter filter = new Filter();
		filter.categoryBits = category;
		filter.maskBits = mask;
//		physics.getBody().getFixtureList().get(0).getFilterData().categoryBits = category;
//		physics.getBody().getFixtureList().get(0).getFilterData().maskBits = mask;
		setCollisionFilters(filter);
	}
	
	public void setCollisionFilters(Filter filter) {
		if (physics == null)
			throw new VikingDodgeRuntimeException(
					"Trying to set collision filters on a non phyical object!");
		for(Fixture fixture : physics.getBody().getFixtureList()){
			fixture.setFilterData(filter);
		}

	}

	/**
	 * Gets the physics component of this entity, will throw an exception if it
	 * is null
	 * 
	 * @return
	 */
	public Box2DObject getPhysics() {
		if (physics == null)
			throw new VikingDodgeRuntimeException(
					"Trying to get physics component on a non physical object");
		return physics;
	}

	/** @return {@link #sprite} */
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void notify(Entity entity, Event event) {
		for(EventListener l: listeners){
			l.onNotify(entity, event);
		}
	}

	@Override
	public void addEventListener(EventListener eventListener) {
		// To be overridden in subclass

	}

	@Override
	public void removeEventListener(EventListener eventListener) {
		// To be overridden in subclass

	}

}
