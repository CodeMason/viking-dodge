package com.jumpbuttonstudios.vikingdodge.event;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.jumpbuttonstudios.vikingdodge.Assets;

/**
 * Enumerated events for easy trigger and resolving
 * 
 * @author Gibbo
 * 
 */
public enum Event {
	/* All player input related events */
	PLAYER_MOVE_LEFT(Assets.get(Assets.SOUND_PLAYER_RUN), 0.5f),
	PLAYER_MOVE_RIGHT(Assets.get(Assets.SOUND_PLAYER_RUN), 0.5f),
	PLAYER_THROW(Assets.get(Assets.SOUND_PLAYER_THROW), 0.5f),
	PLAYER_JUMP(Assets.get(Assets.SOUND_PLAYER_RUN), 0.3f),
	PLAYER_STOPPED(Assets.get(Assets.SOUND_PLAYER_RUN), 0.3f),
	PLAYER_ON_TOP_OF_SOMETHING(Assets.get(Assets.SOUND_ENTITY_LANDED), 1),
	PLAYER_FELL,
	PLAYER_COLLIDED_WITH_ROCK,
	PLAYER_COLLIDED_WITH_SHEEP,
	PLAYER_COLLIDED_WITH_FIREBALL,
	GAMEOVER,
	SHEEP_ON_GROUND,
	SHEEP_COLLIDED_WITH_FIREBALL,
	THROWN_SHEEP_COLLISION,
	ROCK_COLLIDED_WITH_GROUND,
	ROCK_COLLIDED_WITH_SHEEP,
	DRAGON_FIRE,
	DRAGON_SPAWN(Assets.get(Assets.SOUND_DRAGON_ROAR), 0.4f),
	FIREBALL_COLLISION,
	FIREBALL_GROUND_COLLISION,
	CHANGED_TO_MENU(Assets.get(Assets.MUSIC_MENU), 1),
	CHANGED_TO_GAMESCREEN(Assets.get(Assets.MUSIC_INGAME), 0.5f),
	CHANGED_TO_LOADINGSCREEN(Assets.get(Assets.MUSIC_MENU), 1),
	PAUSED,
	RESUMED,
	SOUND_STATE_CHANGE,
	WINDOW_OPEN(Assets.get(Assets.SOUND_WINDOW), 1),
	BUTTON_PRESSED(Assets.get(Assets.SOUND_CLICK), .25f);
	
	
	public Sound effect;
	public Music track;
	public float volume;
	
	private Event() {
	}
	
	private Event(Music track, float volume){
		this.track = track;
		this.volume = volume;
	}
	
	private Event(Sound effect, float volume){
		this.effect = effect;
		this.volume = volume;
	}
	
	public Sound getEffect() {
		return effect;
	}
	
	
	public Music getTrack() {
		return track;
	}
	
	public float getVolume() {
		return volume;
	}
	
}
