package com.webtjw.goandroid.counter;

import android.os.Message;

/**
 * Created by webtj on 2017/12/28.
 */

public class CounterThread extends Thread implements Runnable {

    public CounterService counterService;
    private int count = 0;

    @Override
    public void run() {
        while (!interrupted()) {
            if (counterService != null && counterService.updateInterface != null) {
                Message message = new Message();
                message.what = 0x01;
                message.arg1 = count;
                counterService.updateInterface.updateUI(message);

                try {
                    sleep(1000);
                } catch (InterruptedException e) {}

                count++;
            }
        }
    }
}
