package jakebarnby.click;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jakebarnby.click.R;

/**
 * Game screen of Click app, provides the user with interface for playing the game.
 * @author Jake Barnby
 *
 */
public class GameActivity extends Activity {
	
	private static int count = 0;

	private ClickTimer timer;

	private AdView adView;
	private AdRequest adRequest;


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

		// Create timer for count down
		timer = new ClickTimer(5000, 1000, this);
		// Create a request for an ad
		adRequest = new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build();

		setContentView(R.layout.activity_new_game);
	}
	
	/**
	 * Find adView then load and show the ad.
	 */
	private void setupBannerAd() {
		adView = (AdView) findViewById(R.id.adView);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				setContentView(R.layout.activity_new_game);
			}
		});
		// Build and show an ad from the previously created request
		adView.loadAd(adRequest);
	}

	/**
	 * Response to clickbutton button, updates count and attached text view.
	 * @param view - The view this method was called from
	 */
	public void updateCount(View view) {
		if (count == 0) {
			// First click, reset and start the timer
			timer.reset();
			timer.start();
			// Set size of timer textView
			TextView time = (TextView) findViewById(R.id.textView_timer);
			time.setTextSize(115);
		}
		// If timer isn't finished increase count and update time
		if (!timer.isFinished()) {
			GameActivity.count++;
			TextView clicks = (TextView) findViewById(R.id.textView_clickcount);
			clicks.setText("Clicks: " + count);
		}
	}

	/*
	 * ACTIVITY RESPONSE METHODS--------------
	 */
	
	@Override
	public void onContentChanged() {
		setupBannerAd();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Resume ad adView
		if (adView != null) {
			adView.resume();
		}
		count = 0;
	}

	@Override
	public void onPause() {
		// Pause ad adView
		if (adView != null) {
			adView.pause();
		}
		super.onPause();

	}

	@Override
	public void onDestroy() {
		// Destroy ad adView
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}
}
