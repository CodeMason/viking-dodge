package com.jumpbuttonstudios.vikingdodge.ui.layouts;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.interfaces.AdListener;
import com.jumpbuttonstudios.vikingdodge.network.Network;
import com.jumpbuttonstudios.vikingdodge.screens.GameScreen;
import com.jumpbuttonstudios.vikingdodge.ui.popup.FacebookOrJBSPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.HighscoresPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.PlayerAvatarPopup;
import com.jumpbuttonstudios.vikingdodge.ui.popup.TutorialPopup;

public class MainMenuLayout extends Layout {

	MoveToAction moveLogo;
	MoveToAction moveButtons;

	/** The background */
	Image background;

	TutorialPopup tutorialPopup;

	/* The area where the players avatar and name are displayed */
	PlayerAvatarPopup profile;

	/** Logo */
	Image logo;
	/* Table to place the logo */
	Table logoTable = new Table();

	Table buttons = new Table();
	public Button play;
	public Button highscores;
	private Button login;
	Button sound;
	Button howToPlay;

	ButtonStyle soundOn;
	ButtonStyle soundOff;

	boolean canChangeScreen = false;

	Layout layout;

	FacebookOrJBSPopup facebookOrJBSPopup;

	public MainMenuLayout(final VikingDodge vikingDodge) {
		super(vikingDodge);
		profile = new PlayerAvatarPopup(vikingDodge, this);

		layout = this;

		soundOn = Assets.skin.get("soundOnButton", ButtonStyle.class);
		soundOff = Assets.skin.get("soundOffButton", ButtonStyle.class);

		background = new Image(Assets.skin.get("menu_bg",
				TextureRegionDrawable.class));
		logo = new Image(Assets.skin.get("logo", TextureRegionDrawable.class));
		sound = new Button(vikingDodge.getSoundManager().isSoundDisabled() ? soundOff : soundOn);

		moveLogo = new MoveToAction();
		moveButtons = new MoveToAction();

		moveLogo.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (logo.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2f);
		moveLogo.setInterpolation(Interpolation.elastic);
		moveLogo.setDuration(3);

		highscores = new Button(
				Assets.skin.get("highscores", ButtonStyle.class));
		play = new Button(Assets.skin.get("play", ButtonStyle.class));
		login = new Button(Assets.skin.get("login", ButtonStyle.class));
		facebookOrJBSPopup = new FacebookOrJBSPopup(vikingDodge, this);
		howToPlay = new Button(
				Assets.skin.get("howToButton", ButtonStyle.class));

		tutorialPopup = new TutorialPopup(vikingDodge, layout);

		setFillParent(true);
		logoTable.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (logo.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 2f);
		logoTable.addAction(moveLogo);

		buttons.add(howToPlay).padRight(150).padTop(170);
		buttons.add(highscores).padTop(70).padRight(20);
		buttons.add(play);
		buttons.add(login).padTop(70).padLeft(20);
		buttons.add(sound).padLeft(150).padTop(170);

		buttons.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (buttons.getWidth() / 2),
				-750);
		moveButtons.setPosition(
				vikingDodge.getUi().getWidth() / 2 - (buttons.getWidth() / 2),
				vikingDodge.getUi().getHeight() / 3.5f);
		moveButtons.setDuration(1f);
		moveButtons.setInterpolation(Interpolation.exp10Out);
		buttons.addAction(moveButtons);

		profile.setPosition(profile.getOffScreenPos().x,
				profile.getOffScreenPos().y);
		profile.addAction(Actions.moveTo(profile.getOnScreenPos().x,
				profile.getOnScreenPos().y, 1, Interpolation.exp10Out));

		sound.setPosition(background.getWidth() - (sound.getWidth() * 2),
				background.getHeight() / 2
						- (sound.getWidth() / 2 + (background.getHeight() / 3)));


		debug();

		play.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				layout.notify(null, Event.BUTTON_PRESSED);
				moveLogoAndButtons(new Runnable() {

					@Override
					public void run() {
						vikingDodge.onNotify(null, Event.CHANGED_TO_GAMESCREEN);
						VikingDodge.adListener.closeAd(AdListener.REMOVE_BOTTOM);
						vikingDodge.setScreen(new GameScreen(vikingDodge));
					}
				});
			}

		});

		highscores.addListener(new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				layout.notify(null, Event.BUTTON_PRESSED);
				HighscoresPopup hsp = new HighscoresPopup(vikingDodge, layout);
				hsp.moveIn(1, Interpolation.elastic);
				addActor(hsp);
			}

		});

		login.addListener(new LoginButtonListener(vikingDodge, this));

		sound.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.BUTTON_PRESSED);
				layout.notify(null, Event.SOUND_STATE_CHANGE);
				sound.setStyle(sound.getStyle().equals(soundOn) ? soundOff
						: soundOn);
				return false;
			}

		});
		
		howToPlay.addListener(new ClickListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.BUTTON_PRESSED);
				addActor(new TutorialPopup(vikingDodge, layout));
				return false;
			}
			
		});

	}

	@Override
	public Table init() {
		login.setStyle(Network.isLoggedIn() ? Assets.skin.get("logout",
				ButtonStyle.class) : Assets.skin
				.get("login", ButtonStyle.class));
//		addActor(tutorialPopup);
		addActor(background);
		logoTable.addActor(logo);
		addActor(logoTable);
		addActor(buttons);
		addActor(profile);
//		addActor(sound);
//		addActor(howToPlay);
		return this;
	}

	public void moveLogoAndButtons(Runnable runnable) {
		profile.addAction(Actions.moveTo(-400, profile.getY(), 1,
				Interpolation.pow5));
		buttons.addAction(Actions.sequence(Actions.moveTo(vikingDodge.getUi()
				.getWidth() / 2 - (buttons.getWidth() / 2), -750, 1f,
				Interpolation.pow5), Actions.run(runnable)));

		logoTable.addAction(Actions.moveTo(vikingDodge.getUi().getWidth() / 2
				- (logo.getWidth() / 2), 750, 1f, Interpolation.pow5));
		canChangeScreen = true;
	}

	@Override
	public void clear() {
		clearChildren();
		addActor(background);
	}

	public PlayerAvatarPopup getPlayerAvatarPopup() {
		return profile;
	}

	public Button getLogin() {
		return login;
	}

}
