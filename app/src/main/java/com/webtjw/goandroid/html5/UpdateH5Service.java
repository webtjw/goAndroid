package com.webtjw.goandroid.html5;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.webtjw.goandroid.GoApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class UpdateH5Service extends Service {

    private static final String TAG = "UpdateH5Service";

    public H5Connection h5Connection;
    public UpdateThread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initHttpConnection();

        thread = new UpdateThread();
        thread.updateH5Service = UpdateH5Service.this;
        thread.start();

        UpdateBinder updateBinder = new UpdateBinder();
        updateBinder.h5Service = UpdateH5Service.this;

        return updateBinder;
    }

    public class UpdateBinder extends Binder {
        public UpdateH5Service h5Service;
    }

    // 请求的内容管理
    public interface H5Connection {
        @GET("h5.json")
        Call<ResponseBody> getVersion();

        @GET("h5.zip")
        Call<ResponseBody> getPackage();
    }

    public class ObjectDefine {
        public class VersionObj {
            public String name;
            public String version;
        }
    }

    // 初始化网络请求
    private void initHttpConnection() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.191.3:3000/").build();
        h5Connection = retrofit.create(H5Connection.class);
    }

    public static boolean copyH5AssetsToData() {
        Context context = GoApplication.getContext();
        AssetManager assetManager = context.getAssets();
        String dataPath = context.getFilesDir().getAbsolutePath();

        try {
            InputStream h5Package = assetManager.open("h5/h5.zip");

            File h5Folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "html5");
            h5Folder.mkdir();
            File txt = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/html5", "1.txt");
            txt.createNewFile();


            String[] dododo = new File(Environment.getExternalStorageDirectory().getAbsolutePath()).list();

            for (String item: dododo) {
                Log.i("kawi", item);
            }
        } catch (IOException e) {}

        return false;
    }
}
