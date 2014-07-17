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
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jakebarnby.click.R;

/**
 * Game screen of Click app, provides the user with interface for playing the game.
 * @author Jake Barnby
 *
 */
public class GameActivity extends Activity {
	
	static final String GALAXYS3_TEST_ID = "C6B56C5E1BAA0F338C091FC79F9289C2";
	static final String GALAXYNOTE10_TEST_ID = "19E4C25A726D8BF8B7C32668C35CE52B";
	
	private static final long COUNTDOWN_TIME = 5900;
	private static int score = 0;
	
	private CountDownTimer timer;
	
	private AdView adView;

	// Getters and setters----------
	public static int getScore() {
		return score;
	}

	public static void setScore(int newScorre) {
		score = newScorre;
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
	 * Response to clickbutton button, updates score and attached text view.
	 * @param view - The view this method was called from
	 */
	public void updateCount(View view) {
		if (score == 0) {
			// First click, start a new timer
			startTimer();
		}
		if (!((TextView)  findViewById(R.id.textView_timer)).getText().equals("0")) {
		GameActivity.score++;
		((TextView) findViewById(R.id.textView_clickcount)).setText("Clicks: " + score);
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
		score = 0;
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
	    // Create an ad.
		if (adView == null) {
			adView = (AdView) findViewById(R.id.adView);
		    adView.loadAd(new AdRequest.Builder().addTestDevice(GameActivity.GALAXYNOTE10_TEST_ID).addTestDevice(GALAXYS3_TEST_ID).addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
		}
	}
	
	/**
	 * Kill AdView thread
	 * +
	 */
	protected void stopAds() {
		if (adView != null) {
			adView.destroy();
			adView = null;
		}
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
					//Update the score down textView with the new time remaining
					timer.setText(Long.toString(millisUntilFinished/1000));
					
				}

				@Override
				public void onFinish() {
					//Set the score down textView to display 0
					((TextView) findViewById(R.id.textView_timer)).
					setText(String.valueOf(0));
					if (hasWindowFocus()) {
						showGameOverDialog();
					} else {
						stopTimer();
						resetText();
						GameActivity.setScore(0); 
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
	 * Check if the current score is better than the current high score
	 */
	private void checkHighScore() {
		// Get the current high score
		SharedPreferences prefs = getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int highScore = prefs.getInt("highScore", 0);
		// If current score is greater than high score commit the new score
		if (GameActivity.score > highScore) {
			Editor editor = prefs.edit();
			editor.putInt("highScore", score);
			editor.commit();
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
					+ score
					+ "\nHigh score: "
					+ getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0)
					+ "\nClicks per second: " + (float) score / 5);
			setDialogButtonListeners(dialog);
			dialog.show();
		}
	}
	
	/**
	 * Resets <code>TextView's</code> to their initial states for a new game
	 */
	private void resetText() {
		TextView startClicking = (TextView) findViewById(R.id.textView_timer);
		startClicking.setText(R.string.start_clicking);
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
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
				GameActivity.setScore(0);
				dialog.dismiss();
			}
		});
		
		//If user presses back button while the dialog has focus, implement same functionality as new game button
		dialog.setOnKeyListener(new Dialog.OnKeyListener() {
			@Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                	stopTimer();
                	resetText();
    				GameActivity.setScore(0);
    				dialog.dismiss();
                }
                return true;
            }
        });
	}
}
