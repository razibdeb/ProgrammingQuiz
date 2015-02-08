package com.razibdeb.programmingquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import other.Utilities;

/**
 * Created by Razib Chandra Deb on 2/8/15.
 * Email: razibdeb@gmail.com
 */
public class MainActivity extends ActionBarActivity {

    Button signInButton;
    Button facebookSignInButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        signInButton = (Button) findViewById(R.id.buttonSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for testing purpose only
                try {
                    ParseObject testObject = new ParseObject("ProgrammingQuiz");
                    testObject.put("foo", "bar");
                    testObject.save();
                    Toast.makeText(getApplicationContext(),"Foo saved",Toast.LENGTH_SHORT).show();
                    Utilities.Log("Data Saved");
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Foo NOT saved" + e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    Utilities.Log("Data Not saved" + e.toString());
                }
            }
        });

        facebookSignInButton = (Button) findViewById(R.id.buttonFacebookSignIn);
        facebookSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> permissions = Arrays.asList( "user_about_me","user_relationships", "user_birthday", "user_location");
                //Arrays.asList("email", ParseFacebookUtils.Permissions.Friends.ABOUT_ME)
                ParseFacebookUtils.logIn(permissions,(MainActivity) context, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Utilities.Log("Uh oh. The user cancelled the Facebook login.");
                        }
                        else if (user.isNew())
                        {
                            Utilities.Log("User signed up and logged in through Facebook!");

                            // Fetch Facebook user info if the session is active
                            Session session = ParseFacebookUtils.getSession();
                            if (session != null && session.isOpened()) {
                                fetchFacebookUserData();
                            }

                            Intent intent = new Intent(context, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Utilities.Log("User logged in through Facebook!");


                            ParseUser currentUser = ParseUser.getCurrentUser();
                            if (currentUser != null)
                            {
                                Utilities.Log("Current Username is : " + currentUser.getUsername());
                            }




                            //show home activity
                           // Intent intent = new Intent(context, HomeActivity.class);
                           // startActivity(intent);
                        }
                    }
                });
            }
        });

    }

    private void fetchFacebookUserData() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser graphUser, Response response) {
                        if (graphUser != null) {
                            // Create a JSON object to hold the profile info
                            JSONObject userProfile = new JSONObject();
                            try {
                                // Populate the JSON object
                                userProfile.put("facebookId", graphUser.getId());
                                userProfile.put("name", graphUser.getName());
                                /*if (graphUser.getLocation().getProperty("name") != null) {
                                    userProfile.put("location", (String) graphUser
                                            .getLocation().getProperty("name"));
                                }*/
                                if (graphUser.getProperty("gender") != null) {
                                    userProfile.put("gender",(String) graphUser.getProperty("gender"));
                                }
                                if (graphUser.getBirthday() != null) {
                                    userProfile.put("birthday",graphUser.getBirthday());
                                }
                                if (graphUser.getProperty("relationship_status") != null) {
                                    userProfile.put("relationship_status",(String) graphUser.getProperty("relationship_status"));
                                }

                                // Save the graphUser profile info in a graphUser property
                                ParseUser currentUser = ParseUser.getCurrentUser();
                                currentUser.put("profile", userProfile);
                                currentUser.saveInBackground();

                                // Show the graphUser info
                                //updateViewsWithProfileInfo();
                            } catch (JSONException e) {
                                Utilities.Log("Error parsing returned graphUser data.");
                            }

                        } else if (response.getError() != null) {
                            if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
                                    || (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
                                Utilities.Log("The facebook session was invalidated.");
                            } else {
                                Utilities.Log("Some other error: "+ response.getError().getErrorMessage());
                            }
                        }
                    }
                });
        request.executeAsync();
    }

    /*
    *
    * {"birthday":"01/02/1989","facebookId":"10206348220643074","gender":"male","name":"Razib Chandra Deb","relationship_status":"Single"}
    * */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Utilities.Log("onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Utilities.Log("onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //from facebook
    @Override
    protected void onResume() {
        super.onResume();

        Utilities.Log("onResume");
        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
        try{
        /* Only activate FaceBook publish install if the user has the FaceBook app installed */
            if (com.facebook.Settings.getAttributionId(getContentResolver()) != null){
                com.facebook.AppEventsLogger.activateApp(this);
            }
        } catch (IllegalStateException e){
            Utilities.Log("Facebook Setting Exception again!");
        }
        Utilities.Log("onResume DONE");
    }
    @Override
    protected void onPause() {
        super.onPause();

        Utilities.Log("onPause");
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        Utilities.Log("onPause DONE");
    }

    //for facebook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utilities.Log("onActivityResult requestCode:" + requestCode + " ResultCode: " + resultCode);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
        Utilities.Log("onActivityResult DONE");
    }
}
