package jakebarnby.click;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import com.jakebarnby.click.R;

/**
 * A dialog that fades in and out with the title section removed and is inflated with a custom layout
 * @author Jake Barnby
 *
 */

public abstract class FadeDialog {

	private Context context;
	private int layoutResId;

	public FadeDialog(Context activity, int layoutResId) {
		this.context = activity;
		this.layoutResId = layoutResId;
	}

	// Getters and setters----------
	public Context getContext() {
		return context;
	}
	
	// ------------------------------
	
	/**
	 * Show the fade dialog
	 */
	public abstract void showDialog();

	/**
	 * Create and return the custom dialog
	 */
	public Dialog getDialog() {
		// Create custom dialog object
		Dialog dialog = new Dialog(context);
		// Removing the title of the dialog so custom one can be set
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Set custom layout to dialog
		dialog.setContentView(layoutResId);
		//Set the dim level of the activity behind
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.8f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
		dialog.getWindow().setAttributes(lp);
		// Set fade in/out animation
		dialog.getWindow().getAttributes().windowAnimations = R.style.FadeDialogAnimation;
		return dialog;
	}
}
