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
import com.webtjw.goandroid.utils.Utils;

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
        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        // String dataPath = context.getFilesDir().getAbsolutePath();
        String h5DataPath = dataPath + File.separator + "html5";

        try {
            InputStream inputStream = assetManager.open("h5/h5.zip");

            // 判断 data 文件夹中 html5 文件夹是否存在
            if (new File(h5DataPath).exists()) {
                // 删除文件夹
                Utils.removeAll(h5DataPath);
            } else {
                new File(h5DataPath).mkdir();
            }

            File copyHtml5Zip = new File(h5DataPath + File.separator + "html5.zip");
            copyHtml5Zip.createNewFile();
            FileOutputStream copyHtml5ZipStream = new FileOutputStream(copyHtml5Zip);
            byte[] buf = new byte[1024];
            int readSize = 0;

            while (readSize != -1) {
                copyHtml5ZipStream.write(buf, 0, readSize);
                readSize = inputStream.read(buf);
            }

            inputStream.close();
            copyHtml5ZipStream.flush();
            copyHtml5ZipStream.close();


        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        return false;
    }
}
