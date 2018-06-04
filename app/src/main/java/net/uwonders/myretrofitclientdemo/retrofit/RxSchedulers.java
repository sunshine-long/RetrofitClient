package net.uwonders.myretrofitclientdemo.retrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装RX线程相关
 *
 * @author 康龙
 * @date 2017/5/10
 */

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        /*.doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!NetworkUtil.isNetworkAvailable(context)) {
                                    Toast.makeText(context, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })*/
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
