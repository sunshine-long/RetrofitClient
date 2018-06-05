package net.uwonders.myretrofitclientdemo.base;

import android.content.Context;

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
        onSuccess(value);
       /* if (value.isSuccess()) {
            if (value.getData() == null) {
                onSuccess(value.getData());
            } else {
                T t = value.getData();
                onSuccess(t);
            }
        } else {

        }*/
    }

    @Override
    public void onError(Throwable e) {


    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;

    }

    protected abstract void onSuccess(BaseResponse<T> value);

//    protected abstract void onResError(BaseEntity value);


}
