package net.uwonders.myretrofitclientdemo.retrofit;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author KangLong
 * date 2017/5/8
 * description 采用快速 工厂模式/单例 构建Retrofit请求类
 */
public class MyRetrofitClient {

    public static final String BASE_URL = " http://news-at.zhihu.com/";
    private static final long TIMEOUT = 300;
    private static final String TAG = "MyRetrofitClient";
    private static MyRetrofitClient instance;
    private Retrofit retrofit;

    public static MyRetrofitClient getInstance(Context context) {
        if (instance == null) {
            synchronized (MyRetrofitClient.class) {
                if (instance == null) {
                    instance = new MyRetrofitClient(context);
                }
            }
        }
        return instance;
    }


    /**
     * 构造函数，用于初试化
     * @param context
     */
    private MyRetrofitClient(Context context) {
        CookieJar  mCookieJar = new CookieManger(context.getApplicationContext());
        //添加token 在header中，没有可以不用
        HttpCommonInterceptor mCommonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("token", "yours token")
                .build();
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                //添加Cookie管理，不需要管理可以不加，token在Cookie中的时候需要添加
//                .cookieJar(mCookieJar)
                //打印请求信息
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                //相关请求时间设置
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
        initRetrofit(mOkHttpClient);
    }

    /**
     * 初始化retrofit
     */
    private void initRetrofit(OkHttpClient mOkHttpClient) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                //这里是转换器  这里采用Gson做转换器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RXjava做适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 用于构建请求代理
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
