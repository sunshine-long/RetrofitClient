package net.uwonders.myretrofitclientdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.uwonders.myretrofitclientdemo.base.BaseObserver;
import net.uwonders.myretrofitclientdemo.base.BaseResponse;
import net.uwonders.myretrofitclientdemo.retrofit.MyRetrofitClient;
import net.uwonders.myretrofitclientdemo.retrofit.RxHelper;

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


    }
}
