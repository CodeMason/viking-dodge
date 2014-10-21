package com.jumpbuttonstudios.vikingdodge.ui.popup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.interfaces.AdListener;
import com.jumpbuttonstudios.vikingdodge.screens.GameScreen;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.GameScreenLayout;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.Layout;

public class PausePopup extends Popup {

	/* The Play/Pause, redo and exit to main menu buttons */
	Button playPauseButton;
	Button redo;
	Button mainmenu;
	Button sound;

	/* The avatar of player */
	PlayerAvatarPopup playerAvatarPopup;

	/* When the game is not paused, we draw this */
	ButtonStyle pause;
	/* When the game is paused, we draw this */
	ButtonStyle resume;
	ButtonStyle soundOn;
	ButtonStyle soundOff;
	


	public PausePopup(VikingDodge vikingDodge, Layout parent) {
		super(vikingDodge, parent, new Image(new TextureRegion(
				Assets.get(Assets.UI_WINDOW_PAUSE_OPEN))), 1, false, false);
		moveOut(1, false);

	}

	@Override
	public void create() {
		setTouchable(Touchable.childrenOnly);
		setScreenPositions(
				vikingDodge.getUi().getWidth() / 2
						- (background.getWidth() / 2),
				-background.getHeight() / 1.9f, vikingDodge.getUi().getWidth()
						/ 2 - (background.getWidth() / 2), 0);
		pause = Assets.skin.get("pauseButton", ButtonStyle.class);
		resume = Assets.skin.get("resumeButton", ButtonStyle.class);
		soundOn = Assets.skin.get("soundOnButton", ButtonStyle.class);
		soundOff = Assets.skin.get("soundOffButton", ButtonStyle.class);

		playerAvatarPopup = new PlayerAvatarPopup(vikingDodge, parent, false,
				false);

		playPauseButton = new Button(pause);
		redo = new Button(Assets.skin.get("redoButton", ButtonStyle.class));
		mainmenu = new Button(Assets.skin.get("exitButton", ButtonStyle.class));
		sound = new Button(vikingDodge.getSoundManager().isSoundDisabled() ? soundOff : soundOn);

		playPauseButton.setPosition(background.getWidth() / 2
				- (playPauseButton.getWidth() / 2),
				(background.getHeight() / 2) + 25);
		redo.setPosition(
				(background.getWidth() / 2 - (redo.getWidth() / 2)) - 130,
				background.getHeight() / 2 - (redo.getWidth() / 1.25f) - 20);
		mainmenu.setPosition(
				(background.getWidth() / 2 - (mainmenu.getWidth() / 2)) + 130,
				background.getHeight() / 2 - (mainmenu.getWidth() / 1.25f) - 20);
		sound.setPosition(background.getWidth() - (sound.getWidth() * 2),
		background.getHeight() / 2 - (mainmenu.getWidth() / 1.25f) - 20);
		playerAvatarPopup.setPosition(20, 10);
	}

	@Override
	public void addActors() {
		addActor(background);
		addActor(playPauseButton);
		addActor(redo);
		addActor(mainmenu);
		addActor(playerAvatarPopup);
		addActor(sound);

	}

	@Override
	public void registerListeners() {
		final GameScreenLayout gs = (GameScreenLayout) parent;
		playPauseButton.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int pointer,
					int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				if (vikingDodge.isCanUpdateLogic() && isMovedOut()) {
					parent.notify(null, Event.PAUSED);
					playPauseButton.setStyle(resume);
					createOverlay(true);
					overlay.clearListeners();
					toFront();
					moveIn(1);
					VikingDodge.adListener.requestAd(AdListener.TOP);
				} else if (isMovedIn()) {
					playPauseButton.setStyle(pause);
					moveOut(1, false);
					VikingDodge.adListener.closeAd(AdListener.REMOVE_TOP);
				}
			}

		});

		redo.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				gs.getOverlay().toFront();
				moveOut(1, false);
				playPauseButton.setStyle(pause);
				VikingDodge.adListener.closeAd(AdListener.REMOVE_TOP);
				
				gs.getOverlay().addAction(
						Actions.sequence(Actions.moveTo(0, 0, 1,
								Interpolation.bounceOut), Actions
								.run(new Runnable() {
									@Override
									public void run() {
										vikingDodge.setScreen(new GameScreen(vikingDodge));
									}
								}), Actions.moveTo(0, gs.getOverlay()
								.getHeight(), 1, Interpolation.exp10)));
				return false;
			}

		});

		mainmenu.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				VikingDodge.adListener.closeAd(AdListener.REMOVE_TOP);
				VikingDodge.adListener.requestAd(AdListener.BOTTOM);
				parent.notify(null, Event.BUTTON_PRESSED);
				fadeOverlay();
				moveOut(1, false);
				parent.notify(null, Event.CHANGED_TO_MENU);
				gs.returnToMenu();
				
				return false;
			}

		});
		
		sound.addListener(new ClickListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				parent.notify(null, Event.BUTTON_PRESSED);
				parent.notify(null, Event.SOUND_STATE_CHANGE);
				sound.setStyle(sound.getStyle().equals(soundOn) ? soundOff : soundOn);
				return false;
			}
			
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isMovedOut() && !vikingDodge.isCanUpdateLogic()) {
			parent.notify(null, Event.RESUMED);
			((GameScreenLayout) parent).getHud().toFront();
		}
	}

}
