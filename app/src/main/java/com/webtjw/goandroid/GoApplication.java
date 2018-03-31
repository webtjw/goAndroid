package com.webtjw.goandroid;

import android.app.Application;
import android.content.Context;

import com.webtjw.goandroid.utils.UncaughtHandler;


public class GoApplication extends Application {

    private static final String TAG = "GoApplication";

    private static GoApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        // exception uncaughthandler
        UncaughtHandler.set(app.getApplicationContext());
    }

    public static Context getContext () {
        return app.getApplicationContext();
    }
}
