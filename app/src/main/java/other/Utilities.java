package other;

import android.util.Log;

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
}
