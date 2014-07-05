package jakebarnby.click;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameActivity extends Activity {

	private AdView banner;
	private static int count = 0;

	public static int getCount() {
		return count;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_new_game);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//Code for ad at top of game activity
		// Look up the AdView as a resource and load a request.
		banner = (AdView) this.findViewById(R.id.adView);
		banner.setAdListener(new MyAdListener(this));
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build();
		banner.loadAd(adRequest);
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
		if (banner != null) {
			banner.resume();
		}
		count = 0;
	}

	@Override
	public void onPause() {
		if (banner != null) {
			banner.pause();
		}
		super.onPause();
	}

	/** Called before the activity is destroyed. */
	@Override
	public void onDestroy() {
		// Destroy the AdView.
		if (banner != null) {
			banner.destroy();
		}
		super.onDestroy();
	}

	/**
	 * Main game loop
	 * 
	 * @param view
	 */
	public void updateCount(View view) {
		TextView time = (TextView) findViewById(R.id.textView_time);
		if (count == 0) {
			// First click, start the count-down timer
			new ClickTimer(5000, 1000, this).start();

			time.setTextSize(100);
		}
		if (!time.getText().equals("Out of time!")) {
			// Check if timer is zero therefore no more clicks should be counted
			GameActivity.count++;
			TextView clicks = (TextView) findViewById(R.id.textView_clickcount);
			clicks.setText("Clicks: " + count);
		}
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
