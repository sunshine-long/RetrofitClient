package com.marlon.retrofitclentdemo.contract;


import com.marlon.retrofitclentdemo.base.BasePresenter;
import com.marlon.retrofitclentdemo.base.BaseView;

/**
 * Created by KangLong on 2017/7/6.
 */

public class MainContract {
    public interface View extends BaseView {
        void showData(String s);
    }

    public interface Presenter extends BasePresenter<View> {
        void getVersion();

    }
}
