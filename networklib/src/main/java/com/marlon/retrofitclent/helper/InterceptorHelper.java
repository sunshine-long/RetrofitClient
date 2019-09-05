package com.marlon.retrofitclent.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Marlon
 * @desc
 * @date 2019/3/5
 */
public class InterceptorHelper {
    private static final String TAG = "InterceptorHelper";

    /**
     * 缓存拦截器
     *
     * @param context
     * @return
     */
    public static Interceptor getCaheInterceptor(Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (NetworkUtil.isNetworkAvailable(context)) {
                    Response response = chain.proceed(request);
                    // 从缓存中读取60秒
                    int maxAge = 60;
                    String cacheControl = request.cacheControl().toString();
                    Log.e("Tamic", "60s load cahe" + cacheControl);
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "当前无网络! 为你智能加载缓存", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("Tamic", " no network load cahe");
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    Response response = chain.proceed(request);
                    //set cahe times is 3 days
                    int maxStale = 60 * 60 * 24 * 3;
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };
    }

    /**
     * 日志拦截器
     */
    public static HttpLoggingInterceptor getLogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, "LogInterceptor---------: " + message);
            }
            //设置打印数据的级别
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 自动重试拦截器
     *
     * @return
     */
    public static Interceptor getRetryInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //最大重试次数
                int maxRetry = 10;
                //假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
                int retryNum = 5;

                Request request = chain.request();
                Response response = chain.proceed(request);
                while (!response.isSuccessful() && retryNum < maxRetry) {
                    retryNum++;
                    response = chain.proceed(request);
                }
                return response;
            }
        };
    }

    /**
     * 请求头拦截器
     *
     * @return
     */
    public static Interceptor getHeaderInterceptor(Map<String, String> headers) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等
                Request originalRequest = chain.request();
                if (null == originalRequest.body()) {
                    return chain.proceed(originalRequest);
                }
                Request.Builder requestBuilder = originalRequest.newBuilder();
                if (headers != null && headers.size() > 0) {
                    Set<String> keys = headers.keySet();
                    for (String headerKey : keys) {
                        requestBuilder.addHeader(headerKey, headers.get(headerKey)).build();
                    }
                }
                return chain.proceed(requestBuilder.build());
            }
        };

    }

    /**
     * 重新设置changeBaseUrlInterceptor 这个接口必须添加一个"url_name"的headers
     *
     * @return
     */
    public static Interceptor getBaseUrlInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //获取request
                Request oldRequest = chain.request();
                //从request中获取原有的HttpUrl实例oldHttpUrl
                HttpUrl oldHttpUrl = oldRequest.url();
                //获取request的创建者builder
                Request.Builder builder = oldRequest.newBuilder();
                //从request中获取headers，通过给定的键url_name
                List<String> headerValues = oldRequest.headers("url_name");
                if (headerValues != null && headerValues.size() > 0) {
                    //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                    builder.removeHeader("url_name");
                    //匹配获得新的BaseUrl
                    String headerValue = headerValues.get(0);
                    HttpUrl newBaseUrl = HttpUrl.parse(headerValue);
                    if (null == newBaseUrl) {
                        new IllegalStateException("do not instantiation me");
                    }
                    //重建新的HttpUrl，修改需要修改的url部分
                    HttpUrl newFullUrl = oldHttpUrl
                            .newBuilder()
                            .scheme(newBaseUrl.scheme())//更换网络协议
                            .host(newBaseUrl.host())//更换主机名
                            .port(newBaseUrl.port())//更换端口
//                            .removePathSegment(0)//移除第一个参数
                            .build();
                    //重建这个request，通过builder.url(newFullUrl).build()；
                    // 然后返回一个response至此结束修改
                    Log.e("Url", "intercept: " + newFullUrl.toString());
                    Request newRequest = builder
                            .url(newFullUrl)
                            .build();
                    return chain.proceed(newRequest);
                }
                return chain.proceed(oldRequest);
            }
        };
    }

    /**
     * 从返回值中获取cookie
     *
     * @param context
     * @return
     */
    public static Interceptor saveCookieFromResponse(Context context) {
        return new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                //这里获取请求返回的cookie
                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    final StringBuffer cookieBuffer = new StringBuffer();
                    Observable.fromIterable(originalResponse.headers("Set-Cookie"))
                            .map(new Function<String, String>() {
                                @Override
                                public String apply(String s) throws Exception {
                                    String[] cookieArray = s.split(";");
                                    return cookieArray[0];
                                }
                            }).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String cookie) throws Exception {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cookie", cookieBuffer.toString());
                    editor.commit();
                }
                return originalResponse;
            }
        };
    }

    public static Interceptor addCookiesInterceptor(Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request.Builder builder = chain.request().newBuilder();
                SharedPreferences sharedPreferences = context.getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE);
                Observable.just(sharedPreferences.getString("cookie", ""))
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String cookie) throws Exception {
                                //添加cookie
                                builder.addHeader("Cookie", cookie);
                            }
                        });
                return chain.proceed(builder.build());
            }
        };
    }
}
