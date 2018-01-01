package com.webtjw.goandroid;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.webtjw.goandroid.html5.CheckUpdateThread;
import com.webtjw.goandroid.html5.WebviewActivity;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkH5Update();
        try {
            CheckUpdateThread checkH5Thread = new CheckUpdateThread();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.191.2:3000/").build();
        H5Service h5Service = retrofit.create(H5Service.class);

        Call<ResponseBody> call2GetVersion = h5Service.getVersion();

        call2GetVersion.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    VersionObject versionObject = gson.fromJson(response.body().string(), VersionObject.class);
                    Toast.makeText(GoApplication.getContext(), versionObject.name + '-' + versionObject.version, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(GoApplication.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Log.i(TAG, myVersion);
    }

    public interface H5Service {
        @GET("h5.json")
        Call<ResponseBody> getVersion();
    }

    public class VersionObject {
        public String name;
        public String version;
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

    private void checkH5Update() {

    }

    public static void download(String urlPath) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = conn.getInputStream();

        while ((len = inputStream.read(data)) != -1) {
            byteArrayOutputStream.write(data, 0, len);
        }
        inputStream.close();
        Log.e(TAG, new String(byteArrayOutputStream.toByteArray()));
    }

    // JNI
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
