package com.webtjw.goandroid;

import android.app.Application;
import android.content.Context;

/**
 * Created by webtj on 2017/12/28.
 */

public class GoApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
