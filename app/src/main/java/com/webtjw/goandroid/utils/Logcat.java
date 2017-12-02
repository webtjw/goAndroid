package com.webtjw.goandroid.utils;

import android.util.Log;

// Logcat 类是为了封装处理一下 AS 自带的 logcat 不太好用的替代品
public class Logcat {
    private static final String TAG = "jiawei"; // 独特的标签名，在 logcat 中与原 logcat 区分的字符串

    public static void i (String tag, String info) {
        Log.i(TAG + " " + tag, info);
    }
    public static void w (String tag, String info) {
        Log.w(TAG + " " + tag, info);
    }
    public void e (String tag, String info) {
        Log.e(TAG + " " + tag, info);
    }
    public void d (String tag, String info) {
        Log.d(TAG + " " + tag, info);
    }
    public void v (String tag, String info) {
        Log.v(TAG + " " + tag, info);
    }
}
