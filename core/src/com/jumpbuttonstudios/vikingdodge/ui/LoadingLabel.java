package com.jumpbuttonstudios.vikingdodge.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.StringBuilder;
import com.jumpbuttonstudios.vikingdodge.screens.LoadingScreen;

/**
 * A label that updates the text on the fly
 * 
 * @author Gibbo
 * 
 */
public class LoadingLabel extends Label {

	public LoadingLabel(CharSequence text, LabelStyle style) {
		super(text, style);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		setText(new StringBuilder(String.valueOf((int) LoadingScreen.percentageLoaded) + "%"));
	}

}
