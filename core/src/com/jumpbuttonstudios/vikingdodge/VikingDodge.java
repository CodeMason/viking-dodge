package com.jumpbuttonstudios.vikingdodge;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;
import com.jumpbuttonstudios.vikingdodge.interfaces.AdListener;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.screens.GameScreen;
import com.jumpbuttonstudios.vikingdodge.screens.LoadingScreen;
import com.jumpbuttonstudios.vikingdodge.ui.UI;

public class VikingDodge extends Game implements EventListener {

	/* The type of application we are dealing with */
	public static ApplicationType type;
	/* The games ID */
	public static final int GAME_ID = 8;
	/* The authentication key */
	public static final String AUTHENTICATION_KEY = "m72od5evppzDV4equwhq";
	/* The devs id */
	public static final String DEV_ID = "jimmt";
	
	public static AdListener adListener = new AdListener() {
		
		@Override
		public void requestAd(int adID) {
			
		}
		
		@Override
		public void loadFullScreen() {
			
		}
		
		@Override
		public boolean isFullScreenAdLoaded() {
			return false;
		}
		
		@Override
		public void closeAd(int adID) {
			
		}
	};
	
	/* used to serialize stats */
	Json json = new Json(OutputType.json);
	
	/* Holds all the player stats */
	public StatsData stats = new StatsData();

	/** If the games logic can update */
	boolean canUpdateLogic = true;

	/** If the game is currently over */
	public boolean gameOver = false;

	/** If the game should be in debug mode */
	public static boolean DEBUG = false;

	/**
	 * The scale of the game, translating screen coordinates to world
	 * coordinates
	 */
	public static final float SCALE = 1f / 100f;

	/* World dimensions */
	public static final float WIDTH = 16;
	public static final float HEIGHT = 9;

	/** If the game is currently resetting */
	public boolean isResetting = false;

	/** Handles entities */
	EntityHandler entityHandler;

	/** Handles sounds */
	SoundManager soundManager;

	/** The user interface component */
	UI ui;

	/** For tweein user interface elements */
	TweenManager tweenManager;

	@Override
	public void create() {

		/* Try to load stats from disc */
		if(Gdx.files.external("VikingDodge/stats.jbs").exists())
			load();
		
		/* Connect to the JBS network */
		Network.connect();

		/* Create the new sound manager instance */
		soundManager = new SoundManager(this);

		/* Create a UI instance */
		ui = new UI(new FitViewport(1280, 720), this);

		/* Create the tween manager instance */
		tweenManager = new TweenManager();

		/* Setup the input to come from the ui */
		Gdx.input.setInputProcessor(getUi());

		/*
		 * If we are in debug mode, turn on the logger and setup application
		 * debugging mode
		 */
		if (DEBUG) {
			Gdx.app.setLogLevel(Logger.DEBUG);
			Assets.manager.getLogger().setLevel(Logger.INFO);
		}
		
		/* Load the bottom ad */
		adListener.requestAd(AdListener.BOTTOM);

		/* Switch to loading screen */
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		soundManager.update(Gdx.graphics.getDeltaTime());

	}
	
	/* Loads the player stats from file */
	public void load(){
		String temp = Base64Coder.decodeString(Gdx.files.external(
				"VikingDodge/stats.jbs").readString());
		stats = json.fromJson(StatsData.class, temp);
		Log.info("Stats", "Stats Loaded!");
	}
	
	/* Writes the players stats to file */
	public void save(){
		Gdx.files.external("VikingDodge/stats.jbs").writeString(
				Base64Coder.encodeString(json.toJson(stats)), false);
		Log.info("Stats", "Stats Saved!");
	}

	@Override
	public void onNotify(Entity entity, Event event) {
		switch (event) {
		case DRAGON_SPAWN:
			GameScreen.cam.startShake(0.5f, 3, 0.75f);
			stats.dragonsSpawned++;
			break;
		case PAUSED:
			canUpdateLogic = false;
			break;
		case RESUMED:
			canUpdateLogic = true;
			break;
		case GAMEOVER:
			if (Network.isConnected() && !Network.isScoreSubmited()) {
				Network.submitHighscore(entityHandler.getPlayer().getScore());
				entityHandler.getSpawnHandler().setCanSpawn(false, false);
				stats.gamesPlayed++;
				save();
			}
			break;
		default:
		}
		
		if (entityHandler != null)
			entityHandler.onNotify(entity, event);
		soundManager.onNotify(entity, event);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		getUi().getViewport().update(width, height);
	}

	@Override
	public void pause() {
		super.pause();
		save();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}


	public void setEntityHandler(EntityHandler entityHandler) {
		this.entityHandler = entityHandler;
	}

	/** @return {@link #entityHandler} */
	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setCanUpdateLogic(boolean canUpdateLogic) {
		this.canUpdateLogic = canUpdateLogic;
	}

	public boolean isCanUpdateLogic() {
		return canUpdateLogic;
	}

	/** @return {@link #ui} */
	public UI getUi() {
		return ui;
	}

	public TweenManager getTweenManager() {
		return tweenManager;
	}
	
	public static void setAdListener(AdListener adListener) {
		VikingDodge.adListener = adListener;
	}

}
