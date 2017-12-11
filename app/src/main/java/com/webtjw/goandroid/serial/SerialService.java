package com.webtjw.goandroid.serial;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by webtj on 2017/12/11.
 */

public class SerialService extends Service {

    private SerialThread thread1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initSerialThread();
        return null;
    }

    // 启动串口处理线程
    public void initSerialThread() {
        thread1 = new SerialThread();
        thread1.service = SerialService.this;
        thread1.start();
    }
}
