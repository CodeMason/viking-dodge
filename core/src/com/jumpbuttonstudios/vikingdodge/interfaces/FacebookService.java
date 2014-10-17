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

package com.jumpbuttonstudios.vikingdodge.interfaces;

import com.restfb.types.User;

public interface FacebookService {

	/**
	 * This checks if the user has been fetched, since we have to run the fetch
	 * code on threads we must set flags and wait This methid should be called
	 * before trying to do any calls on the {@link User} instance
	 */
	public boolean userFetched();

	/**
	 * Checks if the session is not null and that the connection is open
	 * 
	 * @return true if logged in
	 */
	public boolean isLoggedIn();

	/**
	 * Opens up a new session and requests the user for access to facebook and
	 * permissions
	 */
	public void logIn();

	/**
	 * Logs the user out and clears the session
	 */
	public void logOut();

	/**
	 * Gets the users full name
	 * 
	 * @return full name if user not null, "GUEST" if user null
	 */
	public String getName();

	/**
	 * Gets the users first name only
	 * 
	 * @return first name if user not null, "GUEST" if user null
	 */
	public String getFirstName();

	/**
	 * 
	 * @return the url of the users avatar
	 */
	public String getAvatar();

	/**
	 * Once the user has accepted the login request we wait until the user has
	 * been fetched, you can check this by calling {@link #userFetched()}
	 * elements
	 * 
	 * @return true if the user allowed facebook to login
	 * 
	 */
	public boolean userAcceptedFacebookLogin();

	/**
	 * This is checked after the user has accepted the login request and they
	 * have been fetched, this will allow the game to update the UI accordingly
	 * 
	 * @return return true if the user has clicked facebook login and accepted
	 */
	public boolean canChangeGameState();

	public void setCanChangeGameState(boolean canChangeState);

}
