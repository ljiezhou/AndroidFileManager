package com.zhou.androidfilebrowser.base;


import android.view.View;

import com.zhou.androidfilebrowser.uitl.ToastUtil;


public abstract class BaseFragment extends AbstractFragment  {

    @Override
    protected void initView(View view) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showErrorMsg(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

}
