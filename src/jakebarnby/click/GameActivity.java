package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jakebarnby.click.R;

/**
 * Game screen of Click app, provides the user with interface for playing the game.
 * @author Jake Barnby
 *
 */
public class GameActivity extends Activity {
	
	private static final long COUNTDOWN_TIME = 10000;
	private static int count = 0;
	
	private CountDownTimer timer;
	
	private AdView adView;

	// Getters and setters----------
	public static int getCount() {
		return count;
	}

	public static void setCount(int newCount) {
		count = newCount;
	}

	// -------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_new_game);
		
		setupBannerAd();
	}
	
	/*
	 * BUTTON RESPONSE METHODS--------------
	 */

	/**
	 * Response to clickbutton button, updates count and attached text view.
	 * @param view - The view this method was called from
	 */
	public void updateCount(View view) {
		if (count == 0) {
			// First click, start a new timer
			startTimer();
		}
		if (!((TextView)  findViewById(R.id.textView_timer)).getText().equals("0")) {
		GameActivity.count++;
		((TextView) findViewById(R.id.textView_clickcount)).setText("Clicks: " + count);
		}
	}

	/*
	 * ACTIVITY RESPONSE METHODS--------------
	 */

	@Override
	public void onResume() {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		super.onResume();
		// Resume ad adView
		if (adView != null) { adView.resume(); }
		count = 0;
	}

	@Override
	public void onPause() {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		// Pause ad adView
		if (adView != null) { adView.pause(); }
		//Stop CountDownTimer thread
		if (timer!=null) { timer.cancel(); timer = null; }
		super.onPause();

	}

	@Override
	public void onDestroy() {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		stopTimer();
		stopAds();
		super.onDestroy();
	}
	
	/*
	 * HELPER METHODS--------------
	 */
	
	/**
	 * Find adView then load and show the ad.
	 */
	private void setupBannerAd() {
		RelativeLayout adViewLayout = (RelativeLayout) findViewById(R.id.adView);
	    // Create an ad.
		if (adView == null) {
			adView = new AdView(getApplication());
		    adView.setAdUnitId("ca-app-pub-4043849132226838/4324173506");
		    adView.setAdSize(AdSize.SMART_BANNER);
		    // Start loading the ad in the background.
		    adView.loadAd(new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build());
		    // Add the AdView to the view hierarchy. The view will have no size until the ad is loaded.		
		}
		adViewLayout.addView(adView);
	}
	
	
	
	/**
	 * Create a new <code>CountDownTimer</code> to update textViews at 1 second intervals
	 * and display a <code>GameOverDialog</code> when finished
	 */
	private void startTimer() {
		if (timer == null) {
			timer = new CountDownTimer(COUNTDOWN_TIME, 1000) {
		
				@Override
				public void onTick(long millisUntilFinished) {
					TextView timer = (TextView) findViewById(R.id.textView_timer);
					timer.setTextSize(115);
					//Update the count down textView with the new time remaining
					timer.setText(String.valueOf(millisUntilFinished/1000));
					
				}

				@Override
				public void onFinish() {
					//Set the count down textView to display 0
					((TextView) findViewById(R.id.textView_timer)).
					setText(String.valueOf(0));
					if (hasWindowFocus()) {
						showGameOverDialog();
					} else {
						stopTimer();
						resetText();
						GameActivity.setCount(0); 
					}
				}
			};
		timer.start(); 
		}
	}
	
	/**
	 * Kill CountDownTimer thread
	 */
	protected void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	/**
	 * Kill AdView thread
	 */
	protected void stopAds() {
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
	}
		
	/**
	 * Create and show fade dialog with game over information
	 */
	protected void showGameOverDialog() {
		// Create custom dialog object
		Dialog dialog = new FadeDialog(new Dialog(this), R.layout.dialog_game_over).getDialog();

		if (!dialog.isShowing()) {
			checkHighScore();
			// Set values to containers
			TextView info = (TextView) dialog.findViewById(R.id.textView_dialogGOInfo);
			info.setText("Your score: "
					+ count
					+ "\nHigh score: "
					+ getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0)
					+ "\nClicks per second: " + (float) count / 5);
			setDialogButtonListeners(dialog);
			dialog.show();
		}
	}

	/**
	 * Check if the current score is better than the current high score
	 */
	private void checkHighScore() {
		// Get the current high score
		SharedPreferences prefs = getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int score = prefs.getInt("highScore", 0);
		// If current score is greater than high score commit the new score
		if (count > score) {
			Editor editor = prefs.edit();
			editor.putInt("highScore", count);
			editor.commit();
		}
	}
	
	private void resetText() {
		TextView startClicking = (TextView) findViewById(R.id.textView_timer);
		startClicking.setText(R.string.start_clicking);
		startClicking.setTextSize(65);
		((TextView) findViewById(R.id.textView_clickcount)).setText(R.string.initial_clicks);	
	}
	
	/**
	 * Sets listeners for the buttons of the game over dialog
	 * @param dialog - The dialog parent of the buttons
	 */
	private void setDialogButtonListeners(final Dialog dialog) {
		((Button) dialog.findViewById(R.id.button_dialogGOClose)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Close button pressed, return user to main activity
				Intent intent = new Intent(GameActivity.this, MainActivity.class);
				startActivity(intent);
				dialog.dismiss();
				finish();
			}
		});

		((Button) dialog.findViewById(R.id.button_dialogGONewgame)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//New game button pressed, reset game activity
				stopTimer();
				resetText();
				GameActivity.setCount(0);
				dialog.dismiss();
			}
		});
		
		//If user presses back button while the dialog has focus, implement
		//same functionality as new game button
		dialog.setOnKeyListener(new Dialog.OnKeyListener() {
			@Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                	stopTimer();
                	resetText();
    				GameActivity.setCount(0);
    				dialog.dismiss();
                }
                return true;
            }
        });
	}
}
