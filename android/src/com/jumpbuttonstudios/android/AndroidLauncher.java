package com.jumpbuttonstudios.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.android.vending.billing.IInAppBillingService;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jumpbuttonstudio.logger.Log;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.interfaces.AdListener;

public class AndroidLauncher extends AndroidApplication implements AdListener {

	VikingDodge vikingDodge;
	public static boolean DEBUG = false;

	RelativeLayout layout;
	RelativeLayout.LayoutParams params;

	AdView bottomAd;
	AdView topAd;
	InterstitialAd fullscreenAd;

	IInAppBillingService inAppBillingService;
	ServiceConnection sc;

	/** Allows ads to be enabled and disabled */
	public enum AdState {
		TOP, BOTTOM, FULL, ENABLE, DISABLE;
	}
	AndroidLauncher androidLauncher;

	AdRequest request = new AdRequest.Builder().addTestDevice(
			AdRequest.DEVICE_ID_EMULATOR).build();

	protected Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (!DEBUG) {
				switch (msg.what) {
				case AdListener.BOTTOM:

					params = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.WRAP_CONTENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);

					layout.addView(bottomAd, params);

					bottomAd.setEnabled(true);
					bottomAd.setBackgroundColor(Color.BLACK);
					bottomAd.loadAd(request);
					Log.info("Ads", "Loading Bottom Ad");
					break;
				case AdListener.TOP:

					params = new RelativeLayout.LayoutParams(
							RelativeLayout.LayoutParams.WRAP_CONTENT,
							RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);

					layout.addView(topAd, params);

					topAd.setEnabled(true);
					topAd.setBackgroundColor(Color.BLACK);
					topAd.loadAd(request);
					Log.info("Ads", "Loading Top Ad");
					break;
				case AdListener.FULL:

					topAd.setEnabled(false);
					bottomAd.setEnabled(false);
					topAd.setBackgroundColor(Color.BLACK);
					fullscreenAd.loadAd(request);
					break;
				case REMOVE_BOTTOM:
					bottomAd.setEnabled(false);
					layout.removeView(bottomAd);
					Log.info("Ads", "Removing Bottom Ad");
					break;
				case REMOVE_TOP:
					((ViewGroup)topAd.getParent()).removeView(topAd);
					topAd.setEnabled(false);
					layout.removeViewInLayout(topAd);
					Log.info("Ads", "Removing Top Ad");
					break;
				default:
					break;
				}
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vikingDodge = new VikingDodge();
		VikingDodge.setAdListener(this);
		androidLauncher = this;
		layout = new RelativeLayout(this);

		sc = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				inAppBillingService = null;

			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				inAppBillingService = IInAppBillingService.Stub.asInterface(service);

			}
		};
		
		Intent billingIntent = new Intent("com.android.billing.InAppBillingService.BIND");
		billingIntent.setPackage("com.android.vending");
		bindService(billingIntent, sc, Context.BIND_AUTO_CREATE);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new VikingDodge(), config);

		/* Create the ad views */
		bottomAd = new AdView(this);
		topAd = new AdView(this);
		fullscreenAd = new InterstitialAd(this);

		/* Set the ad ids */
		bottomAd.setAdUnitId("ca-app-pub-4184436538256601/8061928372");
		topAd.setAdUnitId("ca-app-pub-4184436538256601/4249007577");
		fullscreenAd.setAdUnitId("ca-app-pub-4184436538256601/9538661572");

		/* Set add sizes */
		bottomAd.setAdSize(AdSize.BANNER);
		topAd.setAdSize(AdSize.BANNER);

		layout.addView(gameView);

		topAd.setEnabled(false);
		bottomAd.setEnabled(false);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		setContentView(layout);

	}

	@Override
	public void requestAd(int adID) {

		switch (adID) {
		case AdListener.BOTTOM:
			handler.sendEmptyMessage(adID);
			break;
		case AdListener.TOP:
			handler.sendEmptyMessage(adID);
			break;
		case AdListener.FULL:
			handler.sendEmptyMessage(adID);
			break;
		default:
			break;
		}
	}

	@Override
	public void loadFullScreen() {
		fullscreenAd.show();
	}

	@Override
	public void closeAd(int adID) {
		switch (adID) {
		case REMOVE_BOTTOM:
			handler.sendEmptyMessage(adID);
			break;
		case REMOVE_TOP:
			handler.sendEmptyMessage(adID);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean isFullScreenAdLoaded() {
		return (fullscreenAd.isLoaded());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(sc != null)
			unbindService(sc);
	}

}
