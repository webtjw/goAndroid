package com.webtjw.goandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.webtjw.goandroid.constant.Names.FITSR_OPEN_SP_KEY;
import static com.webtjw.goandroid.constant.Names.FITSR_OPEN_SP_NAME;

import com.webtjw.goandroid.GoApplication;

import java.io.File;
import java.io.InputStream;

/**
 * Created by webtjw on 2017/12/2.
 * Utils 通用工具类
 */

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
}
