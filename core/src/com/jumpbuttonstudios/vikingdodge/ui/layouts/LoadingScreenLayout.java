package com.jumpbuttonstudios.vikingdodge.ui.layouts;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Interpolation.ElasticOut;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.screens.LoadingScreen;
import com.jumpbuttonstudios.vikingdodge.ui.LoadingLabel;

public class LoadingScreenLayout extends Layout {

	MoveToAction moveLogo;
	MoveToAction moveLoadingText;

	/** The background */
	Image background;
	/** Logo */
	Image logo;
	/** The Logo table */
	Table logoTable = new Table();

	/* Table for the loading bar */
	Table loadingBarTable = new Table();
	/** The loading text label */
	Label loadingLabel;
	/** The loading text label */
	LoadingLabel loadingprogressLabel;

	public boolean actorsMovingOut = false;

	public LoadingScreenLayout(VikingDodge vikingDodge) {
		super(vikingDodge);
		background = new Image(Assets.skin.get("menu_bg", TextureRegionDrawable.class));
		logo = new Image(Assets.skin.get("logo", TextureRegionDrawable.class));
		loadingLabel = new Label("LOADING...", Assets.skin.get("label",
				LabelStyle.class));
		loadingprogressLabel = new LoadingLabel("0%", Assets.skin.get("label",
				LabelStyle.class));

		moveLogo = new MoveToAction();
		moveLoadingText = new MoveToAction();

		ElasticOut out = new ElasticOut(11, 8);
		
		moveLogo.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (logo.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2f);
		moveLogo.setInterpolation(out);
		moveLogo.setDuration(1.5f);

		logoTable.addActor(logo);
		logoTable.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (logo.getWidth() / 2),
				1000);

		loadingBarTable.add(loadingLabel);
		loadingBarTable.add(loadingprogressLabel);
		loadingBarTable.setPosition(vikingDodge.getUi().getWidth() / 2
				- (loadingBarTable.getWidth() / 2), -50);

		moveLoadingText.setPosition(vikingDodge.getUi().getWidth() / 2
				- (loadingBarTable.getWidth() / 2), vikingDodge.getUi()
				.getHeight() / 3f);
		moveLoadingText.setInterpolation(out);
		moveLoadingText.setDuration(1.5f);

		moveIn();

		background.setFillParent(true);
		setFillParent(true);

		debug();
	}

	/** Adds the actors to the table */
	@Override
	public Table init() {
		addActor(background);
		addActor(logoTable);
		addActor(loadingBarTable);
		return this;

	}

	public void moveIn() {
		loadingBarTable.addAction(moveLoadingText);
		logoTable.addAction(Actions.sequence(moveLogo, Actions.run(new Runnable() {
			
			@Override
			public void run() {
				((LoadingScreen)vikingDodge.getScreen()).canLoad = true;
			}
		})));
	}

	public void addMoveOutAction() {
		moveLoadingText = new MoveToAction();
		moveLoadingText.setPosition(loadingBarTable.getX(), -700);
		moveLoadingText.setInterpolation(Interpolation.pow5);
		moveLoadingText.setDuration(1.5f);
		loadingBarTable.addAction(moveLoadingText);
		actorsMovingOut = true;

	}

	public boolean movedOut() {
		if (loadingBarTable.getActions().size == 0)
			return true;
		return false;
	}

	@Override
	public void clear() {

	}

}
