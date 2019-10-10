package com.zhou.androidfilebrowser.base;


public class BasePresenter2<T extends IView> implements IPresenter<T> {
    protected T mView;
//    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

//    protected void addSubscribe(Disposable disposable) {
//        if (compositeDisposable == null) {
//            compositeDisposable = new CompositeDisposable();
//        }
//        compositeDisposable.add(disposable);
//    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {
        this.mView = null;
//        if(compositeDisposable!=null) compositeDisposable.clear();
    }
}
