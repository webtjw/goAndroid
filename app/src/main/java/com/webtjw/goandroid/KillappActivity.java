package com.webtjw.goandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webtjw.goandroid.utils.RouteHandle;

public class KillappActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_killapp);
        RouteHandle.addActivity(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("msgBundle");

        TextView text = (TextView) findViewById(R.id.killer);
        text.setText(bundle.getString("msg"));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteHandle.removeAll();
            }
        });
    }

    // 从本活动中定义了应该如何启动本活动
    public static void lauch (Context context, String string) {
        Bundle bundle = new Bundle();
        bundle.putString("msg", string);

        Intent intent = new Intent(context, KillappActivity.class);
        intent.putExtra("msgBundle", bundle);

        context.startActivity(intent);
    }
}
