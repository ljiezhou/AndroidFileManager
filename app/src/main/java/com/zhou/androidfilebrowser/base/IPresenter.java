package com.zhou.androidfilebrowser.base;


/**
 * Created by guo on 2017/3/31.
 */

public interface IPresenter<T extends IView> {

    /**
     * 注入View
     * @param view
     */
    void attachView(T view);

    void start();

    /**
     * 回收
     */
    void detach();
//    private CompositeDisposable com/
}
