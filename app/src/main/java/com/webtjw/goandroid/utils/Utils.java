package com.webtjw.goandroid.utils;

import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

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
    // 复制文件
    public static void copyFile(InputStream origin, File target) {

    }
}
