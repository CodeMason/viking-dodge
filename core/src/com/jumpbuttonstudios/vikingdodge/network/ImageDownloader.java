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

import java.io.ByteArrayOutputStream;
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
import com.esotericsoftware.minlog.Log;

public class ImageDownloader {

	public static byte[] download(String url) {
        InputStream i = null;
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setUseCaches(true);
            i = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = i.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, len);
                System.out.println(len);
            }
            buffer.flush();
            Log.info("DATA", "Done loading data.");
            return buffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                i.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

	public static DownloadedImage downloadUsngGDX(String url) {
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setUrl(url);
		final DownloadedImage dlImage = new DownloadedImage();

		Gdx.net.sendHttpRequest(httpRequest, new HttpResponseListener() {

			@Override
			public void handleHttpResponse(HttpResponse httpResponse) {
				final int code = httpResponse.getStatus().getStatusCode();
				if (code != 200)
					return;

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

	public static DownloadedImage downloadImage(String url) {
		byte[] bytes = download(url);
		int len = bytes.length;
		return new DownloadedImage(bytes, len);
	}

	public static Texture convertToTexture(DownloadedImage downloadedImage) {
		byte[] bytes = downloadedImage.getBytes();
		int numbBytes = downloadedImage.getNumBytes();

		if (numbBytes != 0) {
			Pixmap tmp = new Pixmap(bytes, 0, numbBytes);
			return new Texture(tmp);

		}
		return null;
	}

}
