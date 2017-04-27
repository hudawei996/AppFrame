package com.vanke.libvanke.mvp;

import android.content.Context;

import com.vanke.libvanke.data.RxManager;
import com.vanke.libvanke.data.RxSchedule;


import rx.Observable;
import rx.Subscriber;

/**
 * User: PAPA
 * Date: 2017-03-31
 */

public abstract class BasePresenter<V> {

    private Context context;
    private V mView;
    private RxManager mRxManager = new RxManager();

    public void setView(V v) {
        this.mView = v;
    }

    public void onCreate() {

    }


    protected <T> void addSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        mRxManager.addSubscription(observable.compose(RxSchedule.<T>rxSchedulerHelper()).subscribe
                (subscriber));
    }


    public void onDestroy() {
        mRxManager.unSubscribe();
        mView = null;
    }
}
