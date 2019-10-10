package com.zhou.androidfilebrowser.base;


import android.view.View;

import com.zhou.androidfilebrowser.uitl.ToastUtil;


public abstract class BaseMVPFragment<T extends IPresenter> extends AbstractFragment implements IView {
    protected T mPresenter;

    @Override
    protected void initView(View view) {
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detach();
        mPresenter = null;
    }

    public abstract T createPresenter();

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

}
