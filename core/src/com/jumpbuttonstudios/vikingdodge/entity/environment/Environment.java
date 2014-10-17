package com.jumpbuttonstudios.vikingdodge.entity.environment;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.entity.EntityHandler;
import com.jumpbuttonstudios.vikingdodge.interfaces.Drawable;
import com.jumpbuttonstudios.vikingdodge.interfaces.Updatable;

/**
 * The environment draws the ground, backdrop and draws/simulates the cloud
 * scrolling in the background.
 * 
 * @author Gibbo
 * 
 */
public class Environment implements Drawable, Updatable {

	/** The ground the player will walk on */
	Ground ground;

	/* The left and right side borders */
	Border rightBorder;
	Border leftBorder;

	/** All the cliffs in the world */
	Array<Cliff> cliffs = new Array<Cliff>();

	/** All the clouds */
	Array<Cloud> clouds = new Array<Cloud>();

	/** The background */
	Sprite background = new Sprite(Assets.get(Assets.BG));

	/** The fox */
	Sprite fog = new Sprite(Assets.get(Assets.FOG));

	public Environment(EntityHandler entityHandler) {
		ground = new Ground(entityHandler);
		rightBorder = new Border(0, VikingDodge.HEIGHT / 2, entityHandler);
		leftBorder = new Border(VikingDodge.WIDTH, VikingDodge.HEIGHT / 2,
				entityHandler);
		background.setSize((background.getWidth() * VikingDodge.SCALE) * 2,
				(background.getHeight() * VikingDodge.SCALE) * 2);
		fog.setSize((fog.getWidth() * VikingDodge.SCALE) * 2,
				(fog.getWidth() * VikingDodge.SCALE));
		
		

		/* Create all the cliffs and sort them */
		createCliffs(entityHandler);

		/* Create clouds */
		createClouds();

	}

	@Override
	public void update(float delta) {
		/* Moves clouds across screen */
		for (Cloud cloud : clouds) {
			cloud.update(delta);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {

		background.setPosition(0, 0);
		background.draw(batch);

		/* Draw all cliffs */
		for (Cliff cliff : cliffs) {
			cliff.draw(batch);
		}

		fog.setPosition(0, 0);
		fog.draw(batch);

		for (Cloud cloud : clouds) {
			cloud.draw(batch);
		}

		ground.draw(batch);

	}

	public void createClouds() {
		float y = 3;
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD1), -2, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD1), 0, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD2), 2, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD3), 4, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD2), 6, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD3), 8, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD4), 10, y));
		clouds.add(new Cloud(Assets.get(Assets.BG_CLOUD4), 12, y));
		clouds.shuffle();
	}

	/** Adds a bunch of cliffs to the background at set positions */
	public void createCliffs(EntityHandler entityHandler) {
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK2), 0.2f, 1,
				entityHandler));
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK4),
				VikingDodge.WIDTH / 3.5f, 1, entityHandler));
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK6),
				VikingDodge.WIDTH / 1.25f, 1, entityHandler));
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK1), VikingDodge.WIDTH
				- VikingDodge.WIDTH / 2.35f, 1, entityHandler));
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK3),
				VikingDodge.WIDTH / 2 - 1.75f, 1, entityHandler));
		cliffs.add(new Cliff(Assets.get(Assets.BG_ROCK5), 2.1f, 1,
				entityHandler));

		/* Set cliffs to proper scale */
		for (Cliff cliff : cliffs) {
			cliff.getSprite().setSize(
					cliff.getSprite().getWidth() * VikingDodge.SCALE,
					cliff.getSprite().getHeight() * VikingDodge.SCALE);
		}

	}

}
