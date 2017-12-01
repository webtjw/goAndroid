package com.webtjw.goandroid.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webtj on 2017/12/1.
 * 用于管理栈
 */

public class RouteHandle {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity (Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity (Activity activity) {
        activities.remove(activity);
    }

    public static void removeAll () {
        for (Activity activity: activities) {
            if (!activity.isFinishing()) activity.finish();
        }
    }
}
