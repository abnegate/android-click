package jakebarnby.click;

import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;

import android.app.Application;

public class ClickTracker extends Application {
    Tracker appTracker = GoogleAnalytics.getInstance(this).newTracker(R.xml.tracker);

    public ClickTracker() {
        super();
        GoogleAnalytics.getInstance(this).setDryRun(true);
        GoogleAnalytics.getInstance(this).getLogger().setLogLevel(LogLevel.VERBOSE);
    }

    synchronized Tracker getTracker() {
        return appTracker;
    }
}
