package com.webtjw.goandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        goVideoView();
        setNetworkSpy();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            String age = intent.getStringExtra("msg");
            if (age != "") {
                Toast.makeText(this, age, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 可以在活动被系统回收后，保存回收前的数据，以便活动回复时，在 onCreate 里面重新读取数据
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String jiawei = "you can save this even if the activity's killed!";
        outState.putString("data", jiawei);
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
        TextView button2 = (TextView) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ReturnDataActivity.class);
            EditText editText = (EditText) findViewById(R.id.to_next_data);
            // 将 bundle 塞进 intent 里面传送给下一个活动
            Bundle bundle = new Bundle();
            bundle.putString("msg", editText.getText().toString());
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

    // 查看视频 VideoView
    private void goVideoView () {
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
    }

    // 注册广播接收器
    private void setNetworkSpy () {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkChangeReceiver(), intentFilter);
    }

    // 网络改变广播接收器
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "网络变化", Toast.LENGTH_SHORT).show();
        }
    }

    // JNI
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
