/**
 * Copyright 2014 Stephen Gibson
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
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.entity.Entity;
import com.jumpbuttonstudios.vikingdodge.entity.Player;
import com.jumpbuttonstudios.vikingdodge.entity.Player.PlayerState;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.event.EventListener;

public class SoundManager implements EventListener {

	VikingDodge vikingDodge;

	/* If sound is disabled */
	boolean soundDisabled = false;

	/* A collection of all the sound instances currently playing */
	Array<Long> soundIDs = new Array<Long>();

	/* The rate at which the track will fade */
	float fadeAmount = 0.5f;

	/* This is the track that is currently playing */
	Music currentTrack;
	/*
	 * This is the track that will be played once the current track has been
	 * faded out or changed instantly
	 */
	Music nextTrack;

	/* The volume of the next track */
	float volume;

	/* If we are in the proccess of changing a music track */
	boolean trackChanging = false;

	/* If we are at the initial setup of the game, so we cna fade in the track */
	boolean initialSetup = false;

	public SoundManager(VikingDodge vikingDodge) {
		this.vikingDodge = vikingDodge;
	}

	/* Checks for track changes and handles fading in and out of tracks */
	public void update(float delta) {

		if (initialSetup) {
			if (currentTrack.getVolume() + fadeAmount * delta < 1) {
				currentTrack.setVolume(currentTrack.getVolume() + fadeAmount
						* delta);
			} else {
				currentTrack.setVolume(volume);
				initialSetup = false;
			}
		} else

		/*
		 * Check if we are changing track and fade out the current track and
		 * fade in the next track
		 */
		if (trackChanging) {
			if (currentTrack.getVolume() - fadeAmount * delta > 0) {
				currentTrack.setVolume(currentTrack.getVolume() - fadeAmount
						* delta);
			}

			if (currentTrack.getVolume() <= 0.5f) {
				nextTrack.setVolume(nextTrack.getVolume() < volume ? nextTrack
						.getVolume() + fadeAmount * delta : volume);
			}

			if (nextTrack.getVolume() >= volume) {
				currentTrack.stop();
				trackChanging = false;
				currentTrack = nextTrack;
				nextTrack = null;
			}
		}
	}

	/* Changes the track from one to another */
	public void changeTrack(Music nextTrack, float volume) {
		if (!soundDisabled) {
			this.nextTrack = nextTrack;
			this.nextTrack.setVolume(0);
			this.nextTrack.setLooping(true);
			this.nextTrack.play();
			this.volume = volume;
			trackChanging = true;
		} else {
			this.currentTrack = nextTrack;
			this.volume = volume;
		}
	}

	/* Sets the initial track */
	public void setInitialMusic(AssetDescriptor<Music> currentTrack,
			float volume) {
		this.currentTrack = Assets.get(currentTrack);
		this.currentTrack.setVolume(0);
		this.currentTrack.setLooping(true);
		initialSetup = true;
		this.currentTrack.play();
	}

	/**
	 * Plays a given send effect
	 * 
	 * @param effect
	 * @param volume
	 * @return the sound ID
	 */
	public long playSound(Sound effect, float volume) {
		return effect.play(volume);
	}

	/**
	 * Plays a given send effect
	 * 
	 * @param effect
	 * @param volume
	 * @return the sound ID
	 */
	public long playSound(Sound effect, float volume, boolean loop) {
		long id = effect.play(volume);
		if (soundIDs.contains(id, false)) {
			effect.stop(id);
			soundIDs.removeValue(id, false);
		}
		effect.loop(id);
		soundIDs.add(id);
		return id;
	}

	public boolean playSound(int ID) {
		return true;
	}
	
	public void playTrack(Music music){
		this.currentTrack = music;
		currentTrack.play();
		volume = 0.75f;
		currentTrack.setVolume(volume);
	}

	@Override
	public void onNotify(Entity entity, Event event) {
		Player p = vikingDodge.getEntityHandler() == null ? null : vikingDodge
				.getEntityHandler().getPlayer();
		if (event == Event.SOUND_STATE_CHANGE) {
			soundDisabled = soundDisabled ? false : true;
			if (soundDisabled)
				currentTrack.pause();
			else{
				currentTrack.play();
				currentTrack.setVolume(volume);
			}
		}

		switch (event) {
		case CHANGED_TO_MENU:
			changeTrack(event.getTrack(), event.getVolume());
			break;
		case CHANGED_TO_GAMESCREEN:
			changeTrack(event.getTrack(), event.getVolume());
			break;
		default:
			break;
		}

		if (!soundDisabled)
			switch (event) {
			case CHANGED_TO_MENU:
				changeTrack(event.getTrack(), event.getVolume());
				break;
			case CHANGED_TO_GAMESCREEN:
				changeTrack(event.getTrack(), event.getVolume());
				break;
			case WINDOW_OPEN:
				playSound(event.getEffect(), event.getVolume());
				break;
			case BUTTON_PRESSED:
				playSound(event.getEffect(), event.getVolume());
				break;
			case PLAYER_ON_TOP_OF_SOMETHING:
				playSound(event.getEffect(), event.getVolume());
				break;
			case DRAGON_SPAWN:
				playSound(event.getEffect(), event.getVolume());
				break;
			case PLAYER_THROW:
				if (vikingDodge.getEntityHandler().getPlayer()
						.getSheepInteraction().hasSheep())
					playSound(event.getEffect(), event.getVolume());
				break;
			case PLAYER_MOVE_LEFT:
				if (p != null)
					if (p.getJumpAction().isGrounded()
							&& p.getCurrentState() != PlayerState.SQUASHED)
						playSound(event.getEffect(), event.getVolume(), true);
				break;
			case PLAYER_MOVE_RIGHT:
				if (p != null)
					if (p.getJumpAction().isGrounded()
							&& p.getCurrentState() != PlayerState.SQUASHED)
						playSound(event.getEffect(), event.getVolume(), true);
				break;
			case PLAYER_STOPPED:
				event.getEffect().stop();
				break;
			case PLAYER_JUMP:
				event.getEffect().stop();
				break;
			default:
				break;
			}
	}
	
	public boolean isSoundDisabled() {
		return soundDisabled;
	}

}
