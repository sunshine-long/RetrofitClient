package com.marlon.mvpdaggerretrofit.presenter;

import android.content.Context;

import com.marlon.mvpdaggerretrofit.app.App;
import com.marlon.mvpdaggerretrofit.base.RxPresenter;
import com.marlon.mvpdaggerretrofit.contract.MainContract;
import com.marlon.myretrofitclient.base.BaseObserver;
import com.marlon.myretrofitclient.bean.Resond;
import com.marlon.myretrofitclient.retrofit.BaseApiService;
import com.marlon.myretrofitclient.retrofit.RxHelper;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author KangLong
 * @date 2017/7/6
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    BaseApiService service;
    Context mContext;
    @Inject
    public MainPresenter(App app) {
        this.mContext = app;

    }


    @Override
    public void getVersion() {
        service.getVerisionRxjava().compose(RxHelper.io_main(mContext)).subscribe(new BaseObserver<Resond>(mContext) {
            @Override
            protected void onSuccess(Resond value) {
                mView.showData(value.toString());
            }

            @Override
            protected void onBefore(Disposable d) {

            }

            @Override
            protected void onFailure(String message) {
                mView.showData(message);
            }
        });
    }
}
