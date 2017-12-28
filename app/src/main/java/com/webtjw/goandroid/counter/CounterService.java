package com.webtjw.goandroid.counter;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.webtjw.goandroid.common.UIInterface;

/**
 * Created by webtj on 2017/12/28.
 */

public class CounterService extends Service {

    private CounterThread counterThread;
    public UIInterface updateInterface;
    public boolean isBind = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        counterThread = new CounterThread();
        counterThread.counterService = CounterService.this;
        counterThread.start();
        isBind = true;

        CounterBinder counterBinder = new CounterBinder();
        return counterBinder;
    }



    public class CounterBinder extends Binder {
        private CounterService counterService;

        public CounterService getService() {
            return CounterService.this;
        }
    }

    public void setUpdateMethod(UIInterface uiInterface) {
        updateInterface = uiInterface;
    }
}
