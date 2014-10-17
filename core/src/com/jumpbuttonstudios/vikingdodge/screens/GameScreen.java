package com.jumpbuttonstudios.vikingdodge.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.gibbo.gameutil.camera.ActionOrthoCamera;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.effect.Effect;
import com.jumpbuttonstudios.vikingdodge.effect.SpeechBubble;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.entity.Mob;
import com.jumpbuttonstudios.vikingdodge.entity.environment.Environment;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.GameScreenLayout;

/**
 * The main screen for game-play
 * 
 * @author Gibbo
 * 
 */
public class GameScreen implements Screen, Drawable {

	/** Game instance */
	VikingDodge vikingDodge;

	/** If the GameScreen can run its update method */
	boolean canUpdate = true;

	/** Main view camera, used for Box2D physics */
	public static ActionOrthoCamera cam = new ActionOrthoCamera();
	/** Spritebatch for drawing non UI related objects */
	SpriteBatch batch = new SpriteBatch();

	/** The environment */
	Environment environment;

	/** Debugs Box2D objects */
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	public GameScreen(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;

		vikingDodge.stats.gamesPlayed++;

		/* We managed to get to this screen, must be a new attempt */
		vikingDodge.gameOver = false;

		/* Turn on camera shake */
		if (!cam.shakeEnabled())
			cam.enableShake(true);
		/* Set camera to 16:9 res */
		cam.setToOrtho(false, 16, 9);

		vikingDodge.setEntityHandler(new EntityHandler(vikingDodge));

		environment = new Environment(vikingDodge.getEntityHandler());

		/* Switch to GameScreen UI */
		vikingDodge.getUi().changeLayout(new GameScreenLayout(vikingDodge));

		cam.follow(cam.viewportWidth / 2, cam.viewportHeight / 2, 0, 0);
		cam.update();

	}

	@Override
	public void render(float delta) {
		/* Update game logic */
		if (vikingDodge.isCanUpdateLogic()) {
			vikingDodge.getEntityHandler().getFactory().getWorld()
					.step(1f / 60f, 5, 8);
			vikingDodge.getEntityHandler().update(delta);
			environment.update(delta);
		}
		vikingDodge.getUi().act(delta);

		cam.follow(cam.viewportWidth / 2, cam.viewportHeight / 2, 0, 0);
		cam.update();

		draw(batch);

	}

	@Override
	public void draw(SpriteBatch batch) {

		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		/* Draw the environment */
		environment.draw(batch);

		for (Effect effect : vikingDodge.getEntityHandler().getEffects()) {
			effect.draw(batch);
		}

		for (Mob mob : vikingDodge.getEntityHandler().getMobs()) {
			if (mob.getPhysics().getBody() != null)
				mob.draw(batch);
		}

		if (vikingDodge.getEntityHandler().sheep != null)
			vikingDodge.getEntityHandler().sheep.draw(batch);
		vikingDodge.getEntityHandler().getPlayer().draw(batch);
		
		for(SpeechBubble bubble : vikingDodge.getEntityHandler().getBubbles())
			bubble.draw(batch);

		batch.end();

		/* Draw all UI elements */
		vikingDodge.getUi().draw();

		/* Draw debug lines if debug enables */
		if (VikingDodge.DEBUG) {
			debugRenderer.render(vikingDodge.getEntityHandler().getFactory()
					.getWorld(), cam.combined);
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
