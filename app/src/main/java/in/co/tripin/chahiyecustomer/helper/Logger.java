package in.co.tripin.chahiyecustomer.helper;

import android.util.Log;




public class Logger {
    private static final String TAG = "ChaiYeh";

    public static void d(String string) {
        //if (BuildConfig.LOG) {
            Log.d(TAG, string);
        //}
    }

    public static void v(String string) {
       // if (BuildConfig.LOG) {
            Log.v(TAG, string);
      //  }
    }

    public static void i(String string) {
      //  if (BuildConfig.LOG) {
            Log.i(TAG, string);
      //  }
    }

    public static void e(String string) {
       // if (BuildConfig.LOG) {
            Log.e(TAG, string);
       // }
    }

    public static void w(String string) {
      //  if (BuildConfig.LOG) {
            Log.w(TAG, string);
      //  }
    }
}
