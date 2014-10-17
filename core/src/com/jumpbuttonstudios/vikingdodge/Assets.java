/**
 * Copyright 2014 Jump Button Studios
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jumpbuttonstudios.vikingdodge;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 * Class containing all file paths to every asset, it also holds a manager that
 * can be used to access the assets
 * 
 * @author Stephen Gibson
 */
public class Assets {

	/** Used to load and hold the games assets */
	public static AssetManager manager = new AssetManager();

	/** If he Skin has been loaded */
	public static boolean uiLoaded = false;

	/** The logged in players avatar */
	public static Texture LOGGED_IN_PLAYER_AVATAR;

	/* All the players sprites and sprite sheets */
	public static final AssetDescriptor<Texture> PLAYER_STAND = new AssetDescriptor<Texture>(
			"data/sprite/stand.png", Texture.class),
			PLAYER_RUN = new AssetDescriptor<Texture>("data/sprite/run.png",
					Texture.class),
			PLAYER_LEAP = new AssetDescriptor<Texture>("data/sprite/leap.png",
					Texture.class),
			PLAYER_JUMP = new AssetDescriptor<Texture>("data/sprite/jump.png",
					Texture.class),
			PLAYER_BLINK = new AssetDescriptor<Texture>(
					"data/sprite/blink.png", Texture.class),
			PLAYER_BURN = new AssetDescriptor<Texture>("data/sprite/burn.png",
					Texture.class),
			PLAYER_BURN_HELMET_FALL = new AssetDescriptor<Texture>(
					"data/sprite/burnHelmetFall.png", Texture.class),
			PLAYER_STAND_SHEEP = new AssetDescriptor<Texture>(
					"data/sprite/standwsheep.png", Texture.class),
			PLAYER_RUN_SHEEP = new AssetDescriptor<Texture>(
					"data/sprite/runwsheep.png", Texture.class),
			PLAYER_THROW_SHEEP = new AssetDescriptor<Texture>(
					"data/sprite/throwsheep.png", Texture.class);

	/* The boss sprites */
	public static final AssetDescriptor<Texture> DRAGON_FLY = new AssetDescriptor<Texture>(
			"data/boss/dragon/fly.png", Texture.class),
			DRAGON_FIRE = new AssetDescriptor<Texture>(
					"data/boss/dragon/fire.png", Texture.class),
			DRAGON_POST_FIRE = new AssetDescriptor<Texture>(
					"data/boss/dragon/postFire.png", Texture.class),
			DRAGON_FIRE_ROCK = new AssetDescriptor<Texture>(
					"data/boss/dragon/rock.png", Texture.class);

	/* The sheeps sprites and sprite sheets */
	public static final AssetDescriptor<Texture> SHEEP_STAND = new AssetDescriptor<Texture>(
			"data/sprite/sheepstand.png", Texture.class),
			SHEEP_BLINK = new AssetDescriptor<Texture>(
					"data/sprite/sheepblink.png", Texture.class),
			SHEEP_ASHES = new AssetDescriptor<Texture>(
					"data/sprite/sheepburn.png", Texture.class),
			SHEEP_ONLY = new AssetDescriptor<Texture>(
					"data/sprite/sheep00.png", Texture.class),
			SHEEP_INFO = new AssetDescriptor<Texture>(
					"data/sprite/sheeptext.png", Texture.class),
			SHEEP_CRY = new AssetDescriptor<Texture>(
					"data/sprite/sheeptext1.png", Texture.class);

	/* Effects sprites are here */
	public static final AssetDescriptor<Texture> SHADOW = new AssetDescriptor<Texture>(
			"data/sprite/shadow.png", Texture.class),
			EFFECT_DUST = new AssetDescriptor<Texture>("data/sprite/dust.png",
					Texture.class),
			EFFECT_ROCK_DUST = new AssetDescriptor<Texture>(
					"data/object/dust.png", Texture.class),
			EFFECT_BLUR = new AssetDescriptor<Texture>(
					"data/object/blurEffect.png", Texture.class),
			EFFECT_SMOKE = new AssetDescriptor<Texture>(
					"data/sprite/smoke.png", Texture.class),
			EFFECT_GROUND_BURN = new AssetDescriptor<Texture>(
					"data/ground/groundBurn.png", Texture.class),
			EFFECT_HEALTH_REGAIN = new AssetDescriptor<Texture>(
					"data/sprite/heart/regain.png", Texture.class),
			EFFECT_DRAGON_FIRE_HIT = new AssetDescriptor<Texture>(
					"data/boss/dragon/hiteffect.png", Texture.class),
			EFFECT_DARK_BG = new AssetDescriptor<Texture>(
					"data/ui/InGame/Darkwindow_Effect.png", Texture.class);

	/* The one single rock we use */
	public static final AssetDescriptor<Texture> ROCK = new AssetDescriptor<Texture>(
			"data/object/rock.png", Texture.class);

	/* Environment stuff */
	public static final AssetDescriptor<Texture> GROUND = new AssetDescriptor<Texture>(
			"data/ground/Ground1.png", Texture.class),
			BG = new AssetDescriptor<Texture>("data/bg/bg.png", Texture.class),
			BG_ROCK1 = new AssetDescriptor<Texture>("data/bg/bg00.png",
					Texture.class),
			BG_ROCK2 = new AssetDescriptor<Texture>("data/bg/bg01.png",
					Texture.class),
			BG_ROCK3 = new AssetDescriptor<Texture>("data/bg/bg02.png",
					Texture.class),
			BG_ROCK4 = new AssetDescriptor<Texture>("data/bg/bg03.png",
					Texture.class),
			BG_ROCK5 = new AssetDescriptor<Texture>("data/bg/bg04.png",
					Texture.class),
			BG_ROCK6 = new AssetDescriptor<Texture>("data/bg/bg05.png",
					Texture.class),
			BG_CLOUD1 = new AssetDescriptor<Texture>("data/bg/cloud00.png",
					Texture.class),
			BG_CLOUD2 = new AssetDescriptor<Texture>("data/bg/cloud01.png",
					Texture.class),
			BG_CLOUD3 = new AssetDescriptor<Texture>("data/bg/cloud02.png",
					Texture.class),
			BG_CLOUD4 = new AssetDescriptor<Texture>("data/bg/cloud03.png",
					Texture.class),
			FOG = new AssetDescriptor<Texture>("data/bg/fog.png", Texture.class);

	/* All the UI stuff */
	public static final AssetDescriptor<Texture> UI_HUD_HEART_FULL = new AssetDescriptor<Texture>(
			"data/ui/InGame/Heart_Filled.png", Texture.class),
			UI_HUD_HEART_EMPTY = new AssetDescriptor<Texture>(
					"data/ui/InGame/Heart_Empty.png", Texture.class),
			UI_BUTTON_LEFT = new AssetDescriptor<Texture>(
					"data/ui/InGame/Arrow_Left.png", Texture.class),
			UI_BUTTON_LEFT_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Arrow_Left_Pressed.png", Texture.class),
			UI_BUTTON_RIGHT = new AssetDescriptor<Texture>(
					"data/ui/InGame/Arrow_Right.png", Texture.class),
			UI_BUTTON_RIGHT_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Arrow_Right_Pressed.png", Texture.class),
			UI_BUTTON_JUMP = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Jump.png", Texture.class),
			UI_BUTTON_JUMP_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Jump_Pressed.png", Texture.class),
			UI_BUTTON_THROW = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Throw.png", Texture.class),
			UI_BUTTON_THROW_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Throw_Pressed.png", Texture.class),
			UI_BUTTON_PAUSE = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Pause.png", Texture.class),
			UI_BUTTON_PAUSE_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Pause_Pressed.png", Texture.class),
			UI_BUTTON_RESUME = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Play.png", Texture.class),
			UI_BUTTON_RESUME_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Play_Pressed.png", Texture.class),
			UI_BUTTON_REDO = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Redo.png", Texture.class),
			UI_BUTTON_REDO_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Redo_Pressed.png", Texture.class),
			UI_BUTTON_VOLUME_ON = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_VolumeON.png", Texture.class),
			UI_BUTTON_VOLUME_ON_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_VolumeON_Pressed.png", Texture.class),
			UI_BUTTON_VOLUME_OFF = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_VolumeOFF.png", Texture.class),
			UI_BUTTON_VOLUME_OFF_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_VolumeOFF_Pressed.png",
					Texture.class),
			UI_BUTTON_MAINMENU = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_MainMenu.png", Texture.class),
			UI_BUTTON_MAINMENU_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_MainMenu_Pressed.png", Texture.class),
			UI_BUTTON_PLAY = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Play.png", Texture.class),
			UI_BUTTON_PLAY_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Play_Pressed.png", Texture.class),
			UI_BUTTON_HIGHSCORES = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_HighScores.png", Texture.class),
			UI_BUTTON_HIGHSCORES_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_HighScores_Pressed.png",
					Texture.class),
			UI_BUTTON_LOGIN = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_LogIn.png", Texture.class),
			UI_BUTTON_LOGIN_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_LogIn_Pressed.png", Texture.class),
			UI_BUTTON_LOGOUT = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_LogOut.png", Texture.class),
			UI_BUTTON_LOGOUT_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_LogOut_Pressed.png", Texture.class),
			UI_BUTTON_OK = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_OK.png", Texture.class),
			UI_BUTTON_OK_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_OK_Pressed.png", Texture.class),
			UI_BUTTON_HELP = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Help.png", Texture.class),
			UI_BUTTON_HELP_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Help_Pressed.png", Texture.class),
			UI_BUTTON_REGISTER = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Register.png", Texture.class),
			UI_BUTTON_REGISTER_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Register_Pressed.png",
					Texture.class),
			UI_BUTTON_MAIN_MENU_GO = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Mainmenu_GO.png", Texture.class),
			UI_BUTTON_MAIN_MENU_GO_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Mainmenu_GO_Pressed.png",
					Texture.class),
			UI_BUTTON_PLAYAGAIN = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Playagain.png", Texture.class),
			UI_BUTTON_PLAYAGAIN_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Playagain_Pressed.png",
					Texture.class),
			UI_BUTTON_FACEBOOK = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Facebook.png", Texture.class),
			UI_BUTTON_FACEBOOK_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Facebook_Pressed.png", Texture.class),
			UI_BUTTON_TWITTER = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Twitter.png", Texture.class),
			UI_BUTTON_TWITTER_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/InGame/Button_Twitter_Pressed.png", Texture.class),
			UI_BUTTON_JBS = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_JBS.png", Texture.class),
			UI_BUTTON_JBS_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_JBS_Pressed.png", Texture.class),
			UI_BUTTON_BACK = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Button_Back.png", Texture.class),
			UI_BUTTON_BACK_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Button_Back_Pressed.png", Texture.class),
			UI_BUTTON_EMPTY = new AssetDescriptor<Texture>(
					"data/ui/HighScore/highscorePanelEmptyButton.png",
					Texture.class),
			UI_BUTTON_HOWTO = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Howto.png", Texture.class),
			UI_BUTTON_HOWTO_PRESSED = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Button_Howto_Pressed.png", Texture.class),
			UI_WINDOW_PAUSE_OPEN = new AssetDescriptor<Texture>(
					"data/ui/InGame/Pause_WindowOPEN.png", Texture.class),
			UI_WINDOW_PAUSE = new AssetDescriptor<Texture>(
					"data/ui/InGame/Pause_WindowCLOSED.png", Texture.class),
			UI_WINDOW_GAMEOVER = new AssetDescriptor<Texture>(
					"data/ui/GameOver_Window.png", Texture.class),
			UI_WINDOW_LOGIN = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/LogIN_Window.png", Texture.class),
			UI_WINDOW_LOGOUT = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/LogOut_Window.png", Texture.class),
			UI_WINDOW_WRONG_INFO = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/WrongInfo_Window.png", Texture.class),
			UI_WINDOW_NO_CONNECTION = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/NoConnection_Window.png", Texture.class),
			UI_WINDOW_SMALL = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Window_Small.png", Texture.class),
			UI_WIDGET_WELCOME_BAR = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Welcome_Message_Bar.png", Texture.class),
			UI_WIDGET_AVATAR_BOX = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Welcome_Message_UserPicture.png",
					Texture.class),
			UI_WIDGET_AVATAR_BOX_LOGGED_IN = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Welcome_Message_UserPicture_LoggedIn.png",
					Texture.class),
			UI_WIDGET_LOGO = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Logo.png", Texture.class),
			UI_WIDGET_STAR = new AssetDescriptor<Texture>("data/ui/star.png",
					Texture.class),
			UI_WIDGET_HELMET_BRONZE = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Helmet_Bronze.png", Texture.class),
			UI_WIDGET_HELMET_SILVER = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Helmet_Silver.png", Texture.class),
			UI_WIDGET_HELMET_GOLD = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Helmet_Gold.png", Texture.class),
			UI_WIDGET_PLAYERBAR = new AssetDescriptor<Texture>(
					"data/ui/HighScore/Player_Bar.png", Texture.class),
			UI_WIDGET_TEXT = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Widget_Text.png", Texture.class),
			UI_PANEL_FRIENDS = new AssetDescriptor<Texture>(
					"data/ui/HighScore/SelectedTab_Friends.png", Texture.class),
			UI_PANEL_GLOBAL = new AssetDescriptor<Texture>(
					"data/ui/HighScore/SelectedTab_Global.png", Texture.class),
			UI_PANEL_PERSONAL = new AssetDescriptor<Texture>(
					"data/ui/HighScore/SelectedTab_Personal.png", Texture.class),
			UI_MENU_BG = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/Background.png", Texture.class),
			UI_WINDOW_TUTORIAL0 = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/tutorial/Tutorial_Window0.png",
					Texture.class),
			UI_WINDOW_TUTORIAL1 = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/tutorial/Tutorial_Window1.png",
					Texture.class),
			UI_WINDOW_TUTORIAL2 = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/tutorial/Tutorial_Window2.png",
					Texture.class),
			UI_WINDOW_TUTORIAL3 = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/tutorial/Tutorial_Window3.png",
					Texture.class),
			UI_WINDOW_TUTORIAL4 = new AssetDescriptor<Texture>(
					"data/ui/MainMenu/tutorial/Tutorial_Window4.png",
					Texture.class);

	/* All the different speech bubbles that can be used */
	public static AssetDescriptor<Texture> SPEECHBUBBLE_BUBBLE = new AssetDescriptor<Texture>(
			"data/sprite/speechbubbles/SpeechBubble_Bubble.png", Texture.class),
			SPEECHBUBBLE_CLOSE = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Close.png",
					Texture.class),
			SPEECHBUBBLE_CRUSH_SOMETHING = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Crush_Something.png",
					Texture.class),
			SPEECHBUBBLE_DRAGON = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Dragon.png",
					Texture.class),
			SPEECHBUBBLE_DRAGON_QUESTION = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Dragon_Question.png",
					Texture.class),
			SPEECHBUBBLE_GRRR = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Gr.png",
					Texture.class),
			SPEECHBUBBLE_I_CAN_FLY = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_I_Can_Fly.png",
					Texture.class),
			SPEECHBUBBLE_I_COULD_MOVE = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_I_Would_Move.png",
					Texture.class),
			SPEECHBUBBLE_LAMB_CHOPS = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Lamb_Chops.png",
					Texture.class),
			SPEECHBUBBLE_BEARD_MANLY = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Manly_Beard.png",
					Texture.class),
			SPEECHBUBBLE_NO_FEAR = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_No_Fear.png",
					Texture.class),
			SPEECHBUBBLE_SHIT = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Shit_Shortwit.png",
					Texture.class),
			SPEECHBUBBLE_SIT_HERE = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Sit_Here.png",
					Texture.class),
			SPEECHBUBBLE_SO_LONEY = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_So_Lonely.png",
					Texture.class),
			SPEECHBUBBLE_SO_SHORT = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_So_Short.png",
					Texture.class),
			SPEECHBUBBLE_SOFT_N_FLUFFY = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Soft_Fluffy.png",
					Texture.class),
			SPEECHBUBBLE_STUPID_SHEEP = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Stupid_Sheep.png",
					Texture.class),
			SPEECHBUBBLE_TASTE_GREAT_STEW = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Taste_Good.png",
					Texture.class),
			SPEECHBUBBLE_VALHALLA = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Valhalla.png",
					Texture.class),
			SPEECHBUBBLE_WEEE = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Weee.png",
					Texture.class),
			SPEECHBUBBLE_WHERE_HAMMMER = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Where_Hammer.png",
					Texture.class),
			SPEECHBUBBLE_WHAT_YE_DOING = new AssetDescriptor<Texture>(
					"data/sprite/speechbubbles/SpeechBubble_Wit_Ya_Doing.png",
					Texture.class);

	/* All fonts */
	public static final AssetDescriptor<BitmapFont> UI_FONT = new AssetDescriptor<BitmapFont>(
			"data/ui/comixWhite.fnt", BitmapFont.class),
			UI_FONT_SMALL = new AssetDescriptor<BitmapFont>(
					"data/ui/comixWhiteSmall.fnt", BitmapFont.class),
			UI_FONT_LARGE = new AssetDescriptor<BitmapFont>(
					"data/ui/comixWhiteLarge.fnt", BitmapFont.class),
			UI_FONT_TEXT_FIELD = new AssetDescriptor<BitmapFont>(
					"data/ui/TextFieldFont.fnt", BitmapFont.class);

	/* All sounds and music */
	public static final AssetDescriptor<Music> MUSIC_INGAME = new AssetDescriptor<Music>(
			"data/sounds/ingameMusic.mp3", Music.class),
			MUSIC_MENU = new AssetDescriptor<Music>(
					"data/sounds/menuMusic.mp3", Music.class);

	public static final AssetDescriptor<Sound> SOUND_ENTITY_LANDED = new AssetDescriptor<Sound>(
			"data/sounds/landing.wav", Sound.class),
			SOUND_DRAGON_ROAR = new AssetDescriptor<Sound>(
					"data/sounds/dragonbg/finalroar.wav", Sound.class),
			SOUND_PLAYER_THROW = new AssetDescriptor<Sound>(
					"data/sounds/throw1.wav", Sound.class),
			SOUND_PLAYER_RUN = new AssetDescriptor<Sound>(
					"data/sounds/running.wav", Sound.class),
			SOUND_WINDOW = new AssetDescriptor<Sound>("data/sounds/window.wav",
					Sound.class), SOUND_CLICK = new AssetDescriptor<Sound>(
					"data/sounds/click.wav", Sound.class);

	/** Skin for the user interface */
	public static Skin skin = new Skin();

	/**
	 * Returns a given asset
	 * 
	 * @param assetDescriptor
	 * @return
	 */
	public static <T> T get(AssetDescriptor<T> assetDescriptor) {
		return manager.get(assetDescriptor);
	}

	/**
	 * Loads all the assets into the asset manager
	 */
	public static void load() {
		manager.load(BG);
		manager.load(BG_CLOUD1);
		manager.load(BG_CLOUD2);
		manager.load(BG_CLOUD3);
		manager.load(BG_CLOUD4);
		manager.load(BG_ROCK1);
		manager.load(BG_ROCK2);
		manager.load(BG_ROCK3);
		manager.load(BG_ROCK4);
		manager.load(BG_ROCK5);
		manager.load(BG_ROCK6);
		manager.load(DRAGON_FIRE);
		manager.load(DRAGON_FIRE_ROCK);
		manager.load(DRAGON_FLY);
		manager.load(DRAGON_POST_FIRE);
		// manager.load(EFFECT_BLUR);
		manager.load(EFFECT_DRAGON_FIRE_HIT);
		manager.load(EFFECT_DUST);
		manager.load(EFFECT_GROUND_BURN);
		// manager.load(EFFECT_HEALTH_REGAIN);
		manager.load(EFFECT_ROCK_DUST);
		manager.load(EFFECT_DARK_BG);
		manager.load(FOG);
		manager.load(GROUND);
		manager.load(PLAYER_JUMP);
		manager.load(PLAYER_LEAP);
		manager.load(PLAYER_RUN);
		manager.load(PLAYER_BLINK);
		manager.load(PLAYER_BURN);
		manager.load(PLAYER_BURN_HELMET_FALL);
		manager.load(PLAYER_RUN_SHEEP);
		manager.load(PLAYER_STAND);
		manager.load(PLAYER_STAND_SHEEP);
		manager.load(PLAYER_THROW_SHEEP);
		manager.load(ROCK);
		manager.load(SHADOW);
		manager.load(SHEEP_CRY);
		manager.load(SHEEP_INFO);
		manager.load(SHEEP_ONLY);
		manager.load(SHEEP_STAND);
		manager.load(SHEEP_ASHES);
		manager.load(SHEEP_BLINK);
		manager.load(UI_BUTTON_BACK);
		manager.load(UI_BUTTON_BACK_PRESSED);
		manager.load(UI_BUTTON_MAINMENU);
		manager.load(UI_BUTTON_MAINMENU_PRESSED);
		manager.load(UI_BUTTON_HIGHSCORES);
		manager.load(UI_BUTTON_HIGHSCORES_PRESSED);
		manager.load(UI_BUTTON_JUMP);
		manager.load(UI_BUTTON_JUMP_PRESSED);
		manager.load(UI_BUTTON_LEFT);
		manager.load(UI_BUTTON_LEFT_PRESSED);
		manager.load(UI_BUTTON_LOGIN);
		manager.load(UI_BUTTON_LOGIN_PRESSED);
		manager.load(UI_BUTTON_LOGOUT);
		manager.load(UI_BUTTON_LOGOUT_PRESSED);
		manager.load(UI_BUTTON_OK);
		manager.load(UI_BUTTON_OK_PRESSED);
		manager.load(UI_BUTTON_HELP);
		manager.load(UI_BUTTON_HELP_PRESSED);
		manager.load(UI_BUTTON_REGISTER);
		manager.load(UI_BUTTON_REGISTER_PRESSED);
		manager.load(UI_BUTTON_PAUSE);
		manager.load(UI_BUTTON_PAUSE_PRESSED);
		manager.load(UI_BUTTON_PLAY);
		manager.load(UI_BUTTON_PLAY_PRESSED);
		manager.load(UI_BUTTON_RESUME);
		manager.load(UI_BUTTON_RESUME_PRESSED);
		manager.load(UI_BUTTON_REDO);
		manager.load(UI_BUTTON_REDO_PRESSED);
		manager.load(UI_BUTTON_RIGHT);
		manager.load(UI_BUTTON_RIGHT_PRESSED);
		manager.load(UI_BUTTON_THROW);
		manager.load(UI_BUTTON_THROW_PRESSED);
		manager.load(UI_BUTTON_EMPTY);
		manager.load(UI_BUTTON_MAIN_MENU_GO);
		manager.load(UI_BUTTON_MAIN_MENU_GO_PRESSED);
		manager.load(UI_BUTTON_PLAYAGAIN);
		manager.load(UI_BUTTON_PLAYAGAIN_PRESSED);
		manager.load(UI_BUTTON_FACEBOOK);
		manager.load(UI_BUTTON_FACEBOOK_PRESSED);
		manager.load(UI_BUTTON_TWITTER);
		manager.load(UI_BUTTON_TWITTER_PRESSED);
		manager.load(UI_BUTTON_VOLUME_OFF);
		manager.load(UI_BUTTON_VOLUME_OFF_PRESSED);
		manager.load(UI_BUTTON_VOLUME_ON);
		manager.load(UI_BUTTON_VOLUME_ON_PRESSED);
		manager.load(UI_BUTTON_JBS);
		manager.load(UI_BUTTON_JBS_PRESSED);
		manager.load(UI_BUTTON_HOWTO);
		manager.load(UI_BUTTON_HOWTO_PRESSED);
		manager.load(UI_FONT);
		manager.load(UI_FONT_SMALL);
		manager.load(UI_FONT_LARGE);
		manager.load(UI_FONT_TEXT_FIELD);
		manager.load(UI_HUD_HEART_EMPTY);
		manager.load(UI_HUD_HEART_FULL);
		// manager.load(UI_MENU_BG);
		manager.load(UI_PANEL_FRIENDS);
		manager.load(UI_PANEL_GLOBAL);
		manager.load(UI_PANEL_PERSONAL);
		manager.load(UI_WIDGET_HELMET_BRONZE);
		manager.load(UI_WIDGET_HELMET_SILVER);
		manager.load(UI_WIDGET_HELMET_GOLD);
		manager.load(UI_WIDGET_TEXT);
		// manager.load(UI_WIDGET_LOGO);
		manager.load(UI_WIDGET_PLAYERBAR);
		// manager.load(UI_WIDGET_STAR);
		manager.load(UI_WIDGET_WELCOME_BAR);
		manager.load(UI_WIDGET_AVATAR_BOX);
		manager.load(UI_WIDGET_AVATAR_BOX_LOGGED_IN);
		manager.load(UI_WINDOW_GAMEOVER);
		manager.load(UI_WINDOW_LOGIN);
		manager.load(UI_WINDOW_LOGOUT);
		manager.load(UI_WINDOW_PAUSE);
		manager.load(UI_WINDOW_PAUSE_OPEN);
		manager.load(UI_WINDOW_WRONG_INFO);
		manager.load(UI_WINDOW_NO_CONNECTION);
		manager.load(UI_WINDOW_SMALL);
		manager.load(UI_WINDOW_TUTORIAL0);
		manager.load(UI_WINDOW_TUTORIAL1);
		manager.load(UI_WINDOW_TUTORIAL2);
		manager.load(UI_WINDOW_TUTORIAL3);
		manager.load(UI_WINDOW_TUTORIAL4);
		manager.load(SPEECHBUBBLE_BUBBLE);
		manager.load(SPEECHBUBBLE_CLOSE);
		manager.load(SPEECHBUBBLE_DRAGON);
		manager.load(SPEECHBUBBLE_I_CAN_FLY);
		manager.load(SPEECHBUBBLE_LAMB_CHOPS);
		manager.load(SPEECHBUBBLE_NO_FEAR);
		manager.load(SPEECHBUBBLE_SHIT);
		manager.load(SPEECHBUBBLE_SIT_HERE);
		manager.load(SPEECHBUBBLE_SO_LONEY);
		manager.load(SPEECHBUBBLE_SOFT_N_FLUFFY);
		manager.load(SPEECHBUBBLE_STUPID_SHEEP);
		manager.load(SPEECHBUBBLE_VALHALLA);
		manager.load(SPEECHBUBBLE_WEEE);
		manager.load(SPEECHBUBBLE_WHAT_YE_DOING);
		manager.load(SPEECHBUBBLE_BEARD_MANLY);
		manager.load(SPEECHBUBBLE_CRUSH_SOMETHING);
		manager.load(SPEECHBUBBLE_DRAGON_QUESTION);
		manager.load(SPEECHBUBBLE_GRRR);
		manager.load(SPEECHBUBBLE_I_COULD_MOVE);
		manager.load(SPEECHBUBBLE_SO_SHORT);
		manager.load(SPEECHBUBBLE_TASTE_GREAT_STEW);
		manager.load(SPEECHBUBBLE_WHERE_HAMMMER);
		manager.load(MUSIC_INGAME);
		manager.load(SOUND_ENTITY_LANDED);
		manager.load(SOUND_DRAGON_ROAR);
		manager.load(SOUND_PLAYER_THROW);
		manager.load(SOUND_PLAYER_RUN);
		manager.load(SOUND_WINDOW);
		manager.load(SOUND_CLICK);

		Array<Texture> textures = manager.getAll(Texture.class,
				new Array<Texture>());
		for (Texture t : textures) {
			t.setFilter(TextureFilter.Nearest,
					TextureFilter.MipMapNearestLinear);
		}

	}

	/** Loads the required assets into the skin for the loading screen UI */
	public static void loadingScreenUIDependencies() {
		skin.add("menu_bg", new TextureRegionDrawable(new TextureRegion(
				get(UI_MENU_BG))));
		skin.add("logo", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_LOGO))));
		skin.add("label", new LabelStyle(get(UI_FONT), Color.WHITE));
	}

	/** Add all the requires user interface assets to the skin */
	public static void loadUI() {

		/* ALL BUTTON STUFF */
		ButtonStyle style = new ButtonStyle();
		/* Create play button */
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PLAY)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PLAY_PRESSED)));
		skin.add("play", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HOWTO)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HOWTO_PRESSED)));
		skin.add("howToButton", style);

		/* Create login button */
		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LOGIN)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LOGIN_PRESSED)));
		skin.add("login", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LOGOUT)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LOGOUT_PRESSED)));
		skin.add("logout", style);

		/* Create highscores button */
		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HIGHSCORES)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HIGHSCORES_PRESSED)));
		skin.add("highscores", style);

		/* Create left and right buttons */
		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LEFT)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_LEFT_PRESSED)));
		style.over = style.down;
		skin.add("left", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_RIGHT)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_RIGHT_PRESSED)));
		style.over = style.down;
		skin.add("right", style);

		/* Create jump and throw buttons */
		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_JUMP)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_JUMP_PRESSED)));
		skin.add("jump", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_THROW)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_THROW_PRESSED)));
		skin.add("throw", style);

		/* Create pause popup stuff */
		skin.add("pauseClosed", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_PAUSE))));
		skin.add("pauseOpen", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_PAUSE_OPEN))));

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_RESUME)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_RESUME_PRESSED)));
		skin.add("resumeButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PAUSE)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PAUSE_PRESSED)));
		skin.add("pauseButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_REDO)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_REDO_PRESSED)));
		skin.add("redoButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_MAINMENU)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_MAINMENU_PRESSED)));
		skin.add("exitButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_EMPTY)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_EMPTY)));
		skin.add("emptyButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_OK)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_OK_PRESSED)));
		skin.add("okButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HELP)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_HELP_PRESSED)));
		skin.add("helpButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_REGISTER)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_REGISTER_PRESSED)));
		skin.add("registerButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_BACK)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_BACK_PRESSED)));
		skin.add("backButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_MAIN_MENU_GO)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_MAIN_MENU_GO_PRESSED)));
		skin.add("mainmenuGoButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PLAYAGAIN)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_PLAYAGAIN_PRESSED)));
		skin.add("playagainButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_FACEBOOK)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_FACEBOOK_PRESSED)));
		skin.add("facebookButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_TWITTER)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_TWITTER_PRESSED)));
		skin.add("twitterButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_JBS)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_JBS_PRESSED)));
		skin.add("jbsButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_VOLUME_OFF)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_VOLUME_OFF_PRESSED)));
		skin.add("soundOffButton", style);

		style = new ButtonStyle();
		style.up = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_VOLUME_ON)));
		style.down = new TextureRegionDrawable(new TextureRegion(
				get(UI_BUTTON_VOLUME_ON_PRESSED)));
		skin.add("soundOnButton", style);

		/* Main menu login/out shit */
		skin.add("loginWindow", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_LOGIN))));
		skin.add("logoutWindow", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_LOGOUT))));
		skin.add("wrongInfo", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_WRONG_INFO))));
		skin.add("noConnection", new TextureRegionDrawable(new TextureRegion(
				get(UI_WINDOW_NO_CONNECTION))));
		skin.add("avatarBox", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_AVATAR_BOX))));
		skin.add("avatarBoxLogged", new TextureRegionDrawable(
				new TextureRegion(get(UI_WIDGET_AVATAR_BOX_LOGGED_IN))));
		skin.add("welcomeBar", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_WELCOME_BAR))));

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = get(UI_FONT);
		textFieldStyle.fontColor = Color.BLACK;
		skin.add("field", TextFieldStyle.class);

		/* HIGHSCORE SCREEN PANELS */
		skin.add("personalPanel", new TextureRegionDrawable(new TextureRegion(
				get(UI_PANEL_PERSONAL))));
		skin.add("globalPanel", new TextureRegionDrawable(new TextureRegion(
				get(UI_PANEL_GLOBAL))));
		skin.add("friendsPanel", new TextureRegionDrawable(new TextureRegion(
				get(UI_PANEL_FRIENDS))));

		skin.add("bronzeHelmet", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_HELMET_BRONZE))));
		skin.add("silverHelmet", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_HELMET_SILVER))));
		skin.add("goldHelmet", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_HELMET_GOLD))));

		skin.add("playerBar", new TextureRegionDrawable(new TextureRegion(
				get(UI_WIDGET_PLAYERBAR))));

		skin.add("smallLabel", new LabelStyle(get(UI_FONT_SMALL), Color.WHITE));
		skin.add("largeLabel", new LabelStyle(get(UI_FONT_LARGE), Color.WHITE));

		/* That dark panel thing */
		skin.add("darkBG", new TextureRegionDrawable(new TextureRegion(
				get(EFFECT_DARK_BG))));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = get(UI_FONT);
		skin.add("default", textButtonStyle);

		uiLoaded = true;

	}
}
