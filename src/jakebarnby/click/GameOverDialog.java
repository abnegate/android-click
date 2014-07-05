package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Extension of custom dialog provided for a game over dialog
 * @author Jake
 *
 */

public class GameOverDialog extends CustomDialog {

	private int count;

	public GameOverDialog(Activity activity, int layoutResId, int count) {
		super(activity, layoutResId);
		this.count = count;
	}

	/**
	 * Creates a custom dialog then displays information about the current game
	 */
	public void showDialog() {
		//Check the current high score
		checkHighScore();
		// Get the dialog object
		CustomDialog dialogObj = this;
		Dialog dialog = dialogObj.getDialog();

		if (!dialog.isShowing()) {
			// Set dim for the activity behind the dialog
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.dimAmount = 0.8f; // Dim level. 0.0 - no dim, 1.0 - completely
									// opaque
			dialog.getWindow().setAttributes(lp);

			// Set values to containers
			TextView info = (TextView) dialog
					.findViewById(R.id.textView_dialogGOInfo);
			info.setText("Your score: "
					+ count
					+ "\nHigh score: "
					+ getActivity().getSharedPreferences("highScores",
							Context.MODE_PRIVATE).getInt("highScore", 0)
					+ "\nClicks per second: " + (float) count / 5);
			setButtonListeners(dialog);
			dialog.show();
		}
	}
	
	private void checkHighScore() {
		// Get the current high score
		SharedPreferences prefs = getActivity().getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int score = prefs.getInt("highScore", 0);
		// If current score is greater than high score commit the new score
		if (count > score) {
			Editor editor = prefs.edit();
			editor.putInt("highScore", count);
			editor.commit();
		}
	}
	
	/**
	 * Sets listeners for the buttons of the game over dialog
	 * 
	 * @param dialog
	 *            - The dialog parent of the buttons
	 */
	private void setButtonListeners(final Dialog dialog) {
		Button close = (Button) dialog.findViewById(R.id.button_dialogGOBack);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				getActivity().finish();
			}
		});

		Button restart = (Button) dialog
				.findViewById(R.id.button_dialogGORestart);
		restart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				getActivity().recreate();
			}
		});

	}
	
	
	
}
