package com.webtjw.goandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;

import com.webtjw.goandroid.utils.Logcat;

import java.io.IOException;
import java.util.HashMap;

public class CounterService extends Service {
    static final String TAG = "CounterService";
    public int count = 0;
    public UpdateCallback updateCallback;

    HashMap<Integer, Integer> volumeMap = new HashMap<Integer, Integer>();

    @Override
    public IBinder onBind(Intent intent) {
        startCounter();
        initVolumeMap();
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
                Message msg = new Message();
                msg.what = 1;
                CounterMsgObj obj = new CounterMsgObj(volumeMap.get(count));
                msg.obj = obj;
                if (updateCallback != null) updateCallback.callback(msg);
                count++;

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

    private void initVolumeMap() {
        volumeMap.put(0, 123);
        volumeMap.put(1, 124);
        volumeMap.put(2, 125);
        volumeMap.put(3, 126);
        volumeMap.put(4, 127);
        volumeMap.put(5, 128);
    }
}
