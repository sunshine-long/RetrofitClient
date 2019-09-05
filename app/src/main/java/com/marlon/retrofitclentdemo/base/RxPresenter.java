package com.marlon.retrofitclentdemo.base;

import android.content.Context;

import com.marlon.retrofitclentdemo.app.App;
import com.marlon.retrofitclentdemo.http.ApiService;
import com.marlon.retrofitclent.BaseObserver;
import com.marlon.retrofitclent.helper.RxHelper;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by codeest on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    protected ApiService apiService;
    protected Context mContext;
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    public RxPresenter(App mContext, ApiService apiService) {
        this.apiService = apiService;
        this.mContext = mContext;
    }

    /**
     * 取消注册 中断请求
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
        }
    }

    //注册
    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observable.compose(RxHelper.applySchedulers(mContext)).subscribeWith(observer));
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
