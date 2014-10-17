package com.jumpbuttonstudios.vikingdodge.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.LoadingScreenLayout;

public class UI extends Stage implements EventTriggerer{
	
	/** Instance of our game */
	VikingDodge vikingDodge;

	/** The current UI layout */
	Layout currentLayout;

	/** UI layout for the loading screen */
	LoadingScreenLayout loadingScreenLayout;
	

	public UI(Viewport viewport, VikingDodge vikingDodge) {
		super(viewport);
		this.vikingDodge = vikingDodge;
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw() {
		super.draw();

		/* Draw table debug lines if need be */
		if (VikingDodge.DEBUG){
			currentLayout.debug();
			Table.drawDebug(this);
		}

	}
	
	@Override
	public void notify(Entity entity, Event event) {
		vikingDodge.onNotify(entity, event);
	}

	/**
	 * Create all the User Interface layouts
	 */
	public void loadLayouts() {
		loadingScreenLayout = new LoadingScreenLayout(vikingDodge);
	}

	/** Changes the UI layout */
	public void changeLayout(Layout layout) {
		layout.setUi(this);
		clear();
		this.currentLayout = layout;
		addActor(this.currentLayout.init());
	}

	/**
	 * This will fade the stages actors and all of their children to 0 alpha
	 * 
	 * @return true when alpha reaches 0
	 */
	public boolean fadeOutAll(float fadeAmount) {
		for (Actor actor : getActors()) {
			if (actor.getColor().a - fadeAmount * (1f / 60f) > 0) {
				float alpha = actor.getColor().a - fadeAmount * (1f / 60f);
				actor.setColor(actor.getColor().r, actor.getColor().g,
						actor.getColor().b, alpha);
			} else {
				return true;

			}
			if (actor.equals(getActors().peek())) {
				System.out.println(actor.getColor().a);
				if (actor.getColor().a <= 0)
					return true;
			}
		}

		return false;
	}

	/** @return {@link #currentLayout} */
	public Layout getCurrentLayout() {
		return currentLayout;
	}

	/** @return {@link #loadingScreenUI} */
	public LoadingScreenLayout getLoadingScreenLayout() {
		if (loadingScreenLayout == null){
			this.loadingScreenLayout = new LoadingScreenLayout(vikingDodge);
			return loadingScreenLayout;
		}
		return loadingScreenLayout;
	}
	
	@Override
	public void addEventListener(EventListener eventListener) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void removeEventListener(EventListener eventListener) {
		// TODO Auto-generated method stub
		
	}


}
