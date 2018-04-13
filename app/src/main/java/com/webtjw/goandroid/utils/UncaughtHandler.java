package com.webtjw.goandroid.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;

public class UncaughtHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "wayne UncaughtHandler";

    private Context context;
    private Thread.UncaughtExceptionHandler defaultUncaughtHandler;
    private static UncaughtHandler uncaughtHandler = new UncaughtHandler();


    private UncaughtHandler () {
        defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (handleException(e)) {
            Process.killProcess(Process.myPid());
            System.exit(1); // 0 表示正常退出，1 表示异常
        } else {
             defaultUncaughtHandler.uncaughtException(t, e);
        }
    }

    // 因此要用单例模式
    public static void set (Context context) {
        uncaughtHandler.context = context;
        Thread.setDefaultUncaughtExceptionHandler(uncaughtHandler);
    }

    private boolean handleException (Throwable exception) {
        String timeString = Utils.getFormatTime();
        String logString = timeString + "\n===========>>>>>>>>>>>> information about device and App:\n";

        HashMap<String, String> deviceInfosMap = collectDeviceInfos(context);
        for (Map.Entry<String, String> entry : deviceInfosMap.entrySet()) {
            logString += entry.getKey() + " = " + entry.getValue() + "\n";
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        String errorMessage = stringWriter.toString();
        logString += "===========>>>>>>>>>>>> exception cause information:\n" + errorMessage;

        Utils.saveStringToFile(Environment.getExternalStorageDirectory() + "/tanjiawei/crash/" + timeString, logString);
        return false;
    }

    private HashMap<String, String> collectDeviceInfos (Context ctx) {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);

            hashMap.put("versionName", packageInfo.versionName);
            hashMap.put("versionCode", Integer.toString(packageInfo.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }


        return hashMap;
    }
}
