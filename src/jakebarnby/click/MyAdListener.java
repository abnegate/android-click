package jakebarnby.click;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

public class MyAdListener extends AdListener {
	 
	private Activity activity;
	
	public MyAdListener(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onAdClosed() {
		activity.setContentView(R.layout.activity_new_game);
	}

	@Override
    public void onAdFailedToLoad(int errorCode) {
        String errorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        Toast.makeText(activity, String.format("onAdFailedToLoad(%s)", errorReason),
                Toast.LENGTH_SHORT).show();
    }
}
