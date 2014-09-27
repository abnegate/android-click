package jakebarnby.click;

import com.jakebarnby.click.R;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

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

	private StartAppAd startAppAd = new StartAppAd(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		super.onCreate(savedInstanceState);
		StartAppSDK.init(this, "109453066", "209703184", true);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		startAppAd.onResume();
	}
	
	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		super.onPause();
		startAppAd.onPause();
	}
	
	/*
	 * BUTTON RESPONSE METHODS--------------
	 */
	
	@Override
	public void onBackPressed() {
		startAppAd.showAd(); // show the ad
		startAppAd.loadAd(); // load the next ad
		finish();
	}

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
		startAppAd.showAd(); // show the ad
		startAppAd.loadAd(); // load the next ad
		finish();
	}
}
