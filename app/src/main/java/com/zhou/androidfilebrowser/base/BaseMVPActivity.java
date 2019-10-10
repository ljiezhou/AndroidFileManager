package com.zhou.androidfilebrowser.base;


import com.zhou.androidfilebrowser.uitl.ToastUtil;

public abstract class BaseMVPActivity<T extends IPresenter> extends AbsertactActivity implements IView {

    protected T mPresenter;

    @Override
    protected void initView() {
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
            mPresenter = null;
        }
    }

    public abstract T createPresenter();

    @Override
    protected void showMsg(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

    @Override
    protected void showError(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.getInstance().toastShowS(msg);
    }
}
