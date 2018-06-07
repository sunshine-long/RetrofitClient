package net.uwonders.myretrofitclientdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.uwonders.myretrofitclientdemo.base.BaseObserver;
import net.uwonders.myretrofitclientdemo.base.BaseResponse;
import net.uwonders.myretrofitclientdemo.bean.MobileVideoResource;
import net.uwonders.myretrofitclientdemo.bean.Page;
import net.uwonders.myretrofitclientdemo.retrofit.MyRetrofitClient;
import net.uwonders.myretrofitclientdemo.retrofit.RxHelper;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }

    /**
     * +Rxjava 后五参数的情况
     * @param view
     */
    public void noParameterRxjava(View view) {
        textView.setText("");
        MyRetrofitClient
                .getInstance(this)
                .createService()
                .getVerisionRxjava()
                .compose(RxHelper.<BaseResponse<Resond>>io_main(this))
                .subscribe(new BaseObserver<Resond>(MainActivity.this) {
                    @Override
                    protected void onSuccess(BaseResponse<Resond> value) {
                        textView.setText(value.toString());
                    }

                    @Override
                    protected void onBefore(Disposable d) {
                        //这里调用dispose()可以取消
                        d.dispose();
                    }

                    @Override
                    protected void onFailure(String d) {

                    }


                });
    }

    public void noParameterDefult(View view) {
        textView.setText("");
        MyRetrofitClient.getInstance(this).createService().getVersionDefult().enqueue(new Callback<BaseResponse<Resond>>() {
            @Override
            public void onResponse(Call<BaseResponse<Resond>> call, Response<BaseResponse<Resond>> response) {
                textView.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Resond>> call, Throwable t) {

            }
        });
    }

    public void parameterDefult(View view) {
        textView.setText("");
        MyRetrofitClient.getInstance(this)
                .createService()
                .getVerificatCode("18380426497")
                .compose(RxHelper.io_main(this))
                .subscribe(new BaseObserver<String>(this) {

                    @Override
                    protected void onSuccess(BaseResponse<String> value) {
                        textView.setText(value.toString());
                    }

                    @Override
                    protected void onBefore(Disposable d) {

                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                });
    }

    public void multParameter(View view) {
        int pageNumber = 1, pageSize = 20, userId = 10;
        MyRetrofitClient.getInstance(this)
                .createService()
                .getStudentCourses(pageNumber, pageSize, userId)
                .compose(RxHelper.io_main(this))
                .subscribe(new BaseObserver<Page<MobileVideoResource>>(this) {
                    @Override
                    protected void onSuccess(BaseResponse<Page<MobileVideoResource>> value) {
                        textView.setText(value.toString());
                    }

                    @Override
                    protected void onBefore(Disposable d) {

                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                });
    }

    /**
     * 上传单张图片
     *
     * @param view
     */
    public void uploadImage(View view) {
        File file = new File("/你的文件路径");
        RequestBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "cover.jpg", RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        MyRetrofitClient.getInstance(this)
                .createService()
                .uploadCover(mBody)
                .compose(RxHelper.io_main(this))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccess(BaseResponse<String> value) {
                        textView.setText(value.toString());

                    }

                    @Override
                    protected void onBefore(Disposable d) {

                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                });
    }


    /**
     * 上传多张图片
     *
     * @param view
     */
    public void multImageUpload(View view) {
        File file1 = new File("/你的文件路径");
        File file2 = new File("/你的文件路径");
        File file3 = new File("/你的文件路径");
        File file4 = new File("/你的文件路径");
        File file5 = new File("/你的文件路径");
        RequestBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file1", file1.getName(), RequestBody.create(MediaType.parse("video/*"), file1))
                .addFormDataPart("file2", file2.getName(), RequestBody.create(MediaType.parse("video/*"), file2))
                .addFormDataPart("file3", file3.getName(), RequestBody.create(MediaType.parse("video/*"), file3))
                .addFormDataPart("file4", file4.getName(), RequestBody.create(MediaType.parse("video/*"), file4))
                .addFormDataPart("file5", file5.getName(), RequestBody.create(MediaType.parse("video/*"), file5))
                .addFormDataPart("duration", 10 + "")
                .addFormDataPart("star", String.valueOf(5))
                .addFormDataPart("trainType", "trainType")
                .addFormDataPart("content", "content")
                .addFormDataPart("cover", "cover")
                .addFormDataPart("title", "title")
                .build();
        MyRetrofitClient.getInstance(this)
                .createService()
                .uploadCourse(mBody)
                .compose(RxHelper.io_main(this))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccess(BaseResponse<String> value) {
                        textView.setText(value.toString());
                    }

                    @Override
                    protected void onBefore(Disposable d) {

                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                });
    }

    /**
     * 图文同时上传
     *
     * @param view
     */
    public void multImageAndTextUpload(View view) {
        File file1 = new File("/你的文件路径");
        RequestBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file1", file1.getName(), RequestBody.create(MediaType.parse("video/*"), file1))
                .addFormDataPart("duration", 10 + "")
                .addFormDataPart("star", String.valueOf(5))
                .addFormDataPart("trainType", "trainType")
                .addFormDataPart("content", "content")
                .addFormDataPart("cover", "cover")
                .addFormDataPart("title", "title")
                .build();
        MyRetrofitClient.getInstance(this)
                .createService()
                .uploadCourse(mBody)
                .compose(RxHelper.io_main(this))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccess(BaseResponse<String> value) {
                        textView.setText(value.toString());
                    }

                    @Override
                    protected void onBefore(Disposable d) {

                    }

                    @Override
                    protected void onFailure(String message) {

                    }

                });
    }
}
