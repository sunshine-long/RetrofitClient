package com.marlon.mvpdaggerretrofit.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showShortToast(String s){

        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

    }

    public void showLongtToast(String s){

        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
