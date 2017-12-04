package com.webtjw.goandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class CounterService extends Service {
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
                if (updateCallback != null) updateCallback.callback(count);
                try {
                    sleep(1000);
                } catch (InterruptedException ior) {

                }
            }
        }
    }
}
