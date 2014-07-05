package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Extension of custom dialog for displaying high score
 * @author Jake
 *
 */

public class HighScoreDialog extends CustomDialog {
	
	private int highScore;

	public HighScoreDialog(Activity activity, int layoutResId, int score) {
		super(activity, layoutResId);
		this.highScore = score;
	}

	@Override
	public void showDialog() {
		//Get the dialog object
		CustomDialog dialogObj = this;
		final Dialog dialog = dialogObj.getDialog();

		
		if (!dialog.isShowing()) {
			//Set dim for the activity behind the dialog
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
			lp.dimAmount=0.8f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
			dialog.getWindow().setAttributes(lp);
			
			//Set values to containers
			TextView info = (TextView) dialog.findViewById(R.id.textView_dialogHSInfo);
			info.setText("" + highScore);
			info.setTextSize(50);
			
			//Set listener for high highScore dialog button
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
}
