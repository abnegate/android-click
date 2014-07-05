package jakebarnby.click;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

public class ClickTimer extends CountDownTimer {
	

	private Activity activity;
	private GameOverDialog gameOverDialog;
	private boolean finished = false;

	public boolean isFinished() {
		return finished;
	}

	public ClickTimer(long millisInFuture, long countDownInterval, Activity current) {
		super(millisInFuture, countDownInterval);
		this.activity = current;
		this.gameOverDialog = new GameOverDialog(activity, R.layout.dialog_game_over, GameActivity.getCount());
	}

	@Override
	public void onTick(long millisUntilFinished) {
			TextView time = (TextView) activity.findViewById(R.id.textView_time);
	        time.setText(String.valueOf(millisUntilFinished/1000));
	     }

	@Override
	public void onFinish() {
		 finished  = true;
		 TextView time = (TextView) activity.findViewById(R.id.textView_time);
         time.setText(String.valueOf(0));
		 if (activity.hasWindowFocus()) {
			 gameOverDialog.setCount(GameActivity.getCount());
			 gameOverDialog.showDialog();
		 } 
	}
	
	/**
	 * Resets the timer
	 */
	public void reset() {
		this.finished = false;
	}
	
}
