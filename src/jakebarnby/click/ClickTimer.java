package jakebarnby.click;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

public class ClickTimer extends CountDownTimer {
	

	private Activity activity;
	private boolean finished = false;

	public boolean isFinished() {
		return finished;
	}

	public ClickTimer(long millisInFuture, long countDownInterval, Activity current) {
		super(millisInFuture, countDownInterval);
		this.activity = current;
	}

	@Override
	public void onTick(long millisUntilFinished) {
			 TextView time = (TextView) activity.findViewById(R.id.textView_time);
	         time.setText(String.valueOf(millisUntilFinished/1000));
	     }

	@Override
	public void onFinish() {
		 finished  = true;
		 if (activity.hasWindowFocus()) {
			 new GameOverDialog(activity, R.layout.dialog_game_over, GameActivity.getCount()).showDialog();
		 }
	}
}
