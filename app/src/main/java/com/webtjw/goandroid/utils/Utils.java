package com.webtjw.goandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import static com.webtjw.goandroid.constant.Names.FITSR_OPEN_SP_KEY;
import static com.webtjw.goandroid.constant.Names.FITSR_OPEN_SP_NAME;

import com.webtjw.goandroid.GoApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;


public class Utils {
    // 删除文件夹以及文件夹下的所有东西
    public static void removeAll(String path) {
        File file = new File(path);

        if (file.exists()) {
            if (file.isFile() || file.list() == null || file.list().length == 0) {
                file.delete();
            } else {
                for (File f: file.listFiles()) {
                    Utils.removeAll(f.getAbsolutePath());
                }

                file.delete();
            }
        }
    }

    // 判断 app 是否有 h5 页面可以展示了
    public static boolean haveHTMLInData() {
        SharedPreferences sharedPreferences = GoApplication.getContext().getSharedPreferences(FITSR_OPEN_SP_NAME, Context.MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean(FITSR_OPEN_SP_KEY, false);

        return isFirst;
    }

    // 把上面的标识设为 true
    public static void confirmFirstOpenFlag() {
        SharedPreferences sharedPreferences = GoApplication.getContext().getSharedPreferences(FITSR_OPEN_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FITSR_OPEN_SP_KEY, true);
    }

    // 获取格式化的时间
    public static String getFormatTime () {
        Calendar now = Calendar.getInstance();

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day + " " +  (hour < 10 ? "0" : "") + hour + ":" +  (minute < 10 ? "0" : "") + minute + ":" +  (second < 10 ? "0" : "") + second;
    }

    // 往某个文件输入内容
    public static boolean saveStringToFile (String filePath, String content) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                if (!dir.exists()) dir.mkdirs();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();

                return true;
            } catch (IOException e) {
                Log.e("Utils saveStringToFile", e.getMessage());
            }
        }

        Toast.makeText(GoApplication.getContext(), "无法读写SD卡", Toast.LENGTH_LONG).show();
        return false;
    }
}
