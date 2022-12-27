package kg.black13.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import kg.black13.game.screens.GameOverScreen;

public class AndroidLauncher extends AndroidApplication implements GameOverScreen.Callback {
	private InterstitialAd mInterstitialAd;
	private static final int ACTION_ADMOB_IS_LOADED = 1;
	private Handler mHandler;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		MainClass mainClass = new MainClass(this);
		initialize(mainClass, config);
		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

		mHandler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message message) {
				switch (message.what) {
					case ACTION_ADMOB_IS_LOADED:
						Log.i("TAG", "onAdLoaded HAHAHAHAHHAHAHAHAHHAHAHA");
						loadAd();
						break;
				}


			}
		};

	}

	public void loadAd(){
		AdRequest adRequest = new AdRequest.Builder().build();
		InterstitialAd.load(this,"ca-app-pub-2230097402282612/1289077118", adRequest,
				new InterstitialAdLoadCallback() {
					@Override
					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
						// The mInterstitialAd reference will be null until
						// an ad is loaded.
						mInterstitialAd = interstitialAd;
						Log.i("TAG", "onAdLoaded");
						showAd();
					}

					@Override
					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
						// Handle the error
						Log.i("TAG", "onAdLoaded Failed " + loadAdError);
						mInterstitialAd = null;
					}
				});
	}

	private void showAd(){
		if (mInterstitialAd != null) {
			mInterstitialAd.show(AndroidLauncher.this);
		} else {
			Log.d("TAG", "The interstitial ad wasn't ready yet.");
		}
	}
	@Override
	public void callingBack() {
		Message message = mHandler.obtainMessage(ACTION_ADMOB_IS_LOADED,null);
		message.sendToTarget();
//		loadAd();
	}
}
