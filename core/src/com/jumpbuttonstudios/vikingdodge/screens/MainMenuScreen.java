package com.jumpbuttonstudios.vikingdodge.screens;

import com.badlogic.gdx.Screen;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.MainMenuLayout;

public class MainMenuScreen implements Screen {

	/** Game instance */
	VikingDodge vikingDodge;
	
	public MainMenuScreen(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;
		
		/* Set the UI to main menu */
		vikingDodge.getUi().changeLayout(new MainMenuLayout(vikingDodge));
	}

	@Override
	public void render(float delta) {		
		
		vikingDodge.getUi().act(delta);
		vikingDodge.getUi().draw();

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
