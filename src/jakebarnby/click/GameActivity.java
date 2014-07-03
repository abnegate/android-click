package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameActivity extends Activity {

	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
			this.count++;
			TextView clicks = (TextView) findViewById(R.id.textView_clickcount);
			clicks.setText("Clicks: " + count);
		} else{
			showDialog();
		}
	}

	
	/**
	 * Show a game over dialog with stats of the game
	 */
	private void showDialog() {
		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		//Removing the title of the dialog so custom one can be set
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//Set custom layout to dialog
		dialog.setContentView(R.layout.dialog);
		//Dim the activity in the background
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		//Set the info
		TextView info = (TextView) dialog.findViewById(R.id.textView_dialogInfo);
		info.setText("Your score: " + count + "\nHigh score: \nClicks per second: " + (float) count / 5);
		dialog.show();
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
