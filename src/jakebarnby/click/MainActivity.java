package jakebarnby.click;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
	
    private InterstitialAd interstitial;
    
	private HighScoreDialog highScoreDialog;
	private int highScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		this.highScore = this.getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0);
		this.highScoreDialog = new HighScoreDialog(this, R.layout.dialog_high_score, highScore);
		
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.admob_insertitial_id));
        interstitial.setAdListener(new MyAdListener(this) {
        	@Override
        	public void onAdClosed() {
        		finish();
        		System.exit(0);
        	}
        });
		interstitial.loadAd(new AdRequest.Builder().addTestDevice("C6B56C5E1BAA0F338C091FC79F9289C2").build());
	}
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		this.highScore = this.getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	/**
	 * Starts a new activity that runs the game.
	 */
	public void newGame(View view) {
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		this.startActivity(intent);
	}
	
	/**
	 * Starts a new dialog that displays the high scores
	 */
	public void highScore(View view) {
		highScoreDialog.setScore(highScore);
		highScoreDialog.showDialog();
	}
	
	/**
	 * Quits the application after showing an ad
	 */
	public void showInsertitial(View view) {
        if (interstitial.isLoaded()) {
            interstitial.show();
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
	}

}
