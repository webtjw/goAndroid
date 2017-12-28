package com.webtjw.goandroid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.webtjw.goandroid.common.UIInterface;
import com.webtjw.goandroid.counter.CounterService;


public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    private Button button1;

    private CounterService counterService;
    private ServiceConnection counterServiceConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);

        initCounter();
    }

    @Override
    protected void onDestroy() {
        unbindService(counterServiceConnect);
        super.onDestroy();
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
    private void makeFullscreen() {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 这个是 Activity 的
        if (getSupportActionBar() != null) getSupportActionBar().hide(); // 这个是 AppCompatActivity
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    // 开启计数服务和子线程，同时利用 GoApplication 来 Toast
    private void initCounter() {
        Intent intent = new Intent(MainActivity.this, CounterService.class);

        counterServiceConnect = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                counterService = ((CounterService.CounterBinder)iBinder).getService();
                counterService.setUpdateMethod(new UIInterface() {
                    @Override
                    public void updateUI(Message message) {
                        counterHandler.sendMessage(message);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) { }
        };

        bindService(intent, counterServiceConnect, Context.BIND_AUTO_CREATE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.unbindService(counterServiceConnect);
            }
        });
    }

    public Handler counterHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x01) {
                int count = message.arg1;
                String button1Text = button1.getText().toString();
                button1Text += "当前计数为：" + Integer.toString(count);
                button1.setText(button1Text);
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
