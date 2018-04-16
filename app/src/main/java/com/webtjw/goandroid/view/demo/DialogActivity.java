package com.webtjw.goandroid.view.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.webtjw.goandroid.R;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public static void start (Context context) {
        context.startActivity(new Intent(context, DialogActivity.class));
    }
}
