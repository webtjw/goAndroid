package com.webtjw.goandroid.serial;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webtjw.goandroid.R;

public class SerialActivity extends AppCompatActivity {

    private EditText serialInput;
    private Button serialSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullscreen();
        setContentView(R.layout.activity_serial);

        serialInput = findViewById(R.id.serial_data_input);
        serialSend = findViewById(R.id.serial_data_send);
        setInput();
        initSerialService();
    }

    // 初始化服务
    private void initSerialService() {
        Intent intent = new Intent(SerialActivity.this, SerialActivity.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    // 服务连接
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) { }
    };

    // 发送输入内容
    private void setInput() {
        serialSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SerialActivity.this, serialInput.getText().toString(), Toast.LENGTH_SHORT).show();
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
}
