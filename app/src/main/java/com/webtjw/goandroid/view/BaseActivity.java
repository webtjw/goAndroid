package com.webtjw.goandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "wayne BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        create();
    }

    protected abstract void initView ();
    protected abstract void create ();

    public static void lauch (Context context, Class target) {
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
    }
}
