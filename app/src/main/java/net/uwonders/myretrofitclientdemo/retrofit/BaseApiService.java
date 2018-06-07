package net.uwonders.myretrofitclientdemo.retrofit;


import net.uwonders.myretrofitclientdemo.Resond;
import net.uwonders.myretrofitclientdemo.base.BaseResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * 为Retrofit框架提供接口请求注解
 *
 * @author 康龙
 * @date 2017/5/9
 */

public interface BaseApiService {
    String BASE_URL_ZHIHU = " http://news-at.zhihu.com/";
    String BASE_URL_BAIDU = " https://www.baidu.com/";
    String URL_NAME = "url_name:";

    /**
     * (示例无参数请求)  若果baseUrl不需要修改 直接去掉 @Headers({URL_NAME + 相关url})
     * 这里采用rxjava做回调
     * 获取版本号
     *
     * @param
     * @return
     */
    @Headers({URL_NAME + BASE_URL_ZHIHU})
    @GET("/api/4/version/android/2.3.0")
    Observable<BaseResponse<Resond>> getVerisionRxjava();

    /**
     * (示例无参数请求)
     * 这里用默认回调
     * 获取版本号
     *
     * @param
     * @return
     */
    @GET("/api/4/version/android/2.3.0")
    Call<BaseResponse<Resond>> getVersionDefult();

    /**
     * 若果baseUrl不需要修改 直接去掉 @Headers({URL_NAME + 相关url})
     * <p>
     * 若果请求只有baseurl 没有api接口 需要在get中添加（"/"）
     */

    @Headers(URL_NAME + BASE_URL_BAIDU)
    @GET("/")
    Observable<String> getBaidu();


}



