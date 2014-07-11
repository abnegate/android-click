package jakebarnby.click;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jakebarnby.click.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main screen of Click app, displays main menu to user
 * @author Jake Barnby
 *
 */
public class MainActivity extends Activity {

	private static InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loadInterstitial();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		super.onPause();
	}
	

	
	/**
	 * Creates and loads an interstitial ad
	 */
	private void loadInterstitial() {
		if (interstitial == null) {
			interstitial = new InterstitialAd(this);
			interstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
			interstitial.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					//Can only get here if user pressed quit menu button
					finish();
					System.exit(0);
				}
			});
			// Load the ad
			interstitial.loadAd(new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build());
		}
	}
	
	/*
	 * BUTTON RESPONSE METHODS--------------
	 */

	/**
	 * Response to newgame button, starts a new activity that runs the game.
	 * @param view - The view that this method was called from
	 */
	public void newGame(View view) {
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		startActivity(intent);
	}

	/**
	 * Response to highscore button, starts a new dialog that displays the high scores.
	 * @param view - The view this method was called from
	 */
	public void highScore(View view) {
		final Dialog highScoreDialog = new FadeDialog(new Dialog(this), R.layout.dialog_high_score).getDialog();
		if (!highScoreDialog.isShowing()) {
			// Set high score to the info textView of the dialog
			((TextView) highScoreDialog.findViewById(R.id.textView_dialogHSInfo)).
			setText(String.valueOf(getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0)));

			// Set listener for high highScore dialog close button
			((Button) highScoreDialog.findViewById(R.id.button_dialogHSBack)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					highScoreDialog.dismiss();
				}
			});
			highScoreDialog.show();
		}
	}

	/**
	 * Response to quit button, quits the application after loading and showing an ad
	 * @param view - The view this method was called from
	 */
	public void showInsertitial(View view) {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
}
