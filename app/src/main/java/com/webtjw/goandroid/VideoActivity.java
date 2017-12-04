package com.webtjw.goandroid;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.webtjw.goandroid.utils.Logcat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {

    public static final String TAG = "VideoActivity";
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initVideo();
    }

    private void initVideo () {
        // File[] files = new File("/mnt/extsd/Tokheim/adVideo/").listFiles();
        File[] files = new File("/sdcard/Download/").listFiles();
        List<String> fileArray = new ArrayList<String>();
        for (File item : files) {
            fileArray.add(item.getPath());
        }

        LinearLayout container = (LinearLayout) findViewById(R.id.videoview_container);
        videoView = new VideoView(this);
        container.addView(videoView);

        videoView.setVideoPath(fileArray.get(0));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }
}
