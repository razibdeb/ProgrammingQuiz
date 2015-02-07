package other;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;
/**
 * Created by Razib Chandra Deb on 2/8/15.
 * Email: razibdeb@gmail.com
 */


/***
 * This class has been added to perform Parse initialize
 */
public class QuizApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, Constants.PARSE_APP_ID, Constants.PARSE_CLIENT_KEY);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
