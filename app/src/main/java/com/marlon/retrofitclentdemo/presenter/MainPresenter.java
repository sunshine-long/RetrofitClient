package com.marlon.retrofitclentdemo.presenter;

import com.marlon.retrofitclentdemo.app.App;
import com.marlon.retrofitclentdemo.base.RxPresenter;
import com.marlon.retrofitclentdemo.bean.Resond;
import com.marlon.retrofitclentdemo.contract.MainContract;
import com.marlon.retrofitclentdemo.http.ApiService;
import com.marlon.retrofitclent.BaseObserver;
import com.marlon.retrofitclent.helper.RxHelper;

import javax.inject.Inject;

/**
 * @author KangLong
 * @date 2017/7/6
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter(App app, ApiService service) {
        super(app, service);
    }


    @Override
    public void getVersion() {
        //使用方式一
        addSubscribe(apiService.getVerisionRxjava()
                .compose(RxHelper.applySchedulers(mContext))
                .subscribeWith(new BaseObserver<Resond>() {
                    @Override
                    protected void onSuccess(Resond value) {
                        mView.showData(value.toString());
                    }

                }));
        //使用方式二
        addSubscribe(apiService.getVerisionRxjava(), new BaseObserver<Resond>() {
            @Override
            protected void onSuccess(Resond value) {
                mView.showData(value.toString());
            }
        });
    }
}
