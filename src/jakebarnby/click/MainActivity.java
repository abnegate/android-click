package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
		//Get the dialog object
		CustomDialog dialogObj = new CustomDialog(this, R.layout.dialog_high_score);
		final Dialog dialog = dialogObj.getDialog();

		
		if (!dialog.isShowing()) {
			//Set dim for the activity behind the dialog
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
			lp.dimAmount=0.8f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
			dialog.getWindow().setAttributes(lp);
			
			//Set values to containers
			TextView info = (TextView) dialog.findViewById(R.id.textView_dialogHSInfo);
			info.setText(""+this.getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0));
			info.setTextSize(50);
			
			//Set listener for high score dialog button
			Button close = (Button) dialog.findViewById(R.id.button_dialogHSBack);
			close.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}
	
	/**
	 * Quits the application
	 */
	public void quit(View view) {
		finish();
		System.exit(0);
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
