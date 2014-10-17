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

package com.jumpbuttonstudios.vikingdodge.network;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jumpbuttonstudio.Friend;
import com.jumpbuttonstudio.HighscoreResult;
import com.jumpbuttonstudio.api.API;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.interfaces.FacebookService;

public class Network {

	static API api = new API();

	static String authenticationKey;
	static String devID;
	static int gameID;

	/* The client API to use */
	static FacebookService facebookService;

	/* If a facebook login request was sent */
	static boolean requestSent;

	/* The users login username */
	static String username;

	/* If the highscore has been submitted for this session */
	static boolean scoreSubmited;

	/* The users avatar */
	static DownloadedImage avatar;

	/* If the avatar has finished downloading */
	static boolean avatarDownloaded = false;

	/* How many attempts thus far */
	static int connectionAttempts = 1;

	/* How many times the server will try to reconnect after losing connect */
	static int MaxConnectionAttempts = 5;

	/* How long the thread will sleep when verifying we are still connected */
	static long checkConnectionSleepTime = 30000;

	/* How long the thread will sleep for when attempting to reconnect */
	static long attemptToConnectSleepTime = 5000;

	/* If the game is connected to the server */
	static boolean isConnected = false;

	/* If the user is logged into an account */
	static boolean isLoggedIn = false;

	/**
	 * Registers the game details
	 * 
	 * @param authenticationKey
	 * @param devID
	 * @param gameID
	 */
	public static void register(String authenticationKey, String devID,
			int gameID) {
		Network.authenticationKey = authenticationKey;
		Network.devID = devID;
		Network.gameID = gameID;

	}

	/**
	 * Connects to the network
	 */
	public static void connect() {
		new Thread() {
			@Override
			public void run() {
				/* Fire into a loop and check for connection */
				while (true) {
					if (api.isConnected()) {
						setConnected(true);
						Log.info("Network", "Connection OK");
						try {
							Thread.sleep(checkConnectionSleepTime);
						} catch (InterruptedException e) {
						}
						connectionAttempts = 1;
					} else if (connectionAttempts <= MaxConnectionAttempts) {
						setConnected(false);
						api.connect();
						api.authenticate("LPpiERDcHrju98FhPgc6", "gibbo", 7);
						try {
							Thread.sleep(attemptToConnectSleepTime);
						} catch (InterruptedException e) {
						}
						Log.info("Network", "Connection attempt "
								+ connectionAttempts);
						connectionAttempts++;

					} else {
						try {
							Thread.sleep(checkConnectionSleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						connectionAttempts = 0;
					}

				}
			}

		}.start();
	}

	public static void disconnect() {
		api.disconnect();
		api.logout();
		setLoggedIn(false);
		setConnected(false);
		setAvatarDownloaded(false);
		avatar = null;
		username = null;
	}

	public static void login(String username, String password) {
		setLoggedIn(api.checkLogin(username, password));
		if (isLoggedIn) {
			Network.username = username;
			avatar = ImageDownloader.downloadImage(api.getUserAvatar());
			setAvatarDownloaded(true);
			Assets.skin.add("avatar", new TextureRegionDrawable(
					new TextureRegion(getAvatarAsTexture())));
			Log.info("Network", "Logged in as '" + username + "'");
			Log.info("Netowkr", "Avatar URL : " + api.getUserAvatar());
			Log.info("Network", "The current users ID is ': " + api.getUserID()
					+ "'");
		} else {
			Log.error("Network", "User " + username
					+ " could not be found or the password does not match");
		}
	}

	public static void login() {
		new Thread() {

			@Override
			public void run() {
				facebookService.logIn();
				requestSent = true;
			}
		}.start();
	}

	public static void logout() {
		api.logout();
		setLoggedIn(false);
		setAvatarDownloaded(false);
		avatar = null;
		Log.info("Network", "Logged out user '" + username + "'");
		username = null;
	}

	public static void submitHighscore(final int score) {
		if (score == 0)
			return;
		Log.info("Network", "Attempting to submit highscore");
		new Thread() {
			public void run() {
				api.submitHighscore(score);
				Log.info("Network", "Submitted Highscore of '" + score
						+ "' for user '" + username + "'");
			}
		};
		scoreSubmited = true;
	}

	public static boolean userAcceptedFacebookLoginRequest() {
		if (facebookService.userAcceptedFacebookLogin() && requestSent) {
			setLoggedIn(true);
			return true;
		}
		return false;
	}
	
	public static boolean userFetched(){
		if(facebookService.userFetched()){
			facebookService.setCanChangeGameState(true);
		}
		return facebookService.userFetched();
	}

	public static boolean canGameStateChange() {
		boolean stateChange;
		if (facebookService.canChangeGameState()) {
			Network.username = facebookService.getFirstName();
			avatar = ImageDownloader.downloadUsngGDX(Network.getAvatar());
			setAvatarDownloaded(true);
			Assets.skin.add("avatar", new TextureRegionDrawable(
					new TextureRegion(getAvatarAsTexture())));
			Log.info("Network", "Logged in as '" + username + "'");
			Log.info("Network", "Avatar URL : " + facebookService.getAvatar());
			stateChange = facebookService.canChangeGameState();
			facebookService.setCanChangeGameState(false);
			requestSent = false;
			return stateChange;
		}
		return false;
	}

	public static String getUsername() {
		if (isLoggedIn)
			return username.toUpperCase();
		return "GUEST";
	}

	public static String getAvatar() {
		return facebookService.getAvatar();
	}
	
	public static Texture getAvatarAsTexture(){
		return ImageDownloader.convertToTexture(avatar);
	}

	public static boolean isAvatarDownloaded() {
		return avatarDownloaded;
	}

	public static void setAvatarDownloaded(boolean avatarDownloaded) {
		Network.avatarDownloaded = avatarDownloaded;
	}

	public static ArrayList<HighscoreResult> getGlobalHighscores() {
		return api.getGlobalHighscores();
	}

	public static ArrayList<Friend> getFriendsHighscore() {
		return api.getFriends();
	}
	
	public static int getUserHighscore(int userID){
		return api.getHighscore(userID);
	}

	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setLoggedIn(boolean isLoggedIn) {
		Network.isLoggedIn = isLoggedIn;
	}

	public static boolean isConnected() {
		return isConnected;
	}

	public static void setConnected(boolean isConnect) {
		Network.isConnected = isConnect;
	}

	public static boolean isScoreSubmited() {
		return scoreSubmited;
	}

	public static void setFacebookService(FacebookService facebookService) {
		Network.facebookService = facebookService;
	}

	public static FacebookService getFacebookService() {
		return facebookService;
	}
}
