package net.uwonders.myretrofitclientdemo.retrofit;


import net.uwonders.myretrofitclientdemo.Resond;
import net.uwonders.myretrofitclientdemo.base.BaseResponse;
import net.uwonders.myretrofitclientdemo.bean.MobileVideoResource;
import net.uwonders.myretrofitclientdemo.bean.Page;
import net.uwonders.myretrofitclientdemo.bean.Userinfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 为Retrofit框架提供接口请求注解
 *
 * @author 康龙
 * @date 2017/5/9
 */

public interface BaseApiService {
    String BASE_URL_ZHIHU = " http://news-at.zhihu.com/";
    String BASE_URL_BAIDU = " https://www.baidu.com/";
    String BASE_URL_DAFULT = "http://www.sichuanxinge.com/";
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
    @Headers({URL_NAME + BASE_URL_ZHIHU})
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


    /**
     * 只有一个参数
     *
     * @param mobile 手机号
     * @return
     */
    @GET("/fitness/coach/login/code.jhtml")
    Observable<BaseResponse<String>> getVerificatCode(@Query("mobile") String mobile);

    /**
     * 登录接口
     *
     * @param body 请求体
     * @return
     */

    @POST("/fitness/coach/login/login.jhtml")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    Observable<BaseResponse<Userinfo>> login(@Body RequestBody body);

    /**
     * 获取我的课程列表
     *
     * @param body 请求体
     *             Integer pageNumber; 页码
     *             Integer pageSize; 数量
     *             TrainType  trainType; 训练类型
     *             Boolean  self;  是否是查看自己发的资源
     *             <p>
     *             enum TrainType
     *             {
     *             //5分钟器械
     *             FIVE_MINI_INSTREMENT,
     *             //5分钟徒手
     *             FIVE_MINI_UNARMED,
     *             //5分钟柔韧
     *             FIVE_MINI_PLIABLE,
     *             <p>
     *             }
     * @return
     */

    @POST("/fitness/coach/discovery/list.jhtml")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    Observable<BaseResponse<Page<MobileVideoResource>>> getDiscoverlist(@Body RequestBody body);

    /**
     * 获取自己的学员列表
     *
     * @param
     * @return
     */
    @POST("/fitness/coach/student/list.jhtml")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    Observable<BaseResponse<Page<Userinfo>>> getStudentList(@Body RequestBody body);

    /**
     * 上传资源的封面
     *
     * @param body 文件
     * @return
     */
    @POST("/fitness/coach/member/uploadCover.jhtml")
    Observable<BaseResponse<String>> uploadCover(@Body RequestBody body);

    /**
     * @param body
     * @return //难度星级
     * private int star
     * <p>
     * //时长
     * private Integer duration
     * <p>
     * private TrainType trainType
     * //封面
     * private String cover
     * //标题
     * private String title
     * private String content
     * private String file;
     * public enum TrainType
     * {
     * //5分钟器械
     * FIVE_MINI_INSTREMENT,
     * //5分钟徒手
     * FIVE_MINI_UNARMED,
     * //5分钟柔韧
     * FIVE_MINI_PLIABLE,
     * }
     */

    @POST("/fitness/coach/member/uploadVideo.jhtml")
    Observable<BaseResponse<String>> uploadCourse(@Body RequestBody body);


    /**
     * 查看学员的计划列表：
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @GET("/fitness/mobile/coach/studenTrain/list.jhtml")
    Observable<BaseResponse<Page<MobileVideoResource>>> getStudentCourses(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("userId") long userId);

    /**
     * 给学员添加计划
     * @param resourceId
     * @param userId
     * @return
     */
    @GET("/fitness/mobile/coach/studenTrain/addToTrain.jhtml")
    Observable<BaseResponse<String>> addToTrain(@Query("resourceId") long resourceId, @Query("userId") long userId);

    /**
     *删除学员的计划
     * @param resourceId
     * @param userId
     * @return
     */
    @GET("/fitness/mobile/coach/studenTrain/removeFromTrain.jhtml")
    Observable<BaseResponse<String>> removeFromTrain(@Query("resourceId") long resourceId, @Query("userId") long userId);

}



