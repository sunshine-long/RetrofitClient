package com.marlon.retrofitclentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.marlon.retrofitclent.BaseObserver;
import com.marlon.retrofitclent.BaseResponse;
import com.marlon.retrofitclent.RetrofitClient;
import com.marlon.retrofitclent.helper.RxHelper;
import com.marlon.retrofitclentdemo.bean.MobileVideoResource;
import com.marlon.retrofitclentdemo.bean.Page;
import com.marlon.retrofitclentdemo.bean.Resond;
import com.marlon.retrofitclentdemo.http.ApiService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        textView = findViewById(R.id.textView);
    }

    /**
     * +Rxjava 后五参数的情况
     *
     * @param view
     */
    public void noParameterRxjava(View view) {
        textView.setText("");
        RetrofitClient
                .getInstance(this)
                .createService(ApiService.class)
                .getVerisionRxjava()
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<Resond>() {
                    @Override
                    protected void onSuccess(Resond value) {
                        textView.setText(value.toString());
                    }
                });
    }

    public void noParameterDefult(View view) {
        textView.setText("");
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .getVersionDefult()
                .enqueue(new Callback<BaseResponse<Resond>>() {
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
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .getVerificatCode("18380426497")
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccess(String value) {
                        textView.setText(value.toString());
                    }

                });
    }

    public void multParameter(View view) {
        int pageNumber = 1, pageSize = 20, userId = 10;
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .getStudentCourses(pageNumber, pageSize, userId)
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<Page<MobileVideoResource>>() {
                    @Override
                    protected void onSuccess(Page<MobileVideoResource> value) {
                        textView.setText(value.toString());
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
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .uploadCover(mBody)
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(String value) {
                        textView.setText(value.toString());

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
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .uploadCourse(mBody)
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(String value) {
                        textView.setText(value.toString());
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
        RetrofitClient.getInstance(this)
                .createService(ApiService.class)
                .uploadCourse(mBody)
                .compose(RxHelper.applySchedulers(this))
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(String value) {
                        textView.setText(value.toString());
                    }

                });
    }

    public void gotoMvp(View view) {
        startActivity(new Intent(this,MvpMainActivity.class));
    }
}
