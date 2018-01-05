package com.webtjw.goandroid.constant;

import android.os.Environment;

import java.io.File;

// 常量 - 路径

public class PathName {
    public static final String HTML5_PACKAGE_NAME = "html5.zip";
    public static final String HTML5_FOLDER_NAME = "html5";
    public static final String HTML5_FOLDER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HTML5_FOLDER_NAME;
    public static final String HTML5_PACKAGE_PATH = HTML5_FOLDER_PATH + File.separator + HTML5_PACKAGE_NAME;
}
