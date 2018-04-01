package com.webtjw.goandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.webtjw.goandroid.R;
import com.webtjw.goandroid.view.demo.DialogExamActivity;
import com.webtjw.goandroid.view.demo.UncaughtErrorActivity;

import java.util.ArrayList;

public class IndexActivity extends BaseActivity {

    private static final String TAG = "wayne IndexActivity";

    private LinearLayout demoListBox;

    public ArrayList<DemoItem> demoList = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_index);

        demoListBox = findViewById(R.id.index_demo_list);
    }

    @Override
    protected void create() {
        fillDemoItems();
    }

    private class DemoItem {
        public String btnText;
        public DemoInterface demoInterface;

        public DemoItem (String text, DemoInterface demoInterface) {
            this.btnText = text;
            this.demoInterface = demoInterface;
        }
    }

    private interface DemoInterface {
        public void click();
    }

    private void fillDemoItems () {
        final Context ctx = this;

        // 捕捉未处理的错误，用于处理程序意外崩溃闪退的日志记录和 APP 重启
        DemoItem demoItem1 = new DemoItem("捕捉未处理的错误", new DemoInterface() {
            @Override
            public void click() {
                UncaughtErrorActivity.start(ctx);
            }
        });
        demoList.add(demoItem1);

        // 生成控件列表
        for (int i = 0, demoSize = demoList.size(); i < demoSize; i++) {
            final DemoItem item = demoList.get(i);
            TextView textView = new TextView(this);
            textView.setText(i + 1 + "，" + item.btnText);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.demoInterface.click();
                }
            });
            // style
            final int scale = (int)getResources().getDisplayMetrics().density; // scale
            textView.setBackgroundColor(Color.parseColor("#e1514c"));
            textView.setGravity(Gravity.CENTER);
            textView.setAllCaps(false);
            textView.setPadding(6 * scale, 10 * scale, 6 * scale, 10 * scale);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextSize(16);
            // append
            demoListBox.addView(textView);
            // LayoutParams
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)textView.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.setMargins(4 * scale, 5 * scale, 4 * scale, 5 * scale);
            textView.setLayoutParams(lp);
        }
    }
}
