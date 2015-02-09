package other;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Razib Chandra Deb on 2/8/15.
 * Email: razibdeb@gmail.com
 */
public class Utilities {
    enum LOG_TYPE{
        ERROR,
        DEBUG,
        INFO
    }
    public static final String TAG = "com.razibdeb.programmingquiz";

    public static void Log(String message)
    {
        Log.d(TAG,message);
    }

    public static void Log(String message, LOG_TYPE type)
    {
        Log.d(TAG,message);
    }

    public static void makeToast(String message, Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    //to show progress dialogue
    public static void showProgressDialogue(Context context, ProgressDialog progressDialog) {
        progressDialog = ProgressDialog.show(context, "Please wait ...", "Fetching data", true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
