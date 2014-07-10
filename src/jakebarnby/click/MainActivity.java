package jakebarnby.click;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jakebarnby.click.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Main screen of Click app, displays main menu to user
 * @author Jake Barnby
 *
 */
public class MainActivity extends Activity {

	private InterstitialAd interstitial;

	private HighScoreDialog highScoreDialog;
	private int highScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_main);
		
		// Get current high score and create a new dialog for later use
		setupHighScore();
		// Create new interstitial ad and set listener to exit the app when the ad is closed
		setupInterstitialAd();
	}
	
	/**
	 * Store the current high score and new high score dialog
	 */
	private void setupHighScore() {
		this.highScore = getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0);
		this.highScoreDialog = new HighScoreDialog(this, R.layout.dialog_high_score, highScore);
	}

	/**
	 * Create and load an interstitial ad
	 */
	private void setupInterstitialAd() {
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getResources().getString(R.string.admob_interstitial_id));
		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				//Can only get here if user pressed quit
				finish();
				System.exit(0);
			}
		});
		// Load the ad for showing later
		interstitial.loadAd(new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build());
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		// Make sure the dialog has the most recent high score 
		int newHighScore = getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0);
		if (highScore < newHighScore) {
			highScore = newHighScore;
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
		highScoreDialog.setScore(highScore);
		highScoreDialog.showDialog();
	}

	/**
	 * Response to quit button, quits the application after showing an ad
	 * @param view - The view this method was called from
	 */
	public void showInsertitial(View view) {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}
}
