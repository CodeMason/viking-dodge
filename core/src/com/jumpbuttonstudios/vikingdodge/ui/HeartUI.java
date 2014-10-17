package com.jumpbuttonstudios.vikingdodge.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class HeartUI extends Image {

	/* TODO Hacky, move somewhere later */
	public static int lifes;

	Layout parent;

	/** If this heart is full */
	boolean isFull = true;

	/** Displayed if the heart is full */
	static TextureRegionDrawable full = new TextureRegionDrawable(
			new TextureRegion(Assets.get(Assets.UI_HUD_HEART_FULL)));

	/** Displayed if the heart is empty */
	TextureRegionDrawable empty = new TextureRegionDrawable(new TextureRegion(
			Assets.get(Assets.UI_HUD_HEART_EMPTY)));

	public HeartUI(Layout parent) {
		setDrawable(full);
		this.parent = parent;
		lifes = 3;
	}

	/** Sets this heart to empty */
	public void hurt() {
		setDrawable(empty);
		isFull = false;
		lifes--;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	}

	/** @return {@link #isFull} */
	public boolean isFull() {
		return isFull;
	}

}
