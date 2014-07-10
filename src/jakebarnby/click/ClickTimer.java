package jakebarnby.click;

import android.app.Activity;

/**
 * CountDownTimer that updates text views belonging to the given <code>Context</code>
 * @author Jake Barnby
 * 
 */
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.jakebarnby.click.R;

public class ClickTimer extends CountDownTimer {
	
	private boolean finished = false;
	
	private Context context;
	private GameOverDialog gameOverDialog;

	// Getters and setters----------
	public boolean isFinished() {
		return finished;
	}
	
	public void reset() {
		this.finished = false;
	}
	
	// ------------------------------

	public ClickTimer(long millisInFuture, long countDownInterval, Context context) {
		super(millisInFuture, countDownInterval);
		this.context = context;
		this.gameOverDialog = new GameOverDialog(context, R.layout.dialog_game_over, GameActivity.getCount());
	}

	@Override
	public void onTick(long millisUntilFinished) {
			//Update the count down textView with the new time remaining
			((TextView) ((Activity) context).findViewById(R.id.textView_timer)).
			setText(String.valueOf(millisUntilFinished/1000));
	     }

	@Override
	public void onFinish() {
		 finished  = true;
		 //Set the count down textView to display 0
		 ((TextView) ((Activity) context).findViewById(R.id.textView_timer)).
		 setText(String.valueOf(0));

		 if (((Activity) context).hasWindowFocus()) {
			 gameOverDialog.setCount(GameActivity.getCount());
			 gameOverDialog.showDialog();
		 } 
	}
}
