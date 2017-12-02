package com.webtjw.goandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webtjw.goandroid.utils.Logcat;
import com.webtjw.goandroid.utils.RouteHandle;

public class SecondActivity extends AppCompatActivity {
    static final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RouteHandle.addActivity(this);
        getMsgFromIntent(getIntent());

        TextView text = (TextView) findViewById(R.id.sample_second);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KillappActivity.lauch(SecondActivity.this, "我使用了你的方法来启动你哦！厉害吧");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RouteHandle.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("returnData", "hello kawi!");
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getMsgFromIntent (Intent intent) {
        Bundle bundle = intent.getBundleExtra("bundle");
        Logcat.i(TAG, "Get msg from intent's bundle: " + Integer.toString(bundle.getInt("age")));
    }
}
