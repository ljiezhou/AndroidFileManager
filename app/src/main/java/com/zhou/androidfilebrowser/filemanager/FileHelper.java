package com.zhou.androidfilebrowser.filemanager;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class FileHelper {

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static ArrayList<FileBean> getFiles(String path, boolean showHidden) {
        File file = new File(path);
        ArrayList<FileBean> fileBeans = new ArrayList<>();
        try {
            if (file.exists() && file.isDirectory()) {

                for (File listFile : file.listFiles()) {
                    FileBean fileBean = new FileBean();
                    fileBean.name = listFile.getName();
                    if (showHidden) {
                        fileBeans.add(fileBean);
                    } else {
                        if (!listFile.isHidden()) {
                            fileBeans.add(fileBean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBeans;
    }
}
