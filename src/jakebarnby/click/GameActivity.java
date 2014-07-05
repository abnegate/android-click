package jakebarnby.click;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GameActivity extends Activity {

	private ClickTimer timer;
	
	private AdView adView;
	private MyAdListener adListener;
	private AdRequest adRequest;
	
	private static int count = 0;

	public static int getCount() {
		return count;
	}

	public static void setCount(int newCount) {
		count = newCount;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		timer = (ClickTimer) new ClickTimer(5000, 1000, this);
		adListener = new MyAdListener(this);
		adRequest = new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build();
		
		setContentView(R.layout.activity_new_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	
	@Override
	public void onContentChanged() {
		setupAds();
	}

	/**
	 * Run from button_clickbutton onClick, serves as main game loop
	 * 
	 * @param view
	 */
	public void updateCount(View view) {
		if (count == 0) {
			// First click, start the count-down timer
			timer.reset();
			timer.start();
			TextView time = (TextView) findViewById(R.id.textView_time);
			time.setTextSize(115);
		}
		if (!timer.isFinished()) {
			// Timer isn't finished so increase count and update text
			GameActivity.count++;
			TextView clicks = (TextView) findViewById(R.id.textView_clickcount);
			clicks.setText("Clicks: " + count);
		}
	}

	private void setupAds() {
		// Look up the AdView as a resource and load a request.
		adView = (AdView) this.findViewById(R.id.adView);
		// Set the custom ad listener
		adView.setAdListener(adListener);
		// Build an ad request
		adView.loadAd(adRequest);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_game,
					container, false);
			return rootView;
		}
	}

}
