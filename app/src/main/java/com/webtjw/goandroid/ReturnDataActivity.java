package com.webtjw.goandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnDataActivity extends AppCompatActivity implements View.OnClickListener {

    public String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_data);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        msg = bundle.getString("msg");

        TextView text = (TextView) findViewById(R.id.msg_from_main_result);
        text.setText("MainActivity 传递过来的信息是 " + msg + " ，如果你按了后退键，我会把它加上 Hello! 再返回给 MainActivity");

        this.onClick(text);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("msg", "Hello! " + msg);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(ReturnDataActivity.this, "我通过实现接口实现了点击效果", Toast.LENGTH_SHORT).show();
    }
}
