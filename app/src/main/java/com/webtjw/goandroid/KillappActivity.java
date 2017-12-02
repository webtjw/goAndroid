package com.webtjw.goandroid;

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

        TextView text = (TextView) findViewById(R.id.killer);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouteHandle.removeAll();
            }
        });
    }
}
