package com.razibdeb.programmingquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

import other.Utilities;


public class HomeActivity extends ActionBarActivity {

    TextView welcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTextView = (TextView) findViewById(R.id.TextViewWelcome);

        if (ParseUser.getCurrentUser().getUsername() != null)
            welcomeTextView.setText("Welcome " + ParseUser.getCurrentUser().getUsername().toString());
        else
            welcomeTextView.setText("Welcome USER NOT FOUND");


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null)
        {
            Utilities.Log("Current Username is : " + currentUser.getUsername());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
