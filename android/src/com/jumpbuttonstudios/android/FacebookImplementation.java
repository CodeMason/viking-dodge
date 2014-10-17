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

package com.jumpbuttonstudios.android;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.jumpbuttonstudios.vikingdodge.interfaces.FacebookService;

public class FacebookImplementation implements FacebookService {

	private AndroidLauncher activity;
	/* The user currently logged in */
	private GraphUser user;
	/* The users access token */
	String accessToken;
	/* If the user has been fetched */
	boolean userFetched = false;
	
	
	/*
	 * If the facebook login was successful, we want to ping back to our main
	 * game
	 */
	private boolean userAcceptedFacebookLogin = false;
	/* If the game can update everything due to facebook state change */
	private boolean canChangeGameState = false;

	public FacebookImplementation(AndroidLauncher activity) {
		this.activity = activity;
		
	}
	
	@Override
	public boolean userFetched() {
		if(userFetched)
			setCanChangeGameState(true);
		return userFetched;
	}

	@Override
	public boolean isLoggedIn() {
		return Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened();
	}

	@Override
	public void logIn() {

		Session.openActiveSession(activity, true, new Session.StatusCallback() {

			@Override
			public void call(final Session session, SessionState state,
					Exception exception) {
				Request.newMeRequest(session, new Request.GraphUserCallback() {

					@Override
					public void onCompleted(final GraphUser user, Response response) {
						if (user != null) {
							userAcceptedFacebookLogin = true;
							new Thread(){
								
								@Override
								public void run() {
									FacebookImplementation.this.user = user;
									userFetched = true;
								}
								
							}.start();
						}

					}

				}).executeAsync();

			}

		});

	}

	@Override
	public void logOut() {
		if (isLoggedIn())
			Session.getActiveSession().closeAndClearTokenInformation();

	}

	@Override
	public String getAvatar() {
		return user != null ? "https://graph.facebook.com/" + user.getId() + "/picture?type=large" : "NO AVATAR";
	}

	@Override
	public String getFirstName() {
		return user != null ? user.getFirstName() : "GUEST";
	}

	@Override
	public String getName() {
		return user != null ? user.getName() : "GUEST";
	}

	@Override
	public boolean canChangeGameState() {
		return canChangeGameState;
	}

	@Override
	public boolean userAcceptedFacebookLogin() {
		return userAcceptedFacebookLogin;
	}

	@Override
	public void setCanChangeGameState(boolean canChangeState) {
		this.canChangeGameState = canChangeState;
	}

}
