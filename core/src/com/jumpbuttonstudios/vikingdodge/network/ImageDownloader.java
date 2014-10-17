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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class ImageDownloader {

	
	public static int download(byte[] out, String url){
		InputStream i = null;
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);
			i = connection.getInputStream();
			int readBytes = 0;
			while(true){
				int len = i.read(out, readBytes, out.length - readBytes);
				if(len == -1)	
					break;
				readBytes += len;
			}
			return readBytes;
			
		} catch (Exception e) {
			return 0;
		}finally{
			try {
				i.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		
	}
	
	public static DownloadedImage downloadUsngGDX(String url){
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setUrl(url);
		final DownloadedImage dlImage = new DownloadedImage();
		
		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {
			
			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final int code = httpResponse.getStatus().getStatusCode();
				if(code != 200) return;
				
				dlImage.setBytes(httpResponse.getResult());
				dlImage.setNumBytes(httpResponse.getResult().length);
				
			}
			
			@Override
			public void failed(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
		});
		
		return dlImage;
		
	}
	
	public static DownloadedImage downloadImage(String url){
		byte[] bytes = new byte[200 * 1024];
		int len = download(bytes, url);
		return new DownloadedImage(bytes, len);
	}
	
	public static Texture convertToTexture(DownloadedImage downloadedImage){
		byte[] bytes = downloadedImage.getBytes();
		int numbBytes = downloadedImage.getNumBytes();
		
		if(numbBytes != 0){
			Pixmap tmp = new Pixmap(bytes, 0, numbBytes);
			return new Texture(tmp);
			
		}		
		return null;
	}
	
}