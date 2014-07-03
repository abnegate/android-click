package jakebarnby.click;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

public class ClickTimer extends CountDownTimer {
	

	private Activity current;

	public ClickTimer(long millisInFuture, long countDownInterval, Activity current) {
		super(millisInFuture, countDownInterval);
		this.current = current;
	}

	@Override
	public void onTick(long millisUntilFinished) {
			 TextView time = (TextView) current.findViewById(R.id.textView_time);
	         time.setText(String.valueOf(millisUntilFinished/1000));
	     }

	@Override
	public void onFinish() {
		 TextView time = (TextView) current.findViewById(R.id.textView_time);
         time.setText("Out of time!");
	}
	


}
