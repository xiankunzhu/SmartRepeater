package com.lefthand.smartrepeater.util;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class CommonUtils {

    private static final String LOG_TAG = CommonUtils.class.getSimpleName();

    /**
     * change ms to mm:ss string
     * @param ms
     * @return
     */
    public static String timeFormatMs2Str(int ms){
        String str;
        int minutes = (ms / 1000) / 60, seconds = (ms / 1000) % 60;
        if (minutes >= 10 && seconds >= 10) {
            str = "" + minutes + ":" + seconds;
        } else if (minutes >= 10 && seconds < 10) {
            str = "" + minutes + ":0" + seconds;
        } else if (minutes < 10 && seconds >= 10) {
            str = "0" + minutes + ":" + seconds;
        } else {
            str = "0" + minutes + ":0" + seconds;
        }
        return str;
    }

    /**
     * judge if service work or not
     * @param className
     * @param context
     * @return
     */
    public static boolean isServiceWorked(String className, Context context){
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningServiceInfo = (ArrayList<ActivityManager.RunningServiceInfo>) manager.getRunningServices(30);
        for (int i = 0; i < runningServiceInfo.size(); i++){
            if (runningServiceInfo.get(i).service.getClassName().toString().equals(className)) {
                Log.i(LOG_TAG, "isServiceWorked true");
                return true;
            }

        }
        Log.i(LOG_TAG, "isServiceWorked false");
        return false;
    }
}
