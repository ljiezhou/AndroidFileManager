package com.zhou.androidfilebrowser.base;


import com.zhou.androidfilebrowser.uitl.ToastUtil;

public abstract class BaseActivity extends AbsertactActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void showMsg(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

    @Override
    protected void showError(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

}
