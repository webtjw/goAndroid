package com.webtjw.goandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by webtj on 2017/12/4.
 */

public class SysBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "开机了", Toast.LENGTH_SHORT).show();
    }
}
