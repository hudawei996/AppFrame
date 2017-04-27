package com.vanke.libvanke.net;

import com.vanke.libvanke.varyview.IInteractorView;

import rx.Subscriber;


public abstract class RxSubscribe<T> extends Subscriber<T> {

    private IInteractorView mInteractorView;

    protected boolean showDialog() {
        return true;
    }


    public RxSubscribe(IInteractorView interactorView) {
        mInteractorView =interactorView;
    }


    @Override
    public void onCompleted() {
        if (showDialog())
            mInteractorView.restore();
    }
    @Override
    public void onStart() {
        super.onStart();
        mInteractorView.showLoading("Loading...");
    }
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) { //这里自行替换判断网络的代码
            onFail("网络不可用");
        } else if (e instanceof ApiException) {
            onFail(e.getMessage());
        } else {
            onFail("请求失败，请稍后再试...");
        }
        if (showDialog())
            mInteractorView.restore();
    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String message);

}
