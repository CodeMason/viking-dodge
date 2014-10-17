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

public interface AdListener {
	
	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	public static final int FULL = 3;
	public static final int REMOVE_TOP = 4;
	public static final int REMOVE_BOTTOM = 5;
	
	
	public void requestAd(int adID);
	
	public void loadFullScreen();
	
	public void closeAd(int adID);
	
	public boolean isFullScreenAdLoaded();

}
