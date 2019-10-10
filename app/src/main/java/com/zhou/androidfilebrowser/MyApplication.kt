package com.zhou.androidfilebrowser

import android.app.Application

import com.zhou.androidfilebrowser.uitl.LogcatUtil
import com.zhou.androidfilebrowser.uitl.ToastUtil

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LogcatUtil.debug = true
        ToastUtil.getInstance().setApplicatonContext(this)
    }


}
