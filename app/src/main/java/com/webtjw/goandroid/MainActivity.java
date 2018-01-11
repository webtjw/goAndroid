package com.webtjw.goandroid;

import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.webtjw.goandroid.constant.PathName;
import com.webtjw.goandroid.html5.WebviewActivity;
import com.webtjw.goandroid.utils.Utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;


public class MainActivity extends AppCompatActivity {

    static final String TAG = "Manuel - MainActivity";

    private ServiceConnection html5ServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
        findViewById(R.id.main_text).startAnimation(animation);

        copyHTML5FromAsset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                // startActivity(intent);
            }
        }, 2000);

        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // 从 assets 中复制 H5 资源到 data 里面
    private void copyHTML5FromAsset() {
        if (!Utils.haveHTMLInData()) {
            // 开子线程执行耗时操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = getAssets().open(PathName.HTML5_PACKAGE_NAME);

                        // 新创建一个存放 h5 资源的文件夹
                        if (new File(PathName.HTML5_FOLDER_PATH).exists()) Utils.removeAll(PathName.HTML5_FOLDER_PATH);
                        new File(PathName.HTML5_FOLDER_PATH).mkdir();
                        // 把 assets 中的压缩包复制到 data 中
                        File copyPackage = new File(PathName.HTML5_PACKAGE_PATH);
                        copyPackage.createNewFile();

                        FileOutputStream copyPackageStream = new FileOutputStream(copyPackage);
                        byte[] buf = new byte[1024];
                        int readSize = 0;

                        while (readSize != -1) {
                            copyPackageStream.write(buf, 0, readSize);
                            readSize = inputStream.read(buf);
                        }

                        inputStream.close();
                        copyPackageStream.flush();
                        copyPackageStream.close(); // 复制完成

                        try {
                            ZipFile zipFile = new ZipFile(PathName.HTML5_PACKAGE_PATH);
                            // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
                            if (!zipFile.isValidZipFile()) Log.e(TAG, "压缩文件不合法，可能被损坏");

                            zipFile.extractAll(PathName.HTML5_FOLDER_PATH); // 执行解压
                            Utils.confirmFirstOpenFlag();
                        } catch (ZipException e) {
                            Log.e(TAG, "压缩失败，错误原因是：" + e.getMessage());
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "assets 中读取 H5 资源包失败，请确认 app 打包时 assets 目录下存在：" + PathName.HTML5_PACKAGE_NAME);
                    }
                }
            }).start();
        }
    }

    // JNI
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
