package com.webtjw.goandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;

import com.webtjw.goandroid.utils.Logcat;

import java.io.IOException;

public class CounterService extends Service {
    static final String TAG = "CounterService";
    public int count = 0;
    public UpdateCallback updateCallback;

    @Override
    public IBinder onBind(Intent intent) {
        startCounter();
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        public CounterService getService() {
            return CounterService.this;
        };
    }

    private void startCounter() {
        CounterThread thread = new CounterThread();
        thread.start();
    }

    private class CounterThread extends Thread implements Runnable {
        @Override
        public void run() {
            while (!interrupted()) {
                count++;
                Message msg = new Message();
                msg.what = 1;
                CounterMsgObj obj = new CounterMsgObj(count);
                msg.obj = obj;
                if (updateCallback != null) updateCallback.callback(msg);
                try {
                    sleep(1000);
                } catch (InterruptedException ior) {

                }
            }
        }
    }

    public static class CounterMsgObj extends Object {
        public int count;

        public CounterMsgObj (int num) {
            count = num;
        }
    }
}
