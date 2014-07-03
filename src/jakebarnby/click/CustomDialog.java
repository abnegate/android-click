package jakebarnby.click;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;

/**
 * Class that can provide a custom dialog
 * @author Jake
 *
 */

public class CustomDialog{
	
	private Activity activity;
	
	public CustomDialog(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * Create and return the custom dialog
	 */
	public Dialog getDialog() {
		// Create custom dialog object
		Dialog dialog = new Dialog(activity);
		// Removing the title of the dialog so custom one can be set
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set custom layout to dialog
		dialog.setContentView(R.layout.dialog_game_over);
		// Dim the activity in the background
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		return dialog;
	}

}
