package com.marlon.retrofitclent;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author 康龙
 * @date 2017/5/10
 */
public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    @Override
    public void onNext(BaseResponse<T> value) {
        if (value.isSuccess() && (value.getData() != null)) {
            try {
                onSuccess(value.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            onFailure(new ResponeException(value.getCode(), value.getMessage()));
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage());
        try {
            if (e instanceof ResponeException) {
                onFailure((ResponeException) e);
            } else if (e instanceof HttpException){
                onFailure(new ResponeException(((HttpException) e).code(),((HttpException) e).message()));
            }else {
                onFailure(new ResponeException(-1,e.getMessage()));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }


    /**
     * 请求成功，code == 200；
     *
     * @param value
     */
    protected abstract void onSuccess(T value);


    /**
     * 请求失败，返回错误/失败信息
     *
     * @param exception
     */
    protected void onFailure(ResponeException exception) {
    }

}
