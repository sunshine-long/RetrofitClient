package net.uwonders.myretrofitclientdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.uwonders.myretrofitclientdemo.retrofit.MyRetrofitClient;
import net.uwonders.myretrofitclientdemo.retrofit.RxSchedulers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }

    public void test(View view) {
      MyRetrofitClient
              .getInstance(this)
              .createService()
              .getVerificatCode()
              .compose(RxSchedulers.<Resond>io_main())
              .subscribe(/**这个还可以进一步封装自己发挥哈*/new Observer<Resond>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(Resond stringResponse) {
            textView.setText(stringResponse.toString());
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {

          }
      });

    }
}
