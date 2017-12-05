package com.webtjw.goandroid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.webtjw.goandroid.receiver.TestReceiver;
import com.webtjw.goandroid.utils.Logcat;
import com.webtjw.goandroid.utils.RouteHandle;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    public CounterService counterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        RouteHandle.addActivity(this);
        recoverFromDeath(savedInstanceState);
        setResultBack();
        goVideoView();
        setNetworkSpy();
        setCustomBroadcast();
        startCountService();
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

    // 设立自定义的广播
    private void setCustomBroadcast () {
        // 注册接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.webtjw.goAndroid.testCustomBroadcast");
        registerReceiver(new TestReceiver(), intentFilter);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.webtjw.goAndroid.testCustomBroadcast");
                sendBroadcast(intent);
                // sendOrderedBroadcast(); // 发送有序广播，这种是按顺序发送，有可能会被某个 APP 拦截的
            }
        });
    }

    // 启动计数器 service
    private void startCountService () {
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterService == null) {
                    Intent intent = new Intent(MainActivity.this, CounterService.class);
                    bindService(intent, countServiceConn, BIND_AUTO_CREATE);
                }
            }
        });

    }

    // service 和活动间的链接
    public ServiceConnection countServiceConn = new ServiceConnection () {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            CounterService service = ((CounterService.MsgBinder) iBinder).getService();
            counterService = service;
            service.updateCallback = new UpdateCallback() {
                @Override
                public void callback(Message msg) {
                    counterHandler.sendMessage(msg);
                }
            };
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) { }
    };

    // message handler
    public Handler counterHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // 防止内存泄漏
            if (msg.obj != null && counterService != null) {
                CounterService.CounterMsgObj obj = (CounterService.CounterMsgObj)msg.obj;
                button5.setText("当前计数是：" + obj.count);
            }
            return false;
        }
    });

    // JNI
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
