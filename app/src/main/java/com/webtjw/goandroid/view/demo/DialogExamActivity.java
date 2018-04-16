package com.webtjw.goandroid.view.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webtjw.goandroid.R;

public class DialogExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_exam);

        DialogActivity.start(this);
    }

    public static void start (Context context) {
        context.startActivity(new Intent(context, DialogExamActivity.class));
    }
}
