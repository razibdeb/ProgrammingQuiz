package com.razibdeb.other;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
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
        Log.d("razib", "QuizApplication Called before oncreate");
        super.onCreate();

        try {

            // Initialize Crash Reporting.
            ParseCrashReporting.enable(this);

            // Enable Local Datastore.
            //Parse.enableLocalDatastore(this);

            // Add your initialization code here
            Parse.initialize(this, "Skt8g8VWTZr83LxN3KwN95DCXfBgf4U4snnZuo7v", "sVqUWNo98thyCSYcdzziVRf3SEkm8kwSSSn66qzP");

            //ParseUser.enableAutomaticUser();
            //ParseACL defaultACL = new ParseACL();
            // Optionally enable public read access.
            // defaultACL.setPublicReadAccess(true);
            //ParseACL.setDefaultACL(defaultACL, true);
            Log.d("razib","QuizApplication: Parse Initialized");
            //  Parse.initialize(this, "MkQW1Ekz81HipGeAchKttJqfZPjwakB762ArwQtC", "Rx7ioN2vY04gTxLwhgmTBdXTKFCv05itfoeEbsPJ");
            Toast.makeText(getApplicationContext(), "Parse initialized", Toast.LENGTH_SHORT).show();

            ParseInstallation.getCurrentInstallation().saveInBackground();
            ParseFacebookUtils.initialize("1383324741980577");
            Log.d("razib","Parse Initialized");
        }
        catch (Exception e)
        {
            Log.d("razib","Parse NOT Initialized");
            Toast.makeText(getApplicationContext(),"Parse not initialized",Toast.LENGTH_LONG).show();
        }

        Log.d("razib", "QuizApplication Called");
    }
}
