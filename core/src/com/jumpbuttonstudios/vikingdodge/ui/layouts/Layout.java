package com.jumpbuttonstudios.vikingdodge.ui.layouts;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;
import com.jumpbuttonstudios.vikingdodge.ui.UI;

public abstract class Layout extends Table implements EventTriggerer {

	/** All the listeners waiting for events */
	Array<EventListener> listeners = new Array<EventListener>();

	/** Instance of the UI so we can send events */
	protected UI ui;

	/** Game instance */
	protected VikingDodge vikingDodge;

	public Layout(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;
	}

	/**
	 * Adds all actors the the layout
	 * 
	 */
	public abstract Table init();

	/** Clears all actors from this layout */
	public abstract void clear();

	@Override
	public void notify(Entity entity, Event event) {
			ui.notify(entity, event);
	}

	@Override
	public void addEventListener(EventListener eventListener) {
		this.listeners.add(eventListener);
	}

	@Override
	public void removeEventListener(EventListener eventListener) {
		this.listeners.removeValue(eventListener, true);

	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

}
