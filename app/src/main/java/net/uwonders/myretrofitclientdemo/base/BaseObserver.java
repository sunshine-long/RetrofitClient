package net.uwonders.myretrofitclientdemo.base;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author 康龙
 * @date 2017/5/10
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;
    private Disposable mDisposable;


    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onNext(BaseResponse<T> value) {
        if (value.isSuccess()) {
            try {
                onSuccess(value.getData());
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            onFailure("错误信息:" + value.getMessage() + "---Code：" + value.getCode());
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage());
        onFailure(e.getMessage());
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e.getMessage());
                e.printStackTrace();
            } else {
                onFailure(e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
        onBefore(d);
    }

    /**
     * 请求成功，code == 200；
     *
     * @param value
     */
    protected abstract void onSuccess(T value);

    /**
     * 在回调之前
     *
     * @param d
     */
    protected abstract void onBefore(@NonNull Disposable d);

    /**
     * 请求失败，返回错误/失败信息
     *
     * @param message
     */
    protected abstract void onFailure(String message);


}
