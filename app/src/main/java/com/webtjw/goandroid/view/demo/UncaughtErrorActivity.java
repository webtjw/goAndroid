package com.webtjw.goandroid.view.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.webtjw.goandroid.R;

import java.util.ArrayList;

public class UncaughtErrorActivity extends AppCompatActivity {

    private static final String TAG = "wayne UncaughtErrorActivity";

    Button triggerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncaught_error);

        triggerButton = findViewById(R.id.uncaught_trigger);
        triggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.get(100);
            }
        });
    }

    public static void start (Context context) {
        context.startActivity(new Intent(context, UncaughtErrorActivity.class));
    }
}
