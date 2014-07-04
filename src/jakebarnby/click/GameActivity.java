package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {

	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
		} else {
			//Timer has run out, finish the game
			checkHighScore();
			gameOverDialog();
		}
	}
	
	/**
	 * Creates a custom dialog then displays information about the current game
	 */
	private void gameOverDialog() {
		//Get the dialog object
		CustomDialog dialogObj = new CustomDialog(this, R.layout.dialog_game_over);
		Dialog dialog = dialogObj.getDialog();
		
		if (!dialog.isShowing()) {
			//Set dim for the activity behind the dialog
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
			lp.dimAmount=0.8f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
			dialog.getWindow().setAttributes(lp);
			
			//Set values to containers
			TextView info = (TextView) dialog.findViewById(R.id.textView_dialogGOInfo);
			info.setText("Your score: "
					+ count
					+ "\nHigh score: "
					+ this.getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0)
					+ "\nClicks per second: " + (float) count / 5);
			setButtonListeners(dialog);	
			dialog.show();
		}
	}

	/**
	 * Sets listeners for the buttons of the game over dialog
	 * @param dialog - The dialog parent of the buttons
	 */
	private void setButtonListeners(final Dialog dialog) {
		Button close = (Button) dialog.findViewById(R.id.button_dialogGOBack);
		close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
		
		Button restart = (Button) dialog.findViewById(R.id.button_dialogGORestart);
		restart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				recreate();
			}
		});
		
	}

	private void checkHighScore() {
		//Get the current high score
		SharedPreferences prefs = this.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int score = prefs.getInt("highScore", 0);
		//If current score is greater than high score commit the new score
		if (count > score) {
			Editor editor = prefs.edit();
			editor.putInt("highScore", count);
			editor.commit();
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
