package ua.org.zasadnyy.partybudget.util;

import android.util.Log;

/**
 * Created by vitaliyzasadnyy on 29.07.13.
 */
public final class ZLog {

    public static final String TAG = "party";

    public static void i(Object msg) {
        Log.i(TAG, msg.toString());
    }

    public static void w(Object msg) {
        Log.w(TAG, msg.toString());
    }

    public static void w(Object msg, Throwable throwable) {
        Log.w(TAG, msg.toString(), throwable);
    }

    public static void e(Object msg) {
        Log.e(TAG, msg.toString());
    }

    public static void e(Object msg, Throwable throwable) {
        Log.e(TAG, msg.toString(), throwable);
    }
}
