package com.jumpbuttonstudios.vikingdodge.ui.layouts;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.EventTriggerer;
import com.jumpbuttonstudios.vikingdodge.screens.MainMenuScreen;
import com.jumpbuttonstudios.vikingdodge.ui.HudTable;
import com.jumpbuttonstudios.vikingdodge.ui.popup.GameOverPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.LogoPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.PausePopup;

public class GameScreenLayout extends Layout implements EventTriggerer {

	/* A simple overlay to give the illusion we have not changes screens */
	Image overlay;
	/* If the overlay has moved up */
	boolean overlayUp;

	/* Logo popup that gives the illusion of no screen change */
	LogoPopup logoPopup;

	/* The in-game hud */
	HudTable hud;

	/* Table for the pause dialogue */
	PausePopup pausePopup;
	
	/* The panel that appears when the player has no lifes left */
	GameOverPopup gameOverPopup;
	

	public GameScreenLayout(final VikingDodge vikingDodge) {
		super(vikingDodge);
		vikingDodge.setCanUpdateLogic(false);
		

		/* Setup the overlay */
		overlay = new Image(Assets.skin.get("menu_bg",
				TextureRegionDrawable.class));
		
		/* Move the overlay up and then resume gameplay */
		overlay.addAction(Actions.sequence(Actions.moveTo(overlay.getX(),
				vikingDodge.getUi().getHeight(), 1, Interpolation.exp10),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						vikingDodge.setCanUpdateLogic(true);
						vikingDodge.getEntityHandler().getPlayer().respawn();
						hud.toFront();
					}
				})));

		logoPopup = new LogoPopup(vikingDodge, this);

		/* Add the player to list of listeners so we can send them events */
		addEventListener(vikingDodge.getEntityHandler());

		/* Create the hude */
		hud = new HudTable(vikingDodge, this);
		/* Create a new pause dialogue */
		pausePopup = new PausePopup(vikingDodge, this);
		
		/* Create the game over popup, this is off screen the entire time */
		gameOverPopup = new GameOverPopup(vikingDodge, this);
		
		setFillParent(true);
		debug();

	}

	public void returnToMenu() {
		addActor(logoPopup);
		logoPopup.moveIn(1);
		overlay.addAction(Actions.sequence(
				Actions.moveTo(0, 0, 1, Interpolation.bounceOut),
				Actions.run(new Runnable() {

					@Override
					public void run() {
						overlayUp = true;
					}
				})));
		overlay.toFront();
		logoPopup.toFront();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(overlayUp && logoPopup.isMovedIn()){
			overlayUp = false;
			vikingDodge.setScreen(new MainMenuScreen(vikingDodge));
		}
	}

	@Override
	public Table init() {
		addActor(hud);
		addActor(pausePopup);
		addActor(overlay);
		addActor(gameOverPopup);

		return this;
	}

	public Image getOverlay() {
		return overlay;
	}
	
	public GameOverPopup getGameOverPopup() {
		return gameOverPopup;
	}
	
	public PausePopup getPauseDialogueTable() {
		return pausePopup;
	}

	@Override
	public void clear() {

	}

	public HudTable getHud() {
		return hud;
	}

}
