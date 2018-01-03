package com.webtjw.goandroid.html5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
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

    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File myFile = new File(getAssets() + File.separator + "h5.zip");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(myFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "11111");
            return false;
        }
    }
}
