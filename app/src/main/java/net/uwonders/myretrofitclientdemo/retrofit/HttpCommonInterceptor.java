package net.uwonders.myretrofitclientdemo.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加统一的请求拦截器
 * @author Marlon
 * @date 2017/6/17
 */

public class HttpCommonInterceptor implements Interceptor {
    private Map<String,String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d("HttpCommonInterceptor","add common params");
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中
/*      HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()                .newBuilder()
         .scheme(oldRequest.url().scheme())
             .host(oldRequest.url().host());*/
        // 新的请求
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),
                oldRequest.body());

        //添加公共参数,添加到header中
        if(mHeaderParamsMap.size() > 0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
    public static class Builder{
        HttpCommonInterceptor mHttpCommonInterceptor;
        public Builder(){
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }
        public Builder addHeaderParams(String key, Object value){
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public HttpCommonInterceptor build(){
            return mHttpCommonInterceptor;
        }
    }
}
