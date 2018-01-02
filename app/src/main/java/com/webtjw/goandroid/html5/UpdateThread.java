package com.webtjw.goandroid.html5;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.webtjw.goandroid.GoApplication;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateThread extends Thread implements Runnable {

    private static final String TAG = "UpdateThread";

    public UpdateH5Service updateH5Service;

    @Override
    public void run() {
        super.run();

        Call<ResponseBody> callGetVersion = updateH5Service.h5Connection.getVersion();
        callGetVersion.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    UpdateH5Service.ObjectDefine.VersionObj versionObj = gson.fromJson(response.body().string(), UpdateH5Service.ObjectDefine.VersionObj.class);
                    Toast.makeText(GoApplication.getContext(), versionObj.name + '-' + versionObj.version, Toast.LENGTH_SHORT).show();


                } catch (IOException e) {}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
