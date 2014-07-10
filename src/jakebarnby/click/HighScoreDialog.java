package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.jakebarnby.click.R;

/**
 * <code>FadeDialog</code> for displaying dialog with high score.
 * @author Jake
 *
 */

public class HighScoreDialog extends FadeDialog {

	private int highScore;

	public HighScoreDialog(Activity activity, int layoutResId, int score) {
		super(activity, layoutResId);
		this.highScore = score;
	}

	// Getters and setters----------
	public int getScore() {
		return this.highScore;
	}

	public void setScore(int highScore) {
		this.highScore = highScore;
	}

	// ------------------------------

	@Override
	public void showDialog() {
		// Get the dialog object
		final Dialog dialog = getDialog();

		if (!dialog.isShowing()) {
			// Set high score to the info textView of the dialog
			((TextView) dialog.findViewById(R.id.textView_dialogHSInfo)).setText(String.valueOf(highScore));

			// Set listener for high highScore dialog close button
			((Button) dialog.findViewById(R.id.button_dialogHSBack)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}
}
