package net.uwonders.myretrofitclientdemo.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author KangLong
 * date 2017/5/8
 * description 快速 构建Retrofit请求类
 */
public class MyRetrofitClient {

    public static String baseUrl = BaseApiService.BASE_URL;
    private static final long TIMEOUT = 10;
    private static final String TAG = "MyRetrofitClient";
    private Retrofit retrofit;
    private static Context mContext;
    private OkHttpClient mOkHttpClient;

    public static MyRetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 创建内部类单利
     */
    private static class SingletonHolder {
        private static MyRetrofitClient INSTANCE = new MyRetrofitClient(mContext);
    }
    /**
     * 构造函数，用于初试化
     * @param context
     */
    private MyRetrofitClient(Context context) {
        this(context, null);
    }

    private MyRetrofitClient(Context context, String url) {
        this(context, url, null);
    }

    private MyRetrofitClient(Context context, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        mOkHttpClient = new OkHttpClient.Builder()
                //添加Cookie管理，不需要管理可以不加，token在Cookie中的时候需要添加
                .cookieJar(new CookieManger(context))
                //添加统一的请求头
                .addInterceptor(new BaseInterceptor(headers))
                //打印请求信息
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BASIC))
                //相关请求时间设置
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mOkHttpClient)
                //这里是转换器  这里采用Gson做转换器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RXjava做适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     * 用于构建请求代理,BaseApiService中没有包含时可以用这个
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    public BaseApiService createService() {
        return retrofit.create(BaseApiService.class);
    }
}
