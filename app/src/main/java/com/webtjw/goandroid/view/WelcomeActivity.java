package com.webtjw.goandroid.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.webtjw.goandroid.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void create() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                lauch(WelcomeActivity.this, IndexActivity.class);
            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
