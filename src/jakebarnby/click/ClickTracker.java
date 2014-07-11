package jakebarnby.click;

import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.jakebarnby.click.R;

import android.app.Application;

/**
 * Single application <code>Tracker</code> for analytics 
 * @author Jake Barnby
 *
 */
public class ClickTracker extends Application {
    Tracker appTracker = GoogleAnalytics.getInstance(this).newTracker(R.xml.tracker);

    public ClickTracker() {
        super();
        GoogleAnalytics.getInstance(this).setDryRun(true);
        GoogleAnalytics.getInstance(this).getLogger().setLogLevel(LogLevel.VERBOSE);
    }

    /**
     * Returns the created tracker
     * @return - The created tracker
     */
    synchronized Tracker getTracker() {
        return appTracker;
    }
}
