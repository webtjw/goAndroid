package com.webtjw.goandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by webtj on 2017/12/4.
 */

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "接收到自定义广播了", Toast.LENGTH_SHORT).show();
    }
}
