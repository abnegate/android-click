package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.jakebarnby.click.R;

/**
 * <code>FadeDialog</code> for displaying dialog with game over information.
 * @author Jake Barnby
 *
 */

public class GameOverDialog extends FadeDialog {

	private int count;

	// Getters and setters----------
	public void setCount(int count) {
		this.count = count;
	}
	
	// ------------------------------

	public GameOverDialog(Context context, int layoutResId, int count) {
		super(context, layoutResId);
		this.count = count;
	}

	/**
	 * Creates then shows a fade dialog that displays game over information
	 */
	public void showDialog() {
		//Check the current high score
		checkHighScore();
		// Get the dialog object
		Dialog dialog = getDialog();

		if (!dialog.isShowing()) {
			// Set values to containers
			TextView info = (TextView) dialog.findViewById(R.id.textView_dialogGOInfo);
			info.setText("Your score: "
					+ count
					+ "\nHigh score: "
					+ getContext().getSharedPreferences("highScores", Context.MODE_PRIVATE).getInt("highScore", 0)
					+ "\nClicks per second: " + (float) count / 5);
			setButtonListeners(dialog);
			dialog.show();
		}
	}
	
	/**
	 * Check if the current score is better than the current high score
	 */
	private void checkHighScore() {
		// Get the current high score
		SharedPreferences prefs = getContext().getSharedPreferences("highScores", Context.MODE_PRIVATE);
		int score = prefs.getInt("highScore", 0);
		// If current score is greater than high score commit the new score
		if (count > score) {
			Editor editor = prefs.edit();
			editor.putInt("highScore", count);
			editor.apply();
		}
	}
	
	/**
	 * Sets listeners for the buttons of the game over dialog
	 * @param dialog - The dialog parent of the buttons
	 */
	private void setButtonListeners(final Dialog dialog) {
		((Button) dialog.findViewById(R.id.button_dialogGOClose)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Close button pressed, return user to main activity
				((Activity) getContext()).finish();
				dialog.dismiss();
			}
		});

		((Button) dialog.findViewById(R.id.button_dialogGONewgame)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//New game button pressed, reset game activity
				((Activity) getContext()).setContentView(R.layout.activity_new_game);
				GameActivity.setCount(0);
				dialog.dismiss();
			}
		});
		
		//If user presses back button while the dialog has focus, implement
		//same functionality as new game button
		dialog.setOnKeyListener(new Dialog.OnKeyListener() {
			@Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                	((Activity) getContext()).setContentView(R.layout.activity_new_game);
    				GameActivity.setCount(0);
    				dialog.dismiss();
                }
                return true;
            }
        });
	}	
}