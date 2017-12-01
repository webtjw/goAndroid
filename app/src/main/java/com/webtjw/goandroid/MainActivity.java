package com.webtjw.goandroid;

import android.content.Intent;
import android.nfc.Tag;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.webtjw.goandroid.utils.Logcat;
import com.webtjw.goandroid.utils.RouteHandle;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.activity_main);

        RouteHandle.addActivity(this);
        recoverFromDeath(savedInstanceState);
        setResultBack();
        setShowDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RouteHandle.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logcat.i(TAG, "Result back to MainActivity!!!");
        if (data != null) Logcat.w(TAG, "" + requestCode + " " + resultCode + " " + data.getStringExtra("returnData"));
    }

    // 可以在活动被系统回收后，保存回收前的数据，以便活动回复时，在 onCreate 里面重新读取数据
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String jiawei = "you can save this even if the activity's killed!";
        outState.putString("data", jiawei);
    }

    // 显示另外一个类似 Dialog 的活动
    public void setShowDialog () {
        Button button = (Button) findViewById(R.id.button_dialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
    }

    // 设置本活动为全屏
    private void makeFullscreen () {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 这个是 Activity 的
        if (getSupportActionBar() != null) getSupportActionBar().hide(); // 这个是 AppCompatActivity
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // 后面一个活动返回数据到前面一个活动
    private void setResultBack () {
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // 将 bundle 塞进 intent 里面传送给下一个活动
                Bundle bundle = new Bundle();
                bundle.putInt("age", 24);
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, 1);
            }
        });
    }

    // 从活动被回收后再恢复数据
    private void recoverFromDeath (Bundle savedState) {
        if (savedState != null) {
            String msg = savedState.getString("data");
            Logcat.i(TAG, msg);
        }
    }

    // JNI
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
