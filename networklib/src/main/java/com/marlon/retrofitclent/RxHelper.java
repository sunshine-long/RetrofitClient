package com.marlon.retrofitclent;

import android.content.Context;
import android.widget.Toast;

import com.marlon.myretrofitclient.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装RX线程相关，主要用于控制线程切换，和进行相关预处理
 *
 * @author 康龙
 * @date 2017/5/10
 */

public class RxHelper {
    public static <T> ObservableTransformer<T, T> applySchedulers(final Context context) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (!NetworkUtil.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(errorTransformer());
    }


    public static <T> ObservableTransformer<T, T> applySimpleSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(errorTransformer());
    }

    public static <T> ObservableTransformer<T,T> errorTransformer(){
        return upstream -> upstream.onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            return Observable.error(ExceptionHandle.handleException(throwable));
        });
    }


}
