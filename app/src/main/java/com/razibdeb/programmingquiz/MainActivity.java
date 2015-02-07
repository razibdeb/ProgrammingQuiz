package com.razibdeb.programmingquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseObject;

/**
 * Created by Razib Chandra Deb on 2/8/15.
 * Email: razibdeb@gmail.com
 */
public class MainActivity extends ActionBarActivity {

    Button testButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        try {

          //  Parse.initialize(this, "MkQW1Ekz81HipGeAchKttJqfZPjwakB762ArwQtC", "Rx7ioN2vY04gTxLwhgmTBdXTKFCv05itfoeEbsPJ");
            Toast.makeText(getApplicationContext(),"Parse initialized",Toast.LENGTH_SHORT).show();
            Log.d("razib","Parse Initialized");
        }
        catch (Exception e)
        {
            Log.d("razib","Parse NOT Initialized");
            Toast.makeText(getApplicationContext(),"Parse not initialized",Toast.LENGTH_LONG).show();
        }

        testButton = (Button) findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                        ParseObject testObject = new ParseObject("ProgrammingQuiz");
                        testObject.put("foo", "bar");
                        testObject.save();
                        Toast.makeText(getApplicationContext(),"Foo saved",Toast.LENGTH_SHORT).show();
                        Log.d("razib","Data Saved");
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Foo NOT saved",Toast.LENGTH_SHORT).show();
                        Log.d("razib","Data Not saved");
                    }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
